package com.yunspeak.travel.utils;

import com.yunspeak.travel.bean.UserInfo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by wangyang on 2016/8/15 0015.
 * 序列化与反序列化对象
 */
public class UserUtils {

    public static void saveUserInfo(Object obj){
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
    }
    public static UserInfo getUserInfo(){
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
    }
}
