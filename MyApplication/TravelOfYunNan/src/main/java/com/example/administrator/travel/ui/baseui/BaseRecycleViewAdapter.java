package com.example.administrator.travel.ui.baseui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by wangyang on 2016/10/8 0008.
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<T> mDatas;
    public Context mContext;

    public BaseRecycleViewAdapter(List<T> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }
    public void notifiyData(List list){
        if (list==null)return;
        mDatas=list;
        this.notifyDataSetChanged();
    }
}
