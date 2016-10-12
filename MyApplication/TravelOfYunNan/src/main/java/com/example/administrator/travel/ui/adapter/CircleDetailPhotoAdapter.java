package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CircleDetailPhotoHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/8/17 0017.
 * 圈子页面的照片
 */
public class CircleDetailPhotoAdapter extends TravelBaseAdapter<String>{
    public CircleDetailPhotoAdapter(Context mContext, List<String> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, String item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {

        return new CircleDetailPhotoHolder(mContext);
    }
}
