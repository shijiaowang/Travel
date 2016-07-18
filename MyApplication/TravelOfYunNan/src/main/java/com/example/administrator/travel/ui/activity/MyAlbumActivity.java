package com.example.administrator.travel.ui.activity;

import android.service.carrier.CarrierService;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/18 0018.
 *我的相册
 */
public class MyAlbumActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvBack;
    private TextView mTvAdd;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_my_album;
    }

    @Override
    protected void initView() {
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);//返回
        mTvAdd = FontsIconUtil.findIconFontsById(R.id.tv_add, this);//添加

    }

    @Override
    protected void initListener() {
       mTvBack.setOnClickListener(this);
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
        }

    }
}
