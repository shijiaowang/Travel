package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.StringUtils;

import org.xutils.common.util.DensityUtil;

/**
 * Created by wangyang on 2016/11/5 0005.
 */

public class LoginEditText extends FrameLayout {

    private EditText mEtEnter;
    private View mVLine;
    private TextView mTvDelete;
    private String hint="";

    public LoginEditText(Context context) {
        this(context,null);
    }

    public LoginEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoginEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginEditText, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();//获取自定义属性个数
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.LoginEditText_line_edittext_hint:
                    hint = typedArray.getString(index);
                    break;
            }

        }
        typedArray.recycle();
        View inflate = LayoutInflater.from(context).inflate(R.layout.line_edit_text, this, false);
        mEtEnter = (EditText) inflate.findViewById(R.id.et_enter);
        mEtEnter.setHint(hint);
        mVLine = inflate.findViewById(R.id.v_line);
        mTvDelete = (TextView) inflate.findViewById(R.id.tv_delete);
        this.addView(inflate);
        mTvDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtEnter.setText("");
                mTvDelete.setVisibility(GONE);
            }
        });
        mEtEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (StringUtils.isEmptyNotNull(mEtEnter.getText().toString().toString())){
                   mTvDelete.setVisibility(GONE);
               }else {
                   mTvDelete.setVisibility(VISIBLE);
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtEnter.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ViewGroup.LayoutParams layoutParams = mVLine.getLayoutParams();
                if (hasFocus){
                    layoutParams.height= DensityUtil.dip2px(2);
                }else {
                    layoutParams.height= DensityUtil.dip2px(1);
                }
                mVLine.setLayoutParams(layoutParams);
            }
        });
    }
    public void setHint(String hint){
        mEtEnter.setHint(hint);
    }

}
