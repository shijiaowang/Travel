package com.yunspeak.travel.ui.circle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.circle.circlenav.NavLeftFragment;
import com.yunspeak.travel.ui.circle.hotpost.HotFragment;
import com.yunspeak.travel.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/7 0007.
 * 圈子Fragment
 */
public class CircleFragment extends BaseFragment implements View.OnClickListener {
    private List<Fragment> fragments = new ArrayList<>(2);
    @BindView(R.id.vp_circle) ViewPager mVpCircle;
    @BindView(R.id.rl_title_bg) RelativeLayout mRlTitleBg;
    @BindView(R.id.tv_left_title) TextView mTvLeftTitle;
    @BindView(R.id.tv_right_title) TextView mTvRightTitle;
    @BindView(R.id.v_left) View left;
    @BindView(R.id.v_right) View right;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_circle;
    }


    protected void initView() {

    }

    @Override
    protected void initData() {
        if (fragments.size()<2) {
            fragments.add(new NavLeftFragment());
            fragments.add(new HotFragment());
        }
        mVpCircle.setAdapter(new CirclePagerAdapter(getChildFragmentManager()));
        initTitle(0);
    }

    /**
     * 设置颜色
     * @param isEmpty
     */
    public void showEmpty(boolean isEmpty){

        if (isEmpty){
            int meLine=getContext().getResources().getColor(R.color.meLine);
            setColor(meLine);
        }else {
            int meWhite=getContext().getResources().getColor(R.color.colorFAFAFA);
            setColor(meWhite);
        }
    }

    private void setColor(int meLine) {
        left.setBackgroundColor(meLine);
        right.setBackgroundColor(meLine);
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
