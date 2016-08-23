package com.example.administrator.travel.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.example.administrator.travel.ui.activity.OtherUserCenterActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.PostReplyTextHolder;
import com.example.administrator.travel.ui.adapter.holer.PostReplyUserHolder;

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

         if (position<4){
            return TYPE_POST_USER;
        }else {
            return TYPE_POST_NORMAL;
        }
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {
        if (baseHolder instanceof PostReplyUserHolder){
            PostReplyUserHolder postReplyImageHolder = (PostReplyUserHolder) baseHolder;
            postReplyImageHolder.mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                    Intent intent = new Intent(mContext, OtherUserCenterActivity.class);
                    ActivityCompat.startActivity(((Activity) mContext), intent, compat.toBundle());
                }
            });
        }
    }


    @Override
    protected BaseHolder initHolder(int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType==TravelBaseAdapter.TYPE_POST_USER){
            return new PostReplyUserHolder(super.mContext);
        }else {
            return new PostReplyTextHolder(super.mContext);
        }
    }
}
