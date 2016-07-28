package com.example.administrator.travel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class TravelsApplication extends Application {
    private static Context mContext;
    protected static Handler mHandler;
    private static int mainThreadId;


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
        mContext = getApplicationContext();
        mHandler = new Handler();
        mainThreadId = android.os.Process.myTid();//获取主线程的id

    }

    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Context getmContext() {
        return mContext;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}

