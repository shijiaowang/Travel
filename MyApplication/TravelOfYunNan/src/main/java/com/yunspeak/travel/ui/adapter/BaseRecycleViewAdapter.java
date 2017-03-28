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
    private boolean isNoMore=false;
    public BaseRecycleViewAdapter(List<T> datas){
        this.datas = datas;
    }
    public void resetDatas(List<T> newDatas,boolean isNoMore){
        this.isNoMore=isNoMore;
        this.datas=newDatas;
        notifyDataSetChanged();
    }
    public int getDataSize(){
        return datas==null?0:datas.size();
    }
    public void addHeaderDatas(List<T> newDatas,boolean isNoMore){
        if (newDatas==null)return;
        this.isNoMore=isNoMore;
        this.datas.addAll(0,newDatas);
        notifyDataSetChanged();
    }

    public void addDatas(List<T> newDatas,boolean isNoMore){
        if (newDatas==null)return;
        if (this.datas!=null) {
            this.isNoMore=isNoMore;
            int size = this.datas.size();
            this.datas.addAll(newDatas);
            notifyItemRangeInserted(size,this.datas.size());
        }else {
            resetDatas(newDatas,isNoMore);
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
        if (isNoMore && position==getItemCount()-1){
            return TYPE_NO_MORE;
        }
        return getOtherViewType(position);
    }

    protected  int getOtherViewType(int position){
        return TYPE_NOREMAL;
    }

    @Override
    public int getItemCount() {
        return getDataSize()+(isNoMore?1:0);
    }

    public boolean isNoMore() {
        return isNoMore;
    }

    public void setNoMore(boolean noMore) {
        isNoMore = noMore;
    }

    private class NoMoreHolder extends RecyclerView.ViewHolder{

        public NoMoreHolder(View itemView) {
            super(itemView);
        }
    }
}
