package com.example.administrator.travel.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.ui.fragment.MyCollectionFragment;
import com.example.administrator.travel.ui.fragment.MyPublicationFragment;
import com.example.administrator.travel.ui.view.SimpleViewPagerIndicator;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class MyCollectionActivity extends BarBaseActivity {
   @ViewInject(R.id.vp_collection)
    private ViewPager mVpCollection;
    @ViewInject(R.id.svpi_indicator)
    private SimpleViewPagerIndicator mSvpiIndicator;
    private List<BaseFragment> fragments;

    @Override
    protected void initContentView() {
        mSvpiIndicator.setTitles(new String[]{"我的收藏", "我的发表"});
        mSvpiIndicator.setViewPager(mVpCollection);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initEvent() {
        mVpCollection.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mSvpiIndicator.scroll(position,positionOffset);
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
    protected void initViewData() {
        fragments = new ArrayList<>();
        fragments.add(new MyPublicationFragment());
        fragments.add(new MyCollectionFragment());
        mVpCollection.setAdapter(new MyCollectionPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected String setTitleName() {
        return "我的收藏";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
    class MyCollectionPagerAdapter extends FragmentPagerAdapter{

        public MyCollectionPagerAdapter(FragmentManager fm) {
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
