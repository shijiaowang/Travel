package yunshuo.yneb.com.myapplication.other.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.UserInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import yunshuo.yneb.com.myapplication.other.utils.UIUtils;

/**
 * Created by wangyang on 2016/9/13 0013.
 */
public class DBManager {

    static private DBHelper   dbHelper = new DBHelper(UIUtils.getContext());



    /**
     * 按行读取txt
     *
     * @param is
     * @return
     * @throws Exception
     */
    private static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }



    /**
     * 查询id
     * @param id
     * @return
     */

    public static synchronized String getStringById(String type,String id) {
        String name="未知";
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = writableDatabase.query("yuns_district", new String[]{type}, "_id=?", new String[]{id}, null, null, null);
            if (query.moveToNext()) {
                name = query.getString(0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeQuery(query);
        }
        return name;
    }
    public static String getCityId(String cityName, String id) {
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = writableDatabase.query("yuns_district", new String[]{"_id"}, "name=? and upid=?", new String[]{cityName,id}, null, null, null);
            if (query.moveToNext()) {
                String cityId = query.getString(query.getColumnIndex("_id"));
                return cityId;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeQuery(query);
        }
        return null;
    }


    public static void closeQuery(Cursor cursor){
        if (cursor!=null){
            try {
                cursor.close();
                cursor=null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void insertChatUserInfo(UserInfo userInfo){
        List<UserInfo> userInfos=new ArrayList<>();
        userInfos.add(userInfo);
        insertChatUserInfo(userInfos);
    }
    //userinfo操作
    public static void insertChatUserInfo(List<UserInfo> userInfos){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        Cursor chatuser=null;
        for (UserInfo userInfo:userInfos){
            try {
                if (userInfo == null) continue;
                chatuser = writableDatabase.query("chatuser", null, "userid=?", new String[]{userInfo.getId()}, null, null, null);
               if (chatuser.moveToLast()){
                   String username = chatuser.getString(chatuser.getColumnIndex("username"));
                   String userimg = chatuser.getString(chatuser.getColumnIndex("userimg"));
                   if (userimg.equals(userInfo.getUser_img()) && username.equals(userInfo.getNick_name())){

                   }else {
                       ContentValues contentValues=new ContentValues();
                       contentValues.put("username",userInfo.getNick_name());
                       contentValues.put("userimg",userInfo.getUser_img());
                       writableDatabase.update("chatuser",contentValues,"userid=?",new String[]{userInfo.getId()});
                   }
               }else {
                   ContentValues contentValues=new ContentValues();
                   contentValues.put("userid",userInfo.getId());
                   contentValues.put("username",userInfo.getNick_name());
                   contentValues.put("userimg",userInfo.getUser_img());
                   writableDatabase.insert("chatuser",null,contentValues);
               }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        closeQuery(chatuser);
    }
    public static EaseUser getChatUserByChatId(String chatId){
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor  chatuser = writableDatabase.query("chatuser", null, "userid=?", new String[]{chatId}, null, null, null);
        if (chatuser.moveToLast()) {
            String username = chatuser.getString(chatuser.getColumnIndex("username"));
            String userimg = chatuser.getString(chatuser.getColumnIndex("userimg"));
            EaseUser easeUser=new EaseUser(chatId);
            easeUser.setNickname(username);
            easeUser.setAvatar(userimg);
            closeQuery(chatuser);
            return easeUser;
        }
        closeQuery(chatuser);
        return null;
    }
}
