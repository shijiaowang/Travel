package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.example.administrator.travel.utils.ToastUtils;

/**
 * Created by Administrator on 2016/8/22 0022.
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
        ToastUtils.showToast("点击啦");
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(Color.parseColor("#5cd0c2"));
        ds.setUnderlineText(false);//是否有下划线
    }
}
