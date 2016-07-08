package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2016/7/8 0008.
 * 抽取的holder基类
 */
public abstract class BaseHolder<T> {
    public Context mContext;

    public View root;
    public T datas;
    public BaseHolder(Context context) {
        mContext=context;
        root = initRootView(mContext);
        root.setTag(this);
    }
    //初始化布局
    public View getRootView(){
        return root;
    }

    protected void setDatas(T datas){
        this.datas=datas;
        initItemDatas(datas,mContext);
    }
  //赋值item
    protected abstract void initItemDatas(T datas, Context mContext);

    public T getDatas(){
        return this.datas;
    }

    public abstract View initRootView(Context mContext);


}
