package com.yunspeak.travel;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.yunspeak.travel.utils.FormatDateUtils;


import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wangyang on 2016/7/8 0008.
 */
public class TravelsApplication extends MultiDexApplication {
    private static Context mContext;
    protected static Handler mHandler;
    private static int mainThreadId;



    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        x.Ext.init(this);
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfigFactory.getImagePipelineConfig(this);
        Fresco.initialize(this,imagePipelineConfig);
       /* x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能*/
        mContext = getApplicationContext();
        mHandler = new Handler();
        mainThreadId = android.os.Process.myTid();//获取主线程的id
        initUmeng();
        YunSpeakHelper.getInstance().init(mContext);




    }

    private void initUmeng() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);//服务器控制声音有无
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.e("友盟成功"+deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {
                LogUtil.e("友盟失败"+s);
            }
        });

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.yun_speak);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                                R.layout.app_notify_layout);
                        myNotificationView.setTextViewText(R.id.tv_title, msg.title);
                        myNotificationView.setTextViewText(R.id.tv_content, msg.text);
                        long time = new Date().getTime();
                        myNotificationView.setTextViewText(R.id.tv_time, FormatDateUtils.FormatLongTime("HH:mm",time/1000+""));
                        builder.setCustomBigContentView(myNotificationView)
                                .setSmallIcon(R.drawable.user_icon)
                                .setLargeIcon(bitmap)
                                .setWhen(time)
                                .setContentTitle(msg.title)
                                .setContentText(msg.text)
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);
                        return  builder.build();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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

