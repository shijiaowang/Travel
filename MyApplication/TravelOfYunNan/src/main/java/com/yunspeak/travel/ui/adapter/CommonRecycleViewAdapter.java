package com.yunspeak.travel.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangyang on 2017/3/13.
 * 通用recycleviewadapter
 */

public class CommonRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private int layoutId;
    private int brId;
    private List<T> datas=null;
    private LayoutInflater layoutInflater;

    public CommonRecycleViewAdapter(List<T> datas, int brId, int layoutId) {
        this.datas = datas;
        this.brId = brId;
        this.layoutId = layoutId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        ViewDataBinding inflate = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false);
        return new CommonRecycleHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof  CommonRecycleHolder) {
                CommonRecycleHolder commonRecycleHolder = (CommonRecycleHolder) holder;
                ViewDataBinding binding = commonRecycleHolder.getBinding();
                binding.setVariable(brId, datas.get(holder.getAdapterPosition()));
                binding.executePendingBindings();
            }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
   private static class CommonRecycleHolder extends RecyclerView.ViewHolder{

        private  ViewDataBinding binding;

        public CommonRecycleHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        private ViewDataBinding getBinding(){
            return binding;
        }
    }
}
