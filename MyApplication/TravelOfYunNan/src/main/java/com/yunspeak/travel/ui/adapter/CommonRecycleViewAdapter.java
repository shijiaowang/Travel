package com.yunspeak.travel.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by wangyang on 2017/3/13.
 * 通用recycleviewadapter
 */

public class CommonRecycleViewAdapter<T> extends BaseRecycleViewAdapter<T>{
    private int layoutId;
    private int brId;

    public CommonRecycleViewAdapter(List<T> datas, int brId, int layoutId) {
        super(datas);
        this.brId = brId;
        this.layoutId = layoutId;
    }

    @Override
    protected void onBindOtherHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  CommonRecycleHolder) {
            CommonRecycleHolder commonRecycleHolder = (CommonRecycleHolder) holder;
            ViewDataBinding binding = commonRecycleHolder.getBinding();
            binding.setVariable(brId, datas.get(holder.getAdapterPosition()));
            onBind(binding);
            binding.executePendingBindings();
        }
    }

    @Override
    protected RecyclerView.ViewHolder otherViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding inflate = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false);
        return new CommonRecycleHolder(inflate);
    }

    /**
     * 绑定其他事件
     * @param binding
     */
    protected  void onBind(ViewDataBinding binding) {

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
