package com.yunspeak.travel.utils;

import com.hyphenate.easeui.domain.EaseUser;
import com.yunspeak.travel.bean.User;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.db.ChatDao;
import com.yunspeak.travel.db.UserDao;
import com.yunspeak.travel.global.GlobalValue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import simpledao.cityoff.com.easydao.BaseDaoFactory;

/**
 * Created by wangyang on 2016/8/15 0015.
 * 序列化与反序列化对象
 */
public class UserUtils {


    /*public static void saveUserInfo(Object obj){
        if (obj==null)return;
        FileOutputStream fileOutputStream=null;
        ObjectOutputStream   oos=null;
        try {
            fileOutputStream = UIUtils.getContext().openFileOutput("user.txt", UIUtils.getContext().MODE_PRIVATE);
            oos= new ObjectOutputStream(fileOutputStream);
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(fileOutputStream);
            IOUtils.close(oos);
        }
    }*/

    /**
     * 保存当前用户信息
     * @param data
     */
    public static void saveUserInfo(UserInfo data){
        if (data==null)return;
        GlobalValue.userInfo=data;
        UserDao userDao= BaseDaoFactory.getInstance().getDaoHelper(UserDao.class,User.class);
        userDao.setCurrentUser(new User(GsonUtils.getJson(data),data.getId(),data.getName(),data.getPwd(),new Date().getTime()+"","1"));
        saveChatUserInfo(data);
    }

    /**
     * 保存聊天对象信息
     * @param data
     */
    public static void saveChatUserInfo(UserInfo data){
        com.hyphenate.easeui.domain.UserInfo userInfo = new com.hyphenate.easeui.domain.UserInfo();
        userInfo.setUserId(data.getId());
        userInfo.setNick_name(data.getNick_name());
        userInfo.setUser_img(data.getUser_img());
        ChatDao userDao = BaseDaoFactory.getInstance().getUserDao(ChatDao.class, com.hyphenate.easeui.domain.UserInfo.class, true, data.getId()+"");
        userDao.updateOrInsert(data.getId(),userInfo);
    }
    /**
     * 保存聊天对象信息
     * @param data
     */
    public static void saveChatUserInfo(com.hyphenate.easeui.domain.UserInfo data){
        if (data==null)return;
        ChatDao userDao = BaseDaoFactory.getInstance().getUserDao(ChatDao.class, com.hyphenate.easeui.domain.UserInfo.class, true, data.getUserId());
        userDao.updateOrInsert(data.getUserId(),data);
    }


    /*这是反序列化的*/
   /* public static UserInfo getUserInfo(){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = UIUtils.getContext().openFileInput("user.txt");
            ois = new ObjectInputStream(fis);
            return (UserInfo) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(fis);
            IOUtils.close(ois);
        }
        return null;
    }*/

    /**
     * 获取当前用户信息
     * @return
     */
    public static UserInfo getUserInfo(){
        return GlobalUtils.getUserInfo();
    }

    /**
     * 转换为easeUser
     * @param chatId
     * @return
     */
    public static EaseUser getEaseChatUser(String chatId){
        ChatDao userDao = BaseDaoFactory.getInstance().getUserDao(ChatDao.class, com.hyphenate.easeui.domain.UserInfo.class, true, GlobalUtils.getUserInfo().getId());
        com.hyphenate.easeui.domain.UserInfo userInfo=new com.hyphenate.easeui.domain.UserInfo(chatId);
        com.hyphenate.easeui.domain.UserInfo query = userDao.query(userInfo);
        if (query!=null) {
            EaseUser easeUser = new EaseUser(chatId);
            easeUser.setNickname(query.getNick_name());
            easeUser.setAvatar(query.getUser_img());
        }
        return null;
    }
}
