package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.StringUtils;

import org.xutils.common.util.DensityUtil;

import butterknife.BindColor;

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
    private float deleteRight;
    private int leftIconSrc;
    private ImageView mIvLeftImage;
    private float leftIconLeftMargin;
    private float editLeftMargin;
    private boolean isShowSendButton;
    private AvoidFastButton mBtSend;
    int verTime=60;
    private boolean canSending=true;//是否可以点击发送按钮

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
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (verTime <= 0) {
                removeCallbacksAndMessages(null);
                verTime = 60;//初始化事件
                mBtSend.setText("重发");
                changeClickAble(true);
                canSending=true;
                return;
            }
            mBtSend.setText("重发(" + --verTime + ")");
            sendEmptyMessageDelayed(0, 1000);
        }
    };

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
                case R.styleable.LoginEditText_line_edittext_delete_right:
                    deleteRight = typedArray.getDimension(index, 0);
                    break;
                case R.styleable.LoginEditText_line_edittext_image_src:
                    leftIconSrc = typedArray.getResourceId(index, 0);
                    break;
                case R.styleable.LoginEditText_line_edittext_image_left:
                    leftIconLeftMargin = typedArray.getDimension(index,0);
                    break;
                case R.styleable.LoginEditText_line_edittext_input_left:
                    editLeftMargin = typedArray.getDimension(index,0);
                    break;
                case R.styleable.LoginEditText_line_edittext_show_send_button:
                    isShowSendButton = typedArray.getBoolean(index,false);
                    break;

            }

        }
        typedArray.recycle();
        View inflate = LayoutInflater.from(context).inflate(R.layout.line_edit_text, this, false);
        mIvLeftImage = (ImageView) inflate.findViewById(R.id.iv_left_image);
        mEtEnter = (EditText) inflate.findViewById(R.id.et_enter);
        mEtEnter.setHint(hint);
        //密码隐藏
        setMaxLength(length);
        mEtEnter.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mVLine = inflate.findViewById(R.id.v_line);
        mTvDelete = (TextView) inflate.findViewById(R.id.tv_delete);
        mBtSend = (AvoidFastButton) inflate.findViewById(R.id.bt_ver);

        this.addView(inflate);
        mTvDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtEnter.setText("");
                mTvDelete.setVisibility(GONE);
            }
        });
        if (deleteRight!=0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTvDelete.getLayoutParams();
            layoutParams.rightMargin= (int) deleteRight;
            mTvDelete.setLayoutParams(layoutParams);
        }
        if (leftIconLeftMargin!=0){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTvDelete.getLayoutParams();
            layoutParams.leftMargin= (int) leftIconLeftMargin;
            mTvDelete.setLayoutParams(layoutParams);
        }
        if (editLeftMargin!=0){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mEtEnter.getLayoutParams();
            layoutParams.leftMargin= (int) editLeftMargin;
            mEtEnter.setLayoutParams(layoutParams);
        }
        if (leftIconSrc!=0f){
            mIvLeftImage.setImageResource(leftIconSrc);
        }
        if (isShowSendButton){
            mBtSend.setVisibility(VISIBLE);
            mBtSend.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!canSending)return;
                    if (onClickListener!=null){
                        onClickListener.onClick(v);
                    }
                }
            });
        }
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
                    mVLine.setBackgroundColor(getResources().getColor(R.color.otherTitleBg));
                } else {
                    layoutParams.height = DensityUtil.dip2px(1);
                    mTvDelete.setVisibility(GONE);
                    mVLine.setBackgroundColor(getResources().getColor(R.color.colord2d2d2));
                }
                mVLine.setLayoutParams(layoutParams);
            }
        });
    }

    /**
     * 显示或者隐藏底部
     * @param isShow
     */
    public void showLine(boolean isShow){
        mVLine.setVisibility(isShow?VISIBLE:GONE);
    }
    /**
     * 设置提示语
     * @param hint
     */
    public void setHint(String hint) {
        mEtEnter.setHint(hint);
    }

    /**
     * 开始计时
     */
   public void setTimeStart(){
       mHandler.sendEmptyMessageDelayed(0,1000);
       changeClickAble(false);//设置无法点击
       canSending = false;
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

    /**
     * 获取点击按钮
     * @return
     */
    public AvoidFastButton getmBtSend() {
        return mBtSend;
    }
    /**
     * 设置按钮背景
     *
     * @param b
     */
    protected void changeClickAble(boolean b) {
        if (b) {
            mBtSend.setBackgroundResource(R.drawable.login_button_selector);
            mBtSend.setClickable(b);
        } else {
            mBtSend.setBackgroundResource(R.drawable.green_button_normal_bg_unclick);
            mBtSend.setClickable(b);
        }
    }

    /**
     * 设置按钮是否可点击
     * @param canClick
     */
    public void setButtonState(boolean canClick){
        if (canSending && canClick){
            changeClickAble(true);
        }else {
            changeClickAble(false);
        }
    }

    private SendButtonOnClickListener onClickListener;
    public  interface  SendButtonOnClickListener{
        void onClick(View button);
    }
    public void setOnSendButtonClickListener(SendButtonOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
