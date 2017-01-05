package com.yunspeak.travel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yunspeak.travel.bean.ProvinceBean;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.UserInfo;
import com.yunspeak.travel.utils.UIUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/9/13 0013.
 */
public class DBManager {
    public static final String SQL_NAME = "yun.sql";
    static private DBHelper dbHelper = new DBHelper(UIUtils.getContext());


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
     * 获取省
     *
     * @return
     */
    public static ArrayList<ProvinceBean> getProvince() {
        if (!cityDBIsExits()) {
            initCityDB(UIUtils.getContext());
        }
        ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        Cursor query = null;
        try {
            query = writableDatabase.query("yuns_district", null, "level=?", new String[]{"1"}, null, null, null);
            while (query.moveToNext()) {
                String id = query.getString(query.getColumnIndex("_id"));
                String name = query.getString(query.getColumnIndex("name"));
                ProvinceBean provinceBean = new ProvinceBean(id, name);
                provinceBeanList.add(provinceBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuery(query);
        }


        return provinceBeanList;
    }

    /**
     * 初始化数据库
     *
     * @param context
     * @return
     */
    public static void initCityDB(Context context) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        InputStream in = null;
        try {
            in = context.getAssets().open(SQL_NAME);
            String sqlUpdate = readTextFromSDcard(in);
            String[] s = sqlUpdate.split(";");
            for (String sql : s) {
                if (!StringUtils.isEmpty(sql)) {
                    writableDatabase.execSQL(sql);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (in != null) {
                try {
                    in.close();
                    dbHelper.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断表是否存在
     *
     * @return
     */
    public static boolean cityDBIsExits() {
        Cursor query = null;
        try {

            SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
            query = writableDatabase.query("yuns_district", null, null, null, null, null, null, "1");
            if (query.moveToNext()) {
                dbHelper.close();
                writableDatabase.close();
                return true;
            }
        } catch (Exception e) {
            dbHelper.close();
            e.printStackTrace();
        } finally {
            closeQuery(query);
        }

        LogUtils.e("表不存在");
        return false;
    }

    /**
     * 获取城市列表
     *
     * @param options1Items
     * @return
     */
    public static ArrayList<ArrayList<String>> getCity(ArrayList<ProvinceBean> options1Items) {
        ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor query = null;
        try {
            String city;
            for (ProvinceBean provinceBean : options1Items) {
                query = writableDatabase.query("yuns_district", null, "level=? and upid=?", new String[]{"2", provinceBean.getId()}, null, null, null);
                ArrayList<String> arrayList = new ArrayList<>();
                while (query.moveToNext()) {
                    city = query.getString(query.getColumnIndex("name"));
                    arrayList.add(city);
                }
                arrayLists.add(arrayList);
            }
            return arrayLists;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuery(query);
        }
        return null;
    }

    /**
     * 查询id
     *
     * @param id
     * @return
     */

    public static synchronized String getStringById(String type, String id) {
        String name = "未知";
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = writableDatabase.query("yuns_district", new String[]{type}, "_id=?", new String[]{id}, null, null, null);
            if (query.moveToNext()) {
                name = query.getString(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuery(query);
        }
        return name;
    }

    public static String getCityId(String cityName, String id) {
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = writableDatabase.query("yuns_district", new String[]{"_id"}, "name=? and upid=?", new String[]{cityName, id}, null, null, null);
            if (query.moveToNext()) {
                String cityId = query.getString(query.getColumnIndex("_id"));
                return cityId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuery(query);
        }
        return null;
    }


    public static void closeQuery(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertChatUserInfo(UserInfo userInfo) {
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(userInfo);
        insertChatUserInfo(userInfos);
    }

    //userinfo操作
    public static void insertChatUserInfo(List<UserInfo> userInfos) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        Cursor chatuser = null;
        for (UserInfo userInfo : userInfos) {
            try {
                if (userInfo == null) continue;
                chatuser = writableDatabase.query("chatuser", null, "userid=?", new String[]{userInfo.getId()}, null, null, null);
                if (chatuser.moveToLast()) {
                    String username = chatuser.getString(chatuser.getColumnIndex("username"));
                    String userimg = chatuser.getString(chatuser.getColumnIndex("userimg"));
                    if (userimg.equals(userInfo.getUser_img()) && username.equals(userInfo.getNick_name())) {

                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("username", userInfo.getNick_name());
                        contentValues.put("userimg", userInfo.getUser_img());
                        writableDatabase.update("chatuser", contentValues, "userid=?", new String[]{userInfo.getId()});
                    }
                } else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("userid", userInfo.getId());
                    contentValues.put("username", userInfo.getNick_name());
                    contentValues.put("userimg", userInfo.getUser_img());
                    writableDatabase.insert("chatuser", null, contentValues);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        closeQuery(chatuser);
    }

    public static EaseUser getChatUserByChatId(String chatId) {
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor chatuser = writableDatabase.query("chatuser", null, "userid=?", new String[]{chatId}, null, null, null);
        if (chatuser.moveToLast()) {
            String username = chatuser.getString(chatuser.getColumnIndex("username"));
            String userimg = chatuser.getString(chatuser.getColumnIndex("userimg"));
            EaseUser easeUser = new EaseUser(chatId);
            easeUser.setNickname(username);
            easeUser.setAvatar(userimg);
            closeQuery(chatuser);
            return easeUser;
        }
        closeQuery(chatuser);
        return null;
    }

    /**
     * 插入缓存数据
     *
     * @param pageName
     * @param saveData
     */
    public static void insertHomePageSavaData(String pageName, String saveData) {
        if (StringUtils.isEmpty(saveData)) return;
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pagename", pageName);
        contentValues.put("pagecontent", saveData);
        writableDatabase.insert("homedata", null, contentValues);
    }

    /**
     * 获取缓存数据
     */
    public static String querySaveDataByPageName(String pageName) {
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = writableDatabase.query("homedata", null, "pagename=?", new String[]{pageName}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("pagecontent"));
            return name;
        }
        return "";
    }
}
