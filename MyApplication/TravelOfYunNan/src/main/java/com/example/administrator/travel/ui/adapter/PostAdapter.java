package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.PostOpHolder;
import com.example.administrator.travel.ui.adapter.holer.PostReplyImageHolder;
import com.example.administrator.travel.ui.adapter.holer.PostReplyTextHolder;
import com.example.administrator.travel.utils.LogUtils;

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
        return 30;
    }


    @Override
    protected BaseHolder initHolder(int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType==TravelBaseAdapter.TYPE_POST_OP){
            return new PostOpHolder(super.mContext);
        }else if (itemViewType==TravelBaseAdapter.TYPE_POST_IMG){
            return new PostReplyImageHolder(super.mContext);
        }else {
            return new PostReplyTextHolder(super.mContext);
        }
    }
}
