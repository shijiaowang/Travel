package com.example.administrator.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/11 0011.
 *
 * 文字回复  帖子楼层
 */
public class PostReplyTextHolder extends BaseHolder<Object> {
   @BindView(R.id.v_line) public View line;
    @BindView(R.id.iv_reply_icon)  SimpleDraweeView mIvReplyIcon;
    @BindView(R.id.tv_reply_nick_name) TextView mTvReplyNickName;
    @BindView(R.id.tv_reply_message) TextView mTvReplyMessage;
    @BindView(R.id.tv_floor_number) TextView mTvFloorNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_reply_time) TextView mTvReplyTime;
    @BindView(R.id.tv_love) TextView mTvLove;
    public PostReplyTextHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {
        if (datas instanceof PostDetailBean.DataBean.ForumReplyBean) {
            PostDetailBean.DataBean.ForumReplyBean forumReplyBean = (PostDetailBean.DataBean.ForumReplyBean) datas;
            mTvReplyNickName.setText(forumReplyBean.getNick_name());
            FrescoUtils.displayIcon(mIvReplyIcon,forumReplyBean.getUser_img());
            mTvReplyMessage.setText(forumReplyBean.getContent());
            mTvReplyTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd HH:mm", forumReplyBean.getReply_time()));
            mTvFloorNumber.setText(forumReplyBean.getFloor() + "楼");
            mTvLoveNumber.setText(forumReplyBean.getLike_count());
            mTvLove.setTextColor(forumReplyBean.getIs_like().equals("1") ? mContext.getResources().getColor(R.color.otherFf7f6c) : mContext.getResources().getColor(R.color.color969696));
        }

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_post_reply);
    }
}