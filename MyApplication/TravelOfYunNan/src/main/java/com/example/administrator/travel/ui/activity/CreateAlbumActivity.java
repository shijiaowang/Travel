package com.example.administrator.travel.ui.activity;


import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

public class CreateAlbumActivity extends BaseActivity implements View.OnClickListener {


    private TextView mTvBack;
    private TextView mTvMore;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_create_album;
    }

    @Override
    protected void initView() {
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
        mTvMore = FontsIconUtil.findIconFontsById(R.id.tv_more, this);

    }

    @Override
    protected void initListener() {
       mTvBack.setOnClickListener(this);
        mTvMore.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_more:
                break;
        }
    }
}
