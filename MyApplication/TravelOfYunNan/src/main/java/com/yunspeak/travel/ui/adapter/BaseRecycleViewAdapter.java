package com.yunspeak.travel.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangyang on 2017/3/16.
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> datas;

    public BaseRecycleViewAdapter(List<T> datas){
        this.datas = datas;
    }
    public void resetDatas(List<T> newDatas){
        this.datas=newDatas;
        notifyDataSetChanged();
    }
    public void addDatas(List<T> newDatas){
        if (newDatas==null)return;
        if (this.datas!=null) {
            int size = this.datas.size();
            this.datas.addAll(newDatas);
            notifyItemRangeInserted(size,this.datas.size());
        }else {
            resetDatas(newDatas);
        }
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
}
