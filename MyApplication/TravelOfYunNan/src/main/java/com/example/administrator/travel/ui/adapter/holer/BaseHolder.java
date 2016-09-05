package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

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

    public void setDatas(T datas, int position){
        this.datas=datas;
        if (datas==null){
            return;
        }
        initItemDatas(datas,mContext,position);
    }
  //赋值item
    protected abstract void initItemDatas(T datas, Context mContext, int position);

    public T getDatas(){
        return this.datas;
    }

    public abstract View initRootView(Context mContext);

    /**
     * 初始化ID
     * @param resId
     */
    public View  inflateView(int resId){
        return View.inflate(mContext, resId, null);
    }

    protected ImageOptions getImageOptions(int width,int height){
        return new ImageOptions.Builder().setSize(DensityUtil.dip2px(width), DensityUtil.dip2px(height)).setCrop(true).build();
    }
    protected ImageOptions getUserImageOptions(int width,int height){
        return new ImageOptions.Builder().setSize(DensityUtil.dip2px(width), DensityUtil.dip2px(height)).
                setCircular(true).setRadius(50).
                setCrop(true).build();
    }

}
