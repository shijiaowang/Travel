package com.yunspeak.travel.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.ui.view.PagerCursorView;

import java.util.List;

/**
 * Created by wangyang on 2017/3/13.
 * 无线轮播通用adapter
 */

public class CommonPagerCursorAdapter<T> extends PagerCursorView.CursorPagerAdapter<T> {
    private  int layoutId;
    private  int brId;
    private LayoutInflater layoutInflater;

    public CommonPagerCursorAdapter(List<T> data, int layoutId, int brId) {
        super(data);
        this.layoutId = layoutId;
        this.brId = brId;
    }
    public void reset(List<T> t){
        if (t!=null){
            data=t;
            notifyDataSetChanged();
        }
    }
    @Override
    public Object inflateView(ViewGroup container, int position) {
        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(container.getContext());
        }
        ViewDataBinding inflate = DataBindingUtil.inflate(layoutInflater, layoutId, container, false);
        inflate.setVariable(brId,data.get(position));
        container.addView(inflate.getRoot());
        return inflate.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }


}
