package com.example.administrator.travel.utils;

import android.content.Context;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.SystemMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class SystemMessageInitUtils {
    public static List<SystemMessage> initSystemData(Context context){
        List<SystemMessage> systemMessages=new ArrayList<>(5);
        //初始化第一个item属性
        SystemMessage systemMessage=new SystemMessage();
        systemMessage.setTime("08-09 6:13");
        systemMessage.setIconBg(R.drawable.fragment_message_discuss_bg);
        systemMessage.setIconName(context.getString(R.string.activity_circle_discuss));
        systemMessage.setCursorContent(context.getString(R.string.activity_message_center_cursor));
        systemMessage.setNumber(6);
        systemMessage.setTitle("评论我的");
        systemMessages.add(systemMessage);
        SystemMessage systemMessage1=new SystemMessage();
        systemMessage1.setTime("08-09 6:13");
        systemMessage1.setIconBg(R.drawable.fragment_message_aite_bg);
        systemMessage1.setIconName(context.getString(R.string.activity_message_center_aite));
        systemMessage1.setNumber(6);
        systemMessage1.setCursorContent(context.getString(R.string.activity_message_center_cursor));
        systemMessage1.setTitle("@我");
        systemMessages.add(systemMessage1);
        SystemMessage systemMessage2=new SystemMessage();
        systemMessage2.setTime("08-09 6:13");
        systemMessage2.setIconBg(R.drawable.fragment_message_praise_bg);
        systemMessage2.setIconName(context.getString(R.string.activity_circle_love_empty));
        systemMessage2.setTitle("赞");
        systemMessage2.setIsCursorItem(true);
        systemMessage2.setCursorContent(context.getString(R.string.activity_message_center_cursor));
        systemMessages.add(systemMessage2);

        SystemMessage systemMessage3=new SystemMessage();
        systemMessage3.setTime("08-09 6:13");
        systemMessage3.setIsCursorItem(true);
        systemMessage3.setCursorContent(context.getString(R.string.activity_message_center_cursor));
        systemMessage3.setIconBg(R.drawable.fragment_message_recommend_bg);
        systemMessage3.setIconName(context.getString(R.string.activity_message_center_recommend));
        systemMessage3.setTitle("其他");
        systemMessages.add(systemMessage3);
        SystemMessage systemMessage4=new SystemMessage();
        systemMessage4.setContent("欢迎来到云说");
        systemMessage4.setIsCursorItem(true);
        systemMessage4.setIconBg(R.drawable.activity_other_title_load);
        systemMessage4.setIsLast(true);
        systemMessage4.setCursorContent(context.getString(R.string.activity_message_center_cursor));
        systemMessage4.setTime("08-09 6:13");
        systemMessage4.setTitle("云说客服");
        systemMessages.add(systemMessage4);
        return systemMessages;
    }
}
