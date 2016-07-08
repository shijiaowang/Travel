package com.example.administrator.travel.ui.fragment.circlefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.CircleNavLeftAdapter;
import com.example.administrator.travel.ui.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class NavigationFragment extends BaseFragment {

    private ListView mLvLeftNav;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_circle_navigation;
    }

    @Override
    protected void initView() {
        mLvLeftNav = (ListView) super.root.findViewById(R.id.lv_left_nav);

    }

    @Override
    protected void initData() {
        mLvLeftNav.setAdapter(new CircleNavLeftAdapter(getActivity(),null));
    }

    @Override
    protected void initListener() {

    }
}
