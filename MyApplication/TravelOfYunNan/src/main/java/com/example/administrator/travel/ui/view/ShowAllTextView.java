package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.ui.adapter.holer.SomeTextClick;
import com.example.administrator.travel.utils.ToastUtils;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 查看或者收起全部textview
 */
public class ShowAllTextView extends TextView {

   private String text="";
    private String content;
    private boolean isShowAll=false;
    private int normalShowLength=25;//默认显示字数
    private String[] clickText={"【展开】","【收起】"};
    public int getNormalShowLength() {
        return normalShowLength;
    }

    public void setNormalShowLength(int normalShowLength) {
        this.normalShowLength = normalShowLength;
    }

    public ShowAllTextView(Context context) {
        this(context,null);
    }

    public ShowAllTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowAllTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        if (content.length()>normalShowLength) {
            init();
        }else {
            setText(content);
        }
    }

    public String[] getClickText() {
        return clickText;
    }

    public void setClickText(String[] clickText) {
        this.clickText = clickText;
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setIsShowAll(boolean isShowAll) {
        this.isShowAll = isShowAll;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        if (isShowAll){//展示全部
            text=content+clickText[1];
        }else {
            text=content.substring(0,normalShowLength)+"...."+clickText[0];
        }
        setClick();
    }

    /**
     * 点击事件
     */
    private void setClick() {
        this.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ShowClick(), text.length() - 4, text.length()
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannable);

    }
     class ShowClick extends ClickableSpan {

        @Override
        public void onClick(View widget) {
            setIsShowAll(!isShowAll);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.parseColor("#5cd0c2"));
            ds.setUnderlineText(false);//是否有下划线
        }
    }
}
