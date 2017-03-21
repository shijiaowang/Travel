package com.yunspeak.travel.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.yunspeak.travel.ui.adapter.holer.SomeTextClick;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.AiteTextClick;
import com.yunspeak.travel.bean.InformBean;
import com.yunspeak.travel.ui.find.findcommon.CustomTypefaceSpan;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangyang on 2016/11/3 0003.
 */

public class AiteUtils {

    public static void parseTextMessage(TextView textView, List<InformBean> inform, String content,boolean isClick){
        Spannable span =getSmiledText(textView.getContext(), content, inform);
        // 设置内容
        textView.setText(span);
        if (isClick) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
        }
    }
    public static Spannable getSmiledText(Context context, String text, List<InformBean> inform) {
        int length=text==null?0:text.length();
        if (inform==null || inform.size()==0){
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
            EaseSmileUtils.addSmiles(context,spannableStringBuilder);
            return spannableStringBuilder ;
        }
        StringBuilder stringBuilder=new StringBuilder(text);
        for (InformBean informBean:inform){
            stringBuilder.append("@"+inform.get(inform.indexOf(informBean)).getNick_name());
        }
        String string = stringBuilder.toString()+"\u3000";
        SpannableStringBuilder spannable = new SpannableStringBuilder(string);
        EaseSmileUtils.addSmiles(context,spannable);
        return addAiteUser(context,spannable,length,inform);
    }
    public static Spannable getSmiedTextWithAiteAndLinke(Context context, String text,List<InformBean> inform,String url) {
        int start=text==null?0:text.length();
        text=text==null?"":text;
        StringBuilder stringBuilder = new StringBuilder(text);
        if (!(inform==null || inform.size()==0)) {
            for (InformBean informBean : inform) {
                stringBuilder.append("@").append(inform.get(inform.indexOf(informBean)).getNick_name());
            }
        }
        String string = stringBuilder.toString()+"\u3000";
        if (!StringUtils.isEmpty(url)) {
            string += "【图片】";
        }
        SpannableStringBuilder spannable = new SpannableStringBuilder(string);
        if (!StringUtils.isEmpty(url)) {
            spannable.setSpan(new SomeTextClick(context, url), string.length() - 4, string.length()
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        EaseSmileUtils.addSmiles(context, spannable);
        return addAiteUser(context,spannable,start,inform);
    }

    /**
     *  改变aite的人的颜色
     * @param context
     * @param start
     */
    public static Spannable addAiteUser(Context context, Spannable spannable, int start,List<InformBean> inform) {
      if (inform==null || inform.size()==0)return spannable;
       for (int i=0;i<inform.size();i++){
            spannable.setSpan(new AiteTextClick(context, inform.get(i).getId()),start,start+=inform.get(i).getNick_name().length()+1
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
       }
        return spannable;

    }
    public static int normalColor= Color.parseColor("#969696");
    public static int clickColor=Color.parseColor("#ff7f6c");

    public static void setIconText(String text,String number,TextView textView){
        setIconText(false,text,-1,number,textView,-1);
    }
    public static void setIconText(boolean isLove,String text,int color,String number,TextView textView,int resSize){
        String likeNumber =text+"\u0020"+number;
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder(likeNumber);
        spannableStringBuilder.setSpan(new CustomTypefaceSpan("sans-serif",textView.getContext(),isLove?clickColor:color,resSize),0,1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannableStringBuilder);
    }

    /**
     * 获取唯独消息的数量
     * @return
     */
    public static int getUnReadMessage(){
        try {
            int unreadMsgCount=0;//获取唯独消息数量
            Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();
            Set<String> strings = allConversations.keySet();
            Iterator<String> iterator = strings.iterator();
            while(iterator.hasNext()){
                String next = iterator.next();
                EMConversation emConversation = allConversations.get(next);
                unreadMsgCount+= emConversation.getUnreadMsgCount();
            }
            return unreadMsgCount;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
