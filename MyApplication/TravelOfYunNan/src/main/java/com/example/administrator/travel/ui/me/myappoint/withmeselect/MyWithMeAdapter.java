package com.example.administrator.travel.ui.me.myappoint.withmeselect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class MyWithMeAdapter extends BaseRecycleViewAdapter<MyWithMeSelectBean.DataBean> {

    public MyWithMeAdapter(List<MyWithMeSelectBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.activity_my_appoint_selector, parent, false);
        return new MyWithMeHolder(inflate);
    }






}
