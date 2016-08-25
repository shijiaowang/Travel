package com.example.administrator.travel.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.example.administrator.travel.bean.DestinationDetail;
import com.example.administrator.travel.ui.activity.OtherUserCenterActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.DestinationDetailReplyTextHolder;
import com.example.administrator.travel.ui.adapter.holer.DestinationDetailReplyUserHolder;


import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 * 发现评论公共adapter
 */
public class DiscussCommonAdapter extends TravelBaseAdapter<DestinationDetail.DataBean.TravelReplyBean> {


    public DiscussCommonAdapter(Context mContext, List<DestinationDetail.DataBean.TravelReplyBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        DestinationDetail.DataBean.TravelReplyBean forumReplyBean = mDatas.get(position);
        if (forumReplyBean != null && !(forumReplyBean.getPid().equals("0"))) {
            return TYPE_POST_USER;
        } else if (forumReplyBean != null) {
            return TYPE_POST_NORMAL;
        } else {
            return -1;
        }
    }

    @Override
    protected void initListener(BaseHolder baseHolder, DestinationDetail.DataBean.TravelReplyBean item, int position) {
        if (baseHolder instanceof DestinationDetailReplyUserHolder) {
            DestinationDetailReplyUserHolder detailReplyUserHolder = (DestinationDetailReplyUserHolder) baseHolder;
            detailReplyUserHolder.mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                    Intent intent = new Intent(mContext, OtherUserCenterActivity.class);
                    ActivityCompat.startActivity(((Activity) mContext), intent, compat.toBundle());
                }
            });
        }
        if (baseHolder instanceof DestinationDetailReplyTextHolder) {
            DestinationDetailReplyTextHolder destinationDetailReplyTextHolder = (DestinationDetailReplyTextHolder) baseHolder;
            destinationDetailReplyTextHolder.mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
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
        if (itemViewType == TravelBaseAdapter.TYPE_POST_USER) {
            return new DestinationDetailReplyUserHolder(mContext);
        } else {
            return new DestinationDetailReplyTextHolder(mContext);
        }
    }
}
