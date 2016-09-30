package com.example.administrator.travel.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.TravelReplyBean;
import com.example.administrator.travel.event.DetailCommonEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.baseui.OtherUserCenterActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.DestinationDetailReplyTextHolder;
import com.example.administrator.travel.ui.adapter.holer.DestinationDetailReplyUserHolder;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/25 0025.
 * 发现评论公共adapter
 */
public class DiscussCommonAdapter extends TravelBaseAdapter<TravelReplyBean> {


    private  String typeDestination;

    public DiscussCommonAdapter(Context mContext, List<TravelReplyBean> mDatas, String typeDestination) {
        super(mContext, mDatas);

        this.typeDestination = typeDestination;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        TravelReplyBean forumReplyBean = mDatas.get(position);
        if (forumReplyBean != null && !(forumReplyBean.getPid().equals("0"))) {
            return TYPE_POST_USER;
        } else if (forumReplyBean != null) {
            return TYPE_POST_NORMAL;
        } else {
            return -1;
        }
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final TravelReplyBean item, final int position) {
        if (baseHolder instanceof DestinationDetailReplyUserHolder) {
            final DestinationDetailReplyUserHolder detailReplyUserHolder = (DestinationDetailReplyUserHolder) baseHolder;
            detailReplyUserHolder.mTvLove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickReq(item, detailReplyUserHolder.mTvLove, position);
                }
            });
            detailReplyUserHolder.mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startCatOther(v);
                }
            });
        }
        if (baseHolder instanceof DestinationDetailReplyTextHolder) {
            final DestinationDetailReplyTextHolder destinationDetailReplyTextHolder = (DestinationDetailReplyTextHolder) baseHolder;
            destinationDetailReplyTextHolder.mTvLove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickReq(item, destinationDetailReplyTextHolder.mTvLove, position);
                }
            });
            destinationDetailReplyTextHolder.mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startCatOther(v);
                }
            });
        }
    }

    private void clickReq(TravelReplyBean item, FontsIconTextView mTvLove, int position) {
        //无法取消赞
        if (!item.getIs_like().equals("1")) {
            mTvLove.setTextColor(mContext.getResources().getColor(R.color.otherFf7f6c));
            Map<String, String> likeMap = MapUtils.Build().addKey(mContext).addFId(item.getF_id()).addUserId().
                   addRId(item.getId()).addType(typeDestination).addRUserId(item.getUser_id()).
                    end();
            DetailCommonEvent detailCommonEvent = new DetailCommonEvent();
            detailCommonEvent.setClickPosition(position);
            XEventUtils.postUseCommonBackJson(IVariable.FIND_CLICK_LIKE, likeMap, LoadingBarBaseActivity.TYPE_LIKE_DISCUSS, detailCommonEvent);
        }else {
            ToastUtils.showToast("你已经点过赞了");
        }
    }

    private void startCatOther(View v) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
        Intent intent = new Intent(mContext, OtherUserCenterActivity.class);
        ActivityCompat.startActivity(((Activity) mContext), intent, compat.toBundle());
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
