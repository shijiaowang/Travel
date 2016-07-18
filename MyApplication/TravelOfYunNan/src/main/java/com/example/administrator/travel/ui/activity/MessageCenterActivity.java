package com.example.administrator.travel.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.fragment.SystemMessagePagerAdapter;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.LogUtils;

/**
 * Created by Administrator on 2016/7/15 0015.
 * 消息中心
 */
public class MessageCenterActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvBack;
    private TextView mTvMore;
    private ViewPager mVpMessageCenter;
    private View mVCursor;
    private TextView mTvSystem;
    private TextView mTvPrivate;
    private int[] locationSystem = new int[2];//系统消息位置
    private int[] locationPrivate = new int[2];//私信消息位置
    private int distance = 0;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initView() {
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
        mTvMore = FontsIconUtil.findIconFontsById(R.id.tv_more, this);
        mVpMessageCenter = (ViewPager) findViewById(R.id.vp_message_center);
        mVCursor = findViewById(R.id.v_cursor);
        mTvSystem = (TextView) findViewById(R.id.tv_system);
        mTvPrivate = (TextView) findViewById(R.id.tv_private);


    }

    @Override
    protected void initData() {
        mVpMessageCenter.setAdapter(new SystemMessagePagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void initListener() {
        mTvBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }



}

