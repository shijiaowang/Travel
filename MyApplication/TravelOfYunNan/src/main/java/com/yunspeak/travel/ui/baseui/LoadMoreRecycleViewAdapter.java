package com.yunspeak.travel.ui.baseui;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/10/4.
 */

public abstract class LoadMoreRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    protected List<T> mDatas;
    protected Context mContext;
    protected boolean isScrolling=false;
    protected View loaderView;
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public LoadMoreRecycleViewAdapter(List<T> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    // RecyclerView的count设置为数据总条数+ 1（footerView）
    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    public void setList(List<T> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_FOOTER){
            final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more,null);
            return new FooterViewHolder(inflate);
        }else if (viewType==TYPE_ITEM){
            return normalHolder(LayoutInflater.from(parent.getContext()),parent,viewType);
        }
        return null;
    }

    protected abstract RecyclerView.ViewHolder normalHolder(LayoutInflater context, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
       if (position<getItemCount()-1){
           holder.itemView.setTag(position);
           if (itemClickListener!=null) {
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       int tag = (int) holder.itemView.getTag();
                       itemClickListener.onItemClick(tag);
                   }
               });
           }
           bindNormal(holder,position);

       }else {

       }
    }

    protected abstract void bindNormal(RecyclerView.ViewHolder holder, int position);



    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
            LoadMoreRecycleViewAdapter.this.loaderView =view;

        }

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public ItemViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 开始加载
     */
    public void startLoading(){
        if (loaderView!=null){
            loaderView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载结束
     */
    public void endLoading(){
        if (loaderView!=null){
            loaderView.setVisibility(View.GONE);
        }
    }
    private  OnItemClickListener itemClickListener;

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}