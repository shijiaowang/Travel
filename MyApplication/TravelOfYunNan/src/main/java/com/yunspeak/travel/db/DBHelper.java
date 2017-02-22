package com.yunspeak.travel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangyang on 2016/9/13 0013.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String yunsDb = "yuns_district.db";
    private static final int VERSION_CODE = 2;

    public DBHelper(Context context) {
        super(context, yunsDb, null, VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //省市级联表
        String sql = "CREATE TABLE IF NOT EXISTS yuns_district(_id integer primary key ,name text,level text,upid text,key text,pinyin text)";
        String userNick = "CREATE TABLE IF NOT EXISTS chatuser(_id integer primary key autoincrement,userid text,username text,userimg text)";
        String saveData = "CREATE TABLE IF NOT EXISTS homedata(_id integer primary key autoincrement,pagename text,pagecontent text)";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(userNick);
        sqLiteDatabase.execSQL(saveData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }


}
