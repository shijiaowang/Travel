package com.example.administrator.travel.ui.baseui;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;

import java.util.List;

/**
 * Created by wangyang on 2016/10/4.
 */

public abstract class LoadMoreRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<T> list;
    protected View loaderView;
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public LoadMoreRecycleViewAdapter(List<T> list) {
        this.list = list;
    }

    // RecyclerView的count设置为数据总条数+ 1（footerView）
    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public void setList(List<T> list) {
        this.list = list;
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
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more,null);
            return new FooterViewHolder(inflate);
        }else if (viewType==TYPE_ITEM){
            return normalHolder(LayoutInflater.from(parent.getContext()),parent,viewType);
        }
        return null;
    }

    protected abstract RecyclerView.ViewHolder normalHolder(LayoutInflater context, ViewGroup parent, int viewType);



    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
            LoadMoreRecycleViewAdapter.this.loaderView =view;

        }

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {

        private View parentView;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            this.parentView = itemView;
        }

        public View getParentView()
        {

            return parentView;
        }

        public <E extends View> E $(@IdRes int id)
        {

            return (E) parentView.findViewById(id);
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
}