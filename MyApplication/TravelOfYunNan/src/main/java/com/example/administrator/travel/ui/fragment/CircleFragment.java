package com.example.administrator.travel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.fragment.circlefragment.HotFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/7 0007.
 * 圈子Fragment
 */
public class CircleFragment extends BaseFragment {

    private ViewPager mVpCircle;
    private RelativeLayout mRlTitleBg;
    private List<BaseFragment> fragments=new ArrayList<>(2);

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_circle;
    }


    protected void initView() {
        mRlTitleBg = (RelativeLayout) root.findViewById(R.id.rl_title_bg);
        mVpCircle = (ViewPager) root.findViewById(R.id.vp_circle);
    }

    @Override
    protected void initData() {
          fragments.add(new HotFragment());
          fragments.add(new HotFragment());
        mVpCircle.setAdapter(new CirclePagerAdapter(getActivity().getSupportFragmentManager()));
    }

    @Override
    protected void initListener() {

    }
    class CirclePagerAdapter extends FragmentPagerAdapter{

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
}
