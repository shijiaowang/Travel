package com.example.administrator.travel.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.fragment.SystemMessagePagerAdapter;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.ui.view.SimpleViewPagerIndicator;
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
    private SimpleViewPagerIndicator mIndicator;


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initView() {
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
        mTvMore = FontsIconUtil.findIconFontsById(R.id.tv_more, this);
        mVpMessageCenter = (ViewPager) findViewById(R.id.vp_message_center);

        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.svpi_indicator);
        mIndicator.setTitles(new String[]{"系统消息", "私信"});
        mIndicator.setViewPager(mVpMessageCenter);


    }

    @Override
    protected void initData() {
        mVpMessageCenter.setAdapter(new SystemMessagePagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void initListener() {
        mTvBack.setOnClickListener(this);
        mVpMessageCenter.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

