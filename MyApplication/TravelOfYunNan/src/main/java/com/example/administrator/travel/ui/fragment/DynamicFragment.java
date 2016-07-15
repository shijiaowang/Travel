package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.DynamicAdapter;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.ViewUtil;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class DynamicFragment extends BaseFragment {

    private ListView mLvDynamic;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_other_dynamic;
    }

    @Override
    protected void initView() {
        mLvDynamic = (ListView) root.findViewById(R.id.lv_dynamic);
    }

    @Override
    protected void initData() {
        mLvDynamic.setAdapter(new DynamicAdapter(getContext(), null));
        int length = ViewUtil.setListViewHeightBasedOnChildren1(mLvDynamic);
        LogUtils.e("listView高度为"+length);
    }

    @Override
    protected void initListener() {

    }
}
