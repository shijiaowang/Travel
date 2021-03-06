package com.yunspeak.travel.ui.find.travels;


import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.FindTravelsBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.MapUtils;

import java.util.List;

import butterknife.BindView;

/**
 * wangyang
 * 游记
 */
public class TravelsActivity extends BaseRecycleViewActivity<TravelsEvent,FindTravelsBean,FindTravelsBean.DataBean> implements View.OnClickListener {
    private String content="";
    private EditText mEtSearch;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @Override
    protected void initEvent() {
        super.initEvent();
        try {
            changeNeedHideView(mSwipe);
            mFlContent.removeView(mRlEmpty);
            mRlEmpty.setVisibility(View.GONE);
            llRoot.addView(mRlEmpty);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mVsContent.setLayoutResource(R.layout.activity_travels_header);
        mVsContent.inflate();
        mEtSearch = (EditText) findViewById(R.id.et_search);
        FontsIconTextView mTvSearch = (FontsIconTextView) findViewById(R.id.tv_search);
        mTvSearch.setOnClickListener(this);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //一般输入法或搜狗输入法点击搜索按键
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //这里调用搜索方法
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0); //强制隐藏键盘
                    search();
                    return true;
                }
                return false;
            }
        });
        changeMargin(10,0);
    }

    @Override
    protected String initUrl() {
        return IVariable.FIND_TRAVELS;
    }



    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
            builder.addContent(content);
    }

    /**
     * 搜索
     */
    private void search() {
            content=mEtSearch.getText().toString().trim();
            resetIsFirstInflate();
           setIsProgress(true);
            onLoad(TYPE_SEARCH);
    }






    @Override
    protected BaseRecycleViewAdapter initAdapter(List<FindTravelsBean.DataBean> httpData) {
        return new TravelsAdapter(mDatas,this);
    }

    @Override
    protected String initTitle() {
        return "游记";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                search();
                break;
        }
    }

}
