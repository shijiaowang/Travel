package com.yunspeak.travel.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;

import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.yunspeak.travel.ui.adapter.holer.SomeTextClick;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.AiteTextClick;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.InformBean;

import java.util.List;

/**
 * Created by wangyang on 2016/11/3 0003.
 */

public class AiteUtils {

    public static Spannable getSmiledText(Context context, String text, int start,List<InformBean> inform) {
        if (inform==null || inform.size()==0)return new SpannableStringBuilder(text);
        StringBuilder stringBuilder=new StringBuilder(text);
        for (InformBean informBean:inform){
            stringBuilder.append("@"+inform.get(inform.indexOf(informBean)).getNick_name());
        }
        String string = stringBuilder.toString();
        SpannableStringBuilder spannable = new SpannableStringBuilder(string);
       EaseSmileUtils.addSmiles(context, spannable);
        return addAiteUser(context,spannable,start,inform);
    }
    public static Spannable getSmiedTextWithAiteAndLinke(Context context, String text, int start,List<InformBean> inform,String url) {

        if (inform==null || inform.size()==0)return new SpannableStringBuilder(text);
        StringBuilder stringBuilder=new StringBuilder(text);
        for (InformBean informBean:inform){
            stringBuilder.append("@"+inform.get(inform.indexOf(informBean)).getNick_name());
        }
        String string = stringBuilder.toString();
        string+="【图片】";
        SpannableStringBuilder spannable = new SpannableStringBuilder(string);
        spannable.setSpan(new SomeTextClick(context,url), string.length() - 4, string.length()
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        EaseSmileUtils.addSmiles(context, spannable);
        return addAiteUser(context,spannable,start,inform);
    }

    /**
     *  改变aite的人的颜色
     * @param context
     * @param start
     */
    public static Spannable addAiteUser(Context context, Spannable spannable, int start,List<InformBean> inform) {

       for (int i=0;i<inform.size();i++){
            spannable.setSpan(new AiteTextClick(context, inform.get(i).getId()),start,start+=inform.get(i).getNick_name().length()+1
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
       }
        return spannable;

    }
}