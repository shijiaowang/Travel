package com.example.administrator.travel.ui.baseui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;

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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            childBindView(holder,position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    protected abstract void childBindView(RecyclerView.ViewHolder holder, int position);
   protected   class BaseRecycleViewHolder  extends RecyclerView.ViewHolder{

       public BaseRecycleViewHolder(View itemView) {
           super(itemView);
           ButterKnife.bind(this,itemView);
       }
   }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
