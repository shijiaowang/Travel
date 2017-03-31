package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.UIUtils;

/**
 * Created by wangyang on 2017/3/31.
 * 搜索
 */

public class SearchView extends RelativeLayout {
    String defailtTextHint="请输入关键字";
    int defaultHintColor= UIUtils.getColor(R.color.colorFAFAFA);
    int defaultTextColor= UIUtils.getColor(R.color.color646464);
    String defaultSearchIcon=UIUtils.getString(R.string.fragment_circle_search);
    int defaultBackgroundBg=R.drawable.home_search_bg;
    boolean defaultSearchClickAble=true;
    private EditText editText;

    public SearchView(Context context) {
        this(context,null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(final Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();//获取自定义属性个数
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.SearchView_search_bg:
                    defaultBackgroundBg=typedArray.getResourceId(index,0);
                    break;
                case R.styleable.SearchView_search_color:
                    defaultTextColor=typedArray.getResourceId(index,0);
                    break;
                case R.styleable.SearchView_search_hint:
                    defailtTextHint=typedArray.getString(index);
                    break;
                case R.styleable.SearchView_search_hint_color:
                    defaultHintColor=typedArray.getResourceId(index,0);
                    break;
                case R.styleable.SearchView_search_icon_text:
                    defaultSearchIcon=typedArray.getString(index);
                    break;
                case R.styleable.SearchView_search_edit_clicked:
                    defaultSearchClickAble=typedArray.getBoolean(index,true);
                    break;
            }
        }
        typedArray.recycle();
        LayoutInflater systemService = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = systemService.inflate(R.layout.activity_search, null,false);
        inflate.setBackgroundResource(defaultBackgroundBg);
        editText = (EditText) inflate.findViewById(R.id.et_search);
        TextView fontsIconTextView = (TextView) inflate.findViewById(R.id.tv_search);
        fontsIconTextView.setText(defaultSearchIcon);
        editText.setTextColor(defaultTextColor);
        editText.setHintTextColor(defaultHintColor);
        editText.setHint(defailtTextHint);
        if (!defaultSearchClickAble){
            editText.setClickable(false);
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
        }else {
            fontsIconTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onSearch(getTrim(editText));
                    }
                }
            });
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    //一般输入法或搜狗输入法点击搜索按键
                    if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                        //这里调用搜索方法
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏键盘
                        if (listener!=null){
                            listener.onSearch(getTrim(editText));
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
        addView(inflate);

    }

    @NonNull
    private String getTrim(EditText editText) {
        return editText.getText().toString().trim();
    }

    private OnSearchListener listener;

    public void setListener(OnSearchListener listener) {
        this.listener = listener;
    }

    public  interface  OnSearchListener{
       void onSearch(String text);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !defaultSearchClickAble || super.onInterceptTouchEvent(ev);
    }
}
