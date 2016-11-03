package com.yunspeak.travel.ui.adapter.holer;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.photopreview.CirclePreviewActivity;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/8/22 0022.
 * 部分文字变色可点击
 */
public class SomeTextClick extends ClickableSpan{
    private Context context;
    private String url;

    public SomeTextClick(Context context,String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        //ToastUtils.showToast("点击啦");
        if (StringUtils.isEmpty(url)){
            ToastUtils.showToast("图片不存在");
            return;
        }
        List<String> list=new ArrayList<>();
        list.add(url);
        CirclePreviewActivity.start(context,list,0);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(Color.parseColor("#5cd0c2"));
        ds.setUnderlineText(false);//是否有下划线
    }
}
