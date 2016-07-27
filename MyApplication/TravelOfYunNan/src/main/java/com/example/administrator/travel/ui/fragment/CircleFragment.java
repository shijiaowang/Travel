package com.example.administrator.travel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.event.VolleyStringEvent;
import com.example.administrator.travel.ui.fragment.circlefragment.HotFragment;
import com.example.administrator.travel.ui.fragment.circlefragment.NavigationFragment;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/7 0007.
 * 圈子Fragment
 */
public class CircleFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager mVpCircle;
    private RelativeLayout mRlTitleBg;
    private List<BaseFragment> fragments = new ArrayList<>(2);
    private TextView mTvLeftTitle;
    private TextView mTvRightTitle;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_circle;
    }


    protected void initView() {
        mRlTitleBg = (RelativeLayout) root.findViewById(R.id.rl_title_bg);
        mVpCircle = (ViewPager) root.findViewById(R.id.vp_circle);
        mTvLeftTitle = (TextView) root.findViewById(R.id.tv_left_title);
        mTvRightTitle = (TextView) root.findViewById(R.id.tv_right_title);
    }

    @Override
    protected void initData() {
        fragments.add(new NavigationFragment());
        fragments.add(new HotFragment());
        mVpCircle.setAdapter(new CirclePagerAdapter(getChildFragmentManager()));
        initTitle(0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        mTvLeftTitle.setOnClickListener(this);
        mTvRightTitle.setOnClickListener(this);
       mVpCircle.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               initTitle(position);
           }

           @Override
           public void onPageSelected(int position) {

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
    }

    /**
     * 初始化title
     * @param position
     */
    private int prePosition=-1;
    private void initTitle(int position) {

        if (position==0 && position!=prePosition){
            mRlTitleBg.setBackgroundResource(R.drawable.fragment_circle_nav);
            mTvLeftTitle.setTextColor(getResources().getColor(R.color.circleTitleColorChecked));
            mTvLeftTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            mTvRightTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            mTvRightTitle.setTextColor(getResources().getColor(R.color.circleTitleColorUnChecked));
        }else if (position==1 && position!=prePosition){
            mTvLeftTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            mTvRightTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            mRlTitleBg.setBackgroundResource(R.drawable.fragment_circle_hot);
            mTvRightTitle.setTextColor(getResources().getColor(R.color.circleTitleColorChecked));
            mTvLeftTitle.setTextColor(getResources().getColor(R.color.circleTitleColorUnChecked));
        }
        prePosition=position;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left_title:
                mVpCircle.setCurrentItem(0,false);
                initTitle(0);
                break;
            case R.id.tv_right_title:
                mVpCircle.setCurrentItem(1,false);
                initTitle(1);
                break;
        }
    }

    class CirclePagerAdapter extends FragmentPagerAdapter {

        public CirclePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}
