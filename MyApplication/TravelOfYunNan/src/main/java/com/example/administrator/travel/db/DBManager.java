package com.example.administrator.travel.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.example.administrator.travel.ui.appoint.adddestination.ProvinceBean;
import com.example.administrator.travel.utils.IOUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.UIUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class DBManager {
    public static final String SQL_NAME = "yun.sql";
    private static DBHelper dbHelper;


    private static void DBHelperInstance() {
        if (dbHelper == null) {
            dbHelper = new DBHelper(UIUtils.getContext());
        }
    }

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
        DBHelperInstance();
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        Cursor query = writableDatabase.query("yuns_district", null, "level=?", new String[]{"1"}, null, null, null);
        while (query.moveToNext()) {
            String id = query.getString(query.getColumnIndex("_id"));
            String name = query.getString(query.getColumnIndex("name"));
            ProvinceBean provinceBean = new ProvinceBean(id, name);
            provinceBeanList.add(provinceBean);
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
        DBHelperInstance();
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
            writableDatabase.close();
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
        DBHelperInstance();
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
        DBHelperInstance();
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor query = null;
        try {
            String city="";
            for (ProvinceBean provinceBean : options1Items) {
                query = writableDatabase.query("yuns_district", null, "level=? and upid=?", new String[]{"2", provinceBean.getId()}, null, null, null);
                ArrayList<String> arrayList=new ArrayList<>();
                while (query.moveToNext()) {
                     city=query.getString(query.getColumnIndex("name"));
                    arrayList.add(city);
                }
                arrayLists.add(arrayList);
            }
            writableDatabase.close();
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
     * @param id
     * @return
     */

    public static String getStringById(String type,String id) {
        DBHelperInstance();
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = writableDatabase.query("yuns_district", new String[]{type}, "_id=?", new String[]{id}, null, null, null);
            if (query.moveToNext()) {
                String name = query.getString(0);
                writableDatabase.close();
                return name;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeQuery(query);
        }
        return "未知";
    }
    public static String getCityId(String cityName, String id) {
        DBHelperInstance();
        SQLiteDatabase writableDatabase = dbHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = writableDatabase.query("yuns_district", new String[]{"_id"}, "name=? and upid=?", new String[]{cityName,id}, null, null, null);
            if (query.moveToNext()) {
                String cityId = query.getString(query.getColumnIndex("_id"));
                writableDatabase.close();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
