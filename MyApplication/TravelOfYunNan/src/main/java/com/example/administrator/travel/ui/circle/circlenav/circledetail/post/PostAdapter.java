package com.example.administrator.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/7/11 0011.
 */
public class PostAdapter extends TravelBaseAdapter<Object> {
    public PostAdapter(Context mContext, List<Object> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        PostDetailBean.DataBean.ForumReplyBean forumReplyBean=null;
        if (mDatas.get(position) instanceof PostDetailBean.DataBean.ForumReplyBean){
            forumReplyBean= (PostDetailBean.DataBean.ForumReplyBean) mDatas.get(position);
        }
        if (position == 0) {
            return TYPE_POST_OP;
        } else if (forumReplyBean!=null && !(forumReplyBean.getPid().equals("0"))) {
            return TYPE_POST_USER;
        } else if (forumReplyBean!=null){
            return TYPE_POST_NORMAL;
        }else {
            return -1;
        }
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final Object item, int position) {
        if (baseHolder instanceof PostReplyUserHolder) {
            PostReplyUserHolder postReplyImageHolder = (PostReplyUserHolder) baseHolder;
            if (position==1)postReplyImageHolder.line.setVisibility(View.GONE);
            postReplyImageHolder.mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostDetailBean.DataBean.ForumReplyBean item1 = (PostDetailBean.DataBean.ForumReplyBean) item;
                    OtherUserCenterActivity.start(mContext,v,item1.getUser_id());

                }
            });
        }
        if (baseHolder instanceof PostReplyTextHolder) {
            PostReplyTextHolder postReplyTextHolder = (PostReplyTextHolder) baseHolder;
            if (position==1)postReplyTextHolder.line.setVisibility(View.GONE);
            postReplyTextHolder.mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostDetailBean.DataBean.ForumReplyBean item1 = (PostDetailBean.DataBean.ForumReplyBean) item;
                    OtherUserCenterActivity.start(mContext,v,item1.getUser_id());
                }
            });
        }
    }


    @Override
    protected BaseHolder initHolder(int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TravelBaseAdapter.TYPE_POST_USER) {
            return new PostReplyUserHolder(super.mContext);
        } else if (itemViewType == TYPE_POST_NORMAL) {
            return new PostReplyTextHolder(super.mContext);
        } else {
            return new PostOpHolder(mContext);
        }

    }
}
