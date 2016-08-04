package com.example.administrator.travel.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.fragment.SystemMessagePagerAdapter;
import com.example.administrator.travel.ui.view.SimpleViewPagerIndicator;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/15 0015.
 * 消息中心
 */
public class MessageCenterActivity extends BarBaseActivity implements View.OnClickListener {


    private TextView mTvMore;
    @ViewInject(R.id.vp_message_center)
    private ViewPager mVpMessageCenter;
    @ViewInject(R.id.svpi_indicator)
    private SimpleViewPagerIndicator mIndicator;






    @Override
    protected void initContentView() {
        getmVsRightIcon().inflate();
        mTvMore = FontsIconUtil.findIconFontsById(R.id.tv_ok, this);
        mTvMore.setText(getResources().getString(R.string.activity_message_center_more));
        mIndicator.setTitles(new String[]{"系统消息", "私信"});
        mIndicator.setViewPager(mVpMessageCenter);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_message_center;
    }


    @Override
    protected void initViewData() {
        mVpMessageCenter.setAdapter(new SystemMessagePagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected String setTitleName() {
        return "系统消息";
    }


    @Override
    protected void initEvent() {
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

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected boolean haveRightIcon() {
        return true;
    }
}

