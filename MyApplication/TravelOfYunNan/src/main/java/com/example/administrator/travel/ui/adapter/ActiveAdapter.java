package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.Active;
import com.example.administrator.travel.ui.adapter.holer.ActiveHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class ActiveAdapter extends TravelBaseAdapter<Active.DataBean> {
    public ActiveAdapter(Context mContext, List<Active.DataBean> mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, Active.DataBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new ActiveHolder(mContext);
    }
}
