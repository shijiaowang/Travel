package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;



/**
 * Created by wangyang on 2016/8/17 0017.
 * 隐藏中间位数的显示
 */
public class PhoneTextView extends TextView {

    private String phoneNumber;

    public PhoneTextView(Context context) {
        this(context, null);
    }

    public PhoneTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        phoneNumber = getText().toString().trim();
        hideCenterPhoneNumber();
    }

    private void hideCenterPhoneNumber() {
        if (phoneNumber.length()>4){
            String start = phoneNumber.substring(0, 2);
            String end = phoneNumber.substring(phoneNumber.length()-1, phoneNumber.length());
            phoneNumber=start+"********"+ end;
            setText(phoneNumber);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
        hideCenterPhoneNumber();
    }
}
