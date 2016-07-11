package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.PostOpHolder;
import com.example.administrator.travel.ui.adapter.holer.PostReplyImageHolder;
import com.example.administrator.travel.ui.adapter.holer.PostReplyTextHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class PostAdapter extends TravelBaseAdapter<Object> {
    public PostAdapter(Context mContext, List<Object> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 6;
    }


    @Override
    protected BaseHolder initHolder(int position) {
        if (position==0){
            return new PostOpHolder(super.mContext);
        }else if (position%2==0){
            return new PostReplyImageHolder(super.mContext);
        }else {
            return new PostReplyTextHolder(super.mContext);
        }
    }
}
