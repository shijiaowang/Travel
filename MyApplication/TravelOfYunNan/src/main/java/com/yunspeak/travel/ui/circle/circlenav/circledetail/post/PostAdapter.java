package com.yunspeak.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.MyWitheMeDecoration;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/11 0011.
 */
public class PostAdapter extends BaseRecycleViewAdapter<Object> {
    public static final int TYPE_POST_OP = 0;
    public static final int TYPE_POST_USER = 1;
    public static final int TYPE_POST_NORMAL = 2;
    private final String cId;

    public PostAdapter(List<Object> mDatas, Context mContext,String cId) {
        super(mDatas, mContext);
        this.cId = cId;
    }

    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_POST_USER) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_post_reply_user, parent, false);
            return new PostReplyUserHolder(inflate,cId);
        } else if (viewType == TYPE_POST_NORMAL) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_post_reply, parent, false);
            return new PostReplyTextHolder(inflate,cId);
        } else {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_post_op, parent, false);
            return new PostOpHolder(inflate);
        }

    }
    @Override
    public int getItemViewType(int position) {
        PostDetailBean.DataBean.ForumReplyBean forumReplyBean = null;
        Object o = mDatas.get(position);
        if (o instanceof PostDetailBean.DataBean.ForumReplyBean) {
            forumReplyBean = (PostDetailBean.DataBean.ForumReplyBean) mDatas.get(position);
            if (forumReplyBean.getPid().equals("0")) {
                return TYPE_POST_NORMAL;
            } else{
                return TYPE_POST_USER;
            }
        }else {
            return TYPE_POST_OP;
        }
    }
}


