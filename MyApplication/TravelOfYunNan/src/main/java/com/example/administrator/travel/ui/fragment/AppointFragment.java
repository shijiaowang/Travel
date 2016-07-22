package com.example.administrator.travel.ui.fragment;


import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.AppointEvent;
import com.example.administrator.travel.ui.adapter.fragment.CommonPagerAdapter;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by Administrator on 2016/7/20 0020.
 * 约伴
 */
public class AppointFragment extends BaseFragment {


    private ViewPager mVpAppoint;
    private LinearLayout mLlSwitch;
    private boolean isTogether = false;
    private TextView mTvPlayTogether;
    private TextView mTvPlayWithMe;
    private List<BaseFragment> fragments;
    private FloatingActionButton mFabAdd;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_appoint;
    }

    @Override
    protected void initView() {
        mVpAppoint = (ViewPager) root.findViewById(R.id.vp_appoint);
        mLlSwitch = (LinearLayout) root.findViewById(R.id.ll_switch);
        mTvPlayTogether = (TextView) root.findViewById(R.id.tv_play_together);
        mTvPlayWithMe = (TextView) root.findViewById(R.id.tv_play_with_me);
        mFabAdd = (FloatingActionButton) root.findViewById(R.id.fab_add);


    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>(2);
        fragments.add(new PlayTogetherFragment());
        fragments.add(new PlayWithMeFragment());
        mVpAppoint.setAdapter(new CommonPagerAdapter(getChildFragmentManager(), fragments));

    }

    @Override
    protected void initListener() {
        mLlSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTogether = !isTogether;
                if (isTogether) {
                    selectPager(0);
                    mVpAppoint.setCurrentItem(0, false);
                } else {
                    selectPager(1);
                    mVpAppoint.setCurrentItem(1, false);
                }

            }
        });

        mVpAppoint.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void selectPager(int position) {
        if (position < 0 || position > fragments.size() - 1) {
            return;
        }
        if (position == 0) {
            mTvPlayTogether.setBackgroundResource(R.drawable.fragment_appoint_cursor);
            mTvPlayTogether.setTextColor(getResources().getColor(R.color.otherTitleBg));
            mTvPlayWithMe.setBackgroundColor(Color.TRANSPARENT);
            mTvPlayWithMe.setTextColor(getResources().getColor(R.color.colorFAFAFA));
        } else {
            mTvPlayWithMe.setBackgroundResource(R.drawable.fragment_appoint_cursor);
            mTvPlayWithMe.setTextColor(getResources().getColor(R.color.otherTitleBg));
            mTvPlayTogether.setBackgroundColor(Color.TRANSPARENT);
            mTvPlayTogether.setTextColor(getResources().getColor(R.color.colorFAFAFA));
        }
    }

    /**
     * 事件 与 fragment通信
     * @param event
     */
    public void onEvent(AppointEvent event) {
        if (event.isSmooth()){
            mFabAdd.setVisibility(View.INVISIBLE);
        }else {
            mFabAdd.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
