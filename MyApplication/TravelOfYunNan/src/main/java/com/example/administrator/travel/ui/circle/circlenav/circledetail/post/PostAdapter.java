package com.example.administrator.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.me.myappoint.withmeselect.MyWitheMeDecoration;
import com.example.administrator.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.StringUtils;
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

    public PostAdapter(List<Object> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TravelBaseAdapter.TYPE_POST_USER) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_post_reply_user, parent, false);
            return new PostReplyUserHolder(inflate);
        } else if (viewType == TYPE_POST_NORMAL) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_post_reply, parent, false);
            return new PostReplyTextHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_post_op, parent, false);
            return new PostOpHolder(inflate);
        }

    }

    @Override
    public int getItemViewType(int position) {
        PostDetailBean.DataBean.ForumReplyBean forumReplyBean = null;
        if (mDatas.get(position) instanceof PostDetailBean.DataBean.ForumReplyBean) {
            forumReplyBean = (PostDetailBean.DataBean.ForumReplyBean) mDatas.get(position);
        }
        if (position == 0) {
            return TYPE_POST_OP;
        } else if (forumReplyBean != null && !(forumReplyBean.getPid().equals("0"))) {
            return TYPE_POST_USER;
        } else if (forumReplyBean != null) {
            return TYPE_POST_NORMAL;
        } else {
            return -1;
        }
    }
}


