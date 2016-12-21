package com.yunspeak.travel.ui.circle.circlenav.circledetail;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;

/**
 * Created by wangyang on 2016/8/22 0022.
 * 部分文字变色可点击
 */
public class AiteTextClick extends ClickableSpan{
    private Context context;
    private String id;

    public AiteTextClick(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    @Override
    public void onClick(View widget) {
        widget.invalidate();
        if (StringUtils.isEmpty(id)){
            ToastUtils.showToast("用户不存在");
            return;
        }
        OtherUserCenterActivity.start(context,id);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(Color.parseColor("#7fbdff"));
        ds.setUnderlineText(false);//是否有下划线
    }
}
