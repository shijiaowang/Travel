package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.PostAdapter;

/**
 * 帖子
 */

public class PostActivity extends BaseActivity {


    private ListView mLvPostDetail;
    private ImageView mIvBack;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_post;
    }

    @Override
    protected void initView() {
        mLvPostDetail = (ListView) findViewById(R.id.lv_post_detail);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
    }

    @Override
    protected void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
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
}
