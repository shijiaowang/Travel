package com.yunspeak.travel.ui.find.travels;


import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.baseui.BaseXListViewActivity;
import com.yunspeak.travel.ui.baseui.TravelsDetailActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.MapUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * wangyang
 * 游记
 */
public class TravelsActivity extends BaseXListViewActivity<TravelsEvent,TravelsBean,TravelsBean.DataBean> implements View.OnKeyListener {
    private String content="";
    private EditText mEtSearch;
    private FontsIconTextView mTvSearch;


    @Override
    protected void initEvent() {
        super.initEvent();
        mVsHeader.setLayoutResource(R.layout.activity_travels_header);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mTvSearch = (FontsIconTextView) findViewById(R.id.tv_search);
        setMarginTop(10);
        setChildSpace(10);

    }

    @Override
    protected void childItemClick(int i) {
        Intent intent = new Intent(TravelsActivity.this, TravelsDetailActivity.class);
        intent.putExtra(IVariable.T_ID,mDatas.get(i).getId());
        startActivity(intent);
    }

    @Override
    protected String initUrl() {
        return IVariable.FIND_TRAVELS;
    }


    @Override
    protected void childAdd(MapUtils.Builder builder) {
        builder.addContent(content);
    }

    /**
     * 搜索
     */
    private void search() {
            content=getString(mEtSearch);
            onLoad(TYPE_SEARCH);
    }



    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0); //强制隐藏键盘
            search();
            return true;
        }
        return false;
    }


    @Override
    protected TravelBaseAdapter initAdapter(List<TravelsBean.DataBean> httpData) {
        return new TravelsAdapter(this,mDatas);
    }



    @Override
    protected String initTitle() {
        return "游记";
    }
}
