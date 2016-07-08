package com.example.administrator.travel.ui.fragment.circlefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.CircleActivity;
import com.example.administrator.travel.ui.adapter.CircleNavLeftAdapter;
import com.example.administrator.travel.ui.adapter.CircleNavRightAdapter;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.utils.ToastUtils;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class NavigationFragment extends BaseFragment {

    private ListView mLvLeftNav;
    private ListView mLvRightNav;
    private RelativeLayout mRlPost;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_circle_navigation;
    }

    @Override
    protected void initView() {
        mLvLeftNav = (ListView) super.root.findViewById(R.id.lv_left_nav);
        mLvRightNav = (ListView) super.root.findViewById(R.id.lv_right_nav);


    }

    @Override
    protected void initData() {
        mLvLeftNav.setAdapter(new CircleNavLeftAdapter(getActivity(),null));
        mLvRightNav.setAdapter(new CircleNavRightAdapter(getActivity(),null));

    }

    @Override
    protected void initListener() {
        mLvRightNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                Intent intent = new Intent(getActivity(), CircleActivity.class);
                ActivityCompat.startActivity(getActivity(), intent, compat.toBundle());
            }
        });
    }
}
