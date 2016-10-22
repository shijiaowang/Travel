package com.example.administrator.travel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "yuns_district.db", null, 1);
    }

    @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //省市级联表
            String sql = "create table yuns_district(_id integer primary key autoincrement,name text,level text,upid text)";
            String userNick = "create table chatuser(_id integer primary key autoincrement,userid text,username text,userimg text)";
            sqLiteDatabase.execSQL(sql);
            sqLiteDatabase.execSQL(userNick);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }



}
