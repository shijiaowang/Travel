package com.example.administrator.travel.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.fragment.SystemMessagePagerAdapter;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/15 0015.
 * 消息中心
 */
public class MessageCenterActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvBack;
    private TextView mTvMore;
    private ViewPager mVpMessageCenter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initView() {
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
        mTvMore = FontsIconUtil.findIconFontsById(R.id.tv_more, this);
        mVpMessageCenter = (ViewPager) findViewById(R.id.vp_message_center);


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
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
