package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.travel.ui.activity.OtherUserCenterActivity;
import com.example.administrator.travel.ui.activity.dragtopview.MainActivity;
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
        return 30;
    }
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {

        if (position==0){
            return TYPE_POST_OP;
        }else if (position<4){
            return TYPE_POST_IMG;
        }else {
            return TYPE_POST_NORMAL;
        }
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item) {
        if (baseHolder instanceof PostReplyImageHolder){

            PostReplyImageHolder postReplyImageHolder = (PostReplyImageHolder) baseHolder;
            postReplyImageHolder.mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   mContext.startActivity(new Intent(mContext, MainActivity.class));
                }
            });
        }
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
