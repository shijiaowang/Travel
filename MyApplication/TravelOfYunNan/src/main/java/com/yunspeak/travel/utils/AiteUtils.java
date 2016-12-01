package com.yunspeak.travel.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.yunspeak.travel.ui.adapter.holer.SomeTextClick;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.AiteTextClick;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.InformBean;

import java.util.List;

/**
 * Created by wangyang on 2016/11/3 0003.
 */

public class AiteUtils {

    public static void parseTextMessage(TextView textView, List<InformBean> inform, String content, Context mContext,boolean isClick){
        Spannable span =getSmiledText(mContext, content, inform);
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
        String string = stringBuilder.toString();
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
                stringBuilder.append("@" + inform.get(inform.indexOf(informBean)).getNick_name());
            }
        }
        String string = stringBuilder.toString();
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
}
