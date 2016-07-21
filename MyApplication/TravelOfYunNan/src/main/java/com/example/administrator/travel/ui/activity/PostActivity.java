package com.example.administrator.travel.ui.activity;


import android.view.View;

import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.PostAdapter;
import com.example.administrator.travel.utils.FontsIconUtil;


/**
 * 帖子
 */

public class PostActivity extends BaseActivity {


    private ListView mLvPostDetail;
    private TextView mTvBack;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_post;
    }

    @Override
    protected void initView() {
        mLvPostDetail = (ListView) findViewById(R.id.lv_post_detail);
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back,this);
    }

    @Override
    protected void initListener() {
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {
            mLvPostDetail.setAdapter(new PostAdapter(this,null));

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
