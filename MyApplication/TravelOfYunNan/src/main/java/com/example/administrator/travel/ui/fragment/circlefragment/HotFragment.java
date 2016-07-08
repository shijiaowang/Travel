package com.example.administrator.travel.ui.fragment.circlefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.CircleHotAdapter;
import com.example.administrator.travel.ui.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class HotFragment extends BaseFragment {

    private ListView mLvCircleHot;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_circle_hot;
    }
    @Override
    protected void initView() {
        mLvCircleHot = (ListView) root.findViewById(R.id.lv_circle_hot);
    }
    @Override
    protected void initData() {
       mLvCircleHot.setAdapter(new CircleHotAdapter(getActivity(),null));
    }

    @Override
    protected void initListener() {

    }


}
