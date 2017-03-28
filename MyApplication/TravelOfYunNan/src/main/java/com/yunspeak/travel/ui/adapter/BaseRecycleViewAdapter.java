package com.yunspeak.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;

import java.util.List;

/**
 * Created by wangyang on 2017/3/16.
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   private static final int TYPE_NO_MORE=0;
   private static final int TYPE_NOREMAL=1;

    protected List<T> datas;
    protected LayoutInflater layoutInflater;

    public BaseRecycleViewAdapter(List<T> datas){
        this.datas = datas;
    }
    public void resetDatas(List<T> newDatas){
        this.datas=newDatas;
        notifyDataSetChanged();
    }
    public void addHeaderDatas(List<T> newDatas){
        if (newDatas==null)return;
        this.datas.addAll(0,newDatas);
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (viewType==TYPE_NO_MORE){
            View inflate = layoutInflater.inflate(R.layout.item_common_no_more, parent,false);
            return new NoMoreHolder(inflate);
        }else {
            return otherViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)!=TYPE_NO_MORE){
            onBindOtherHolder(holder,position);
        }
    }

    protected abstract void onBindOtherHolder(RecyclerView.ViewHolder holder, int position);

    protected abstract RecyclerView.ViewHolder otherViewHolder(ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1){
            return TYPE_NO_MORE;
        }
        return TYPE_NOREMAL;
    }
    @Override
    public int getItemCount() {
        return (datas==null?0:datas.size())+1;
    }
   private class NoMoreHolder extends RecyclerView.ViewHolder{

        public NoMoreHolder(View itemView) {
            super(itemView);
        }
    }
}
