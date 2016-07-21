package com.example.administrator.travel.ui.fragment;



import android.support.v4.view.ViewPager;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.fragment.CommonPagerAdapter;
import com.example.administrator.travel.utils.FontsIconUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/20 0020.
 * 约伴
 */
public class AppointFragment extends BaseFragment {


    private ViewPager mVpAppoint;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_appoint;
    }

    @Override
    protected void initView() {
        mVpAppoint = (ViewPager) root.findViewById(R.id.vp_appoint);
    }

    @Override
    protected void initData() {
        List<BaseFragment> fragments=new ArrayList<>(2);
        fragments.add(new PlayTogetherFragment());
        fragments.add(new PlayWithMeFragment());
     mVpAppoint.setAdapter(new CommonPagerAdapter(getChildFragmentManager(),fragments));

    }

    @Override
    protected void initListener() {

    }
}
