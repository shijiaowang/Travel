package com.example.administrator.travel.ui.activity;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvBack;
    private TextView mCreatePost;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_circle;
    }

    @Override
    protected void initView() {
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mCreatePost = (TextView) findViewById(R.id.tv_create_post);
        initFontsIcon();
    }

    private void initFontsIcon() {
        Typeface fromAsset = Typeface.createFromAsset(getAssets(), "fonts/icomoon.ttf");
        mTvBack.setTypeface(fromAsset);
        mCreatePost.setTypeface(fromAsset);
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
