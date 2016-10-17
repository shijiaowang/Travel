package com.example.administrator.travel.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 主页精选
 */
public class ChosenAdapter extends BaseRecycleViewAdapter<HomeBean.DataBean.ForumBean> {


    public ChosenAdapter(List<HomeBean.DataBean.ForumBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<HomeBean.DataBean.ForumBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_home_chosen, parent, false);
        return new ChosenHolder(inflate);
    }
}
