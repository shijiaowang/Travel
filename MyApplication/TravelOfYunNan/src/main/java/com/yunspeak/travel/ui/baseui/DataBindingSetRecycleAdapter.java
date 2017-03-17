package com.yunspeak.travel.ui.baseui;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.List;

/**
 * Created by wangyang on 2017/3/17.
 * setRecycle 不用静态方法设置属性
 */

public abstract class DataBindingSetRecycleAdapter<T> {
    @BindingAdapter("bind:setRecycleView")
    public abstract void setRecycleView(SetRecycleComponent dataBindingComponent, RecyclerView recyclerView, List<T> datas);
    @BindingAdapter("bind:init")
    public abstract void init(SetRecycleComponent dataBindingComponent, SwipeToLoadLayout swipeToLoadLayout,int style);

}
