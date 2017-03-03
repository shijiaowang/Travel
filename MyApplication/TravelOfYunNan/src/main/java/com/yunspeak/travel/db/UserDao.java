package com.yunspeak.travel.db;

import android.database.Cursor;

import com.yunspeak.travel.bean.User;
import com.yunspeak.travel.bean.UserBean;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.StringUtils;
import java.util.Date;

import simpledao.cityoff.com.easydao.BaseEasyDao;

/**
 * Created by wangyang on 2017/3/2.
 * 用户管理
 */

public class UserDao extends BaseEasyDao<User>{


    /**
     * 获取当前登录的用户
     * @return
     */
    public UserBean getCurrentUser(){
        UserBean userBean=null;
        User user=new User("",0,"","1");
        user = query(user);
        if (user!=null){
            userBean = GsonUtils.getObject(user.getJson(), UserBean.class);
        }
        return userBean;
    }

    /**
     * 获取多用户集合
     * @return
     */
    public String[] getAccountArray(){
        Cursor query = sqLiteDatabase.query(tableName, new String[]{"name"}, null, null, null, null, "lastLoginTime DESC");
        String[] names=null;
        int index=0;
        if (query!=null) {
            int count = query.getCount();
            names = new String[count];
            while (query.moveToNext()) {
                names[index++] = query.getString(0);
            }
        }
        return names;
    }

    /**
     * 设置当前对象为登录对象，并且重置其他的
     * @param user
     */
    public boolean setCurrentUser(User user){
        int id = user.getId();
        String lastLoginTime = user.getLastLoginTime();
        if (StringUtils.isEmpty(lastLoginTime)){
            lastLoginTime=String.valueOf(new Date().getTime());
            user.setLastLoginTime(lastLoginTime);
        }
        user.setIsLogin("1");
        try {
            //其他设置为false 更新当前为true
            exec("update user set isLogin = 0 where id != "+id+";");
            User query = query(new User(null, id, null, null));
            if (query!=null){
                update(user);//存在用户更新
            }else {//不存在插入
                insert(user);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }



    }
}
