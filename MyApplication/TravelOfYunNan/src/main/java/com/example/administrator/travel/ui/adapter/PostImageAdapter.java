package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.PostImageHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class PostImageAdapter extends TravelBaseAdapter<String>{
    public PostImageAdapter(Context mContext, List<String> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, String item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new PostImageHolder(mContext);
    }
}
