package com.yunspeak.travel.ui.home.welcome.splash.register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by wangyang on 2016/12/1 0001.
 */
public class CityoffSpeak extends ClickableSpan {
    private Context context;

    public CityoffSpeak(Context context) {
        this.context = context;

    }

    @Override
    public void onClick(View widget) {
        context.startActivity(new Intent(context,WebViewActivity.class));
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(Color.parseColor("#5cd0c2"));
        ds.setUnderlineText(false);//是否有下划线
    }
}