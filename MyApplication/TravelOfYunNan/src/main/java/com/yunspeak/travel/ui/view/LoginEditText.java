package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
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

    private boolean isEmpty;
    private EditText mEtEnter;
    private View mVLine;
    private TextView mTvDelete;
    private String hint = "";
    private int length=100;

    public LoginEditText(Context context) {
        this(context, null);
    }

    public LoginEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
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
                case R.styleable.LoginEditText_line_edittext_max_length:
                    length = typedArray.getInt(index,100);
                    break;
            }

        }
        typedArray.recycle();
        View inflate = LayoutInflater.from(context).inflate(R.layout.line_edit_text, this, false);
        mEtEnter = (EditText) inflate.findViewById(R.id.et_enter);
        mEtEnter.setHint(hint);
        //密码隐藏
        setMaxLength(length);
        mEtEnter.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
                   if (textChangedListener!=null){
                       textChangedListener.beforeTextChanged(s,start,count,after);
                   }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmptyNotNull(mEtEnter.getText().toString().toString())) {
                    isEmpty = true;
                } else {
                    isEmpty=false;
                }
                mTvDelete.setVisibility(isEmpty?GONE:VISIBLE);
                if (textChangedListener!=null){
                    textChangedListener.onTextChanged(s,start,before,count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (textChangedListener!=null){
                    textChangedListener.afterTextChanged(s);
                }
            }
        });
        mEtEnter.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ViewGroup.LayoutParams layoutParams = mVLine.getLayoutParams();
                if (hasFocus) {
                    layoutParams.height = DensityUtil.dip2px(1.5f);
                    if (!StringUtils.isEmpty(getString())){
                        mTvDelete.setVisibility(VISIBLE);
                    }
                } else {
                    layoutParams.height = DensityUtil.dip2px(1);
                    mTvDelete.setVisibility(GONE);
                }
                mVLine.setLayoutParams(layoutParams);
            }
        });
    }

    /**
     * 设置提示语
     * @param hint
     */
    public void setHint(String hint) {
        mEtEnter.setHint(hint);
    }

    /**
     * 获取输入的字
     * @return
     */
    public String getString() {
        return mEtEnter.getText().toString().trim();
    }

    /**
     * 设置模式
     * @param inputType
     */
    public void setInputType(int inputType){
        mEtEnter.setInputType(inputType);
    }

    /**
     * 设置最长字数
     * @param length
     */
    public void setMaxLength(int length){
        mEtEnter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});//长度限制设置
    }
    public  interface TextChangedListener{
        void beforeTextChanged(CharSequence s, int start, int count, int after);
        void onTextChanged(CharSequence s, int start, int before, int count);
        void afterTextChanged(Editable s);
    }
    public TextChangedListener textChangedListener;
    public void addTextChangedListener(TextChangedListener textChangedListener){
        this.textChangedListener=textChangedListener;
    }

    public void setError(CharSequence text){
        mEtEnter.setError(text);
    }
}
