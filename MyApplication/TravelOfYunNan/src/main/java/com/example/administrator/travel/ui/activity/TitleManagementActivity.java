package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.fragment.TabFragment;
import com.example.administrator.travel.ui.fragment.TitleManagementFragment;
import com.example.administrator.travel.ui.view.SimpleViewPagerIndicator;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7 0007.
 * 称号管理
 */
public class TitleManagementActivity extends LoadingBarBaseActivity {
    private static final int TYPE_MY_TITLE = 0;
    private static final int TYPE_VER_TITLE = 1;//认证标志
    private static final int TYPE_PLAY_WAY = 2;//玩法
    private static final int TYPE_DIY_TITLE = 3;
    private List<Fragment> fragments=new ArrayList<>();
    private String[] mTitles = {"我的称号", "认证标志", "玩法", "自定义"};
    @ViewInject(R.id.vp_pager)
    private ViewPager mVpPager;
    @ViewInject(R.id.indicator)
    private SimpleViewPagerIndicator mIndicator;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_title_management;
    }

    @Override
    protected void initEvent() {
       init();
        mVpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private void init() {
        mIndicator.setIsTitle(true);
        mIndicator.setViewPager(mVpPager);
        mIndicator.setTitles(mTitles);
        String[] title4 = {"我叫老司机", "我叫司机", "小", "我叫老司机", "老司机称霸武林", "我叫老司机", "神棍上路", "我叫机", "我就呵呵"};
        TitleManagementFragment tabFragment1 = TitleManagementFragment.newInstance(title4, TYPE_MY_TITLE);
        TitleManagementFragment tabFragment2 = TitleManagementFragment.newInstance(title4, TYPE_MY_TITLE);
        TitleManagementFragment tabFragment3 = TitleManagementFragment.newInstance(title4, TYPE_MY_TITLE);
        TitleManagementFragment tabFragment4 = TitleManagementFragment.newInstance(title4, TYPE_MY_TITLE);
        fragments.add(tabFragment1);
        fragments.add(tabFragment2);
        fragments.add(tabFragment3);
        fragments.add(tabFragment4);
        mVpPager.setAdapter(new TitlePagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        return null;
    }

    @Override
    protected String setTitleName() {
        return "称号管理";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
    class TitlePagerAdapter extends FragmentPagerAdapter{

        public TitlePagerAdapter(FragmentManager fm) {
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
}
