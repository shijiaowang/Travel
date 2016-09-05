package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PostDetail;
import com.example.administrator.travel.utils.FormatDateUtils;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class PostReplyTextHolder extends BaseHolder<Object> {
   @ViewInject(R.id.v_line)
   public View line;
    @ViewInject(R.id.iv_reply_icon)
    public ImageView mIvReplyIcon;
    @ViewInject(R.id.tv_reply_nick_name)
    private TextView mTvReplyNickName;
    @ViewInject(R.id.tv_reply_message)
    private TextView mTvReplyMessage;
    @ViewInject(R.id.tv_floor_number)
    private TextView mTvFloorNumber;
    @ViewInject(R.id.tv_love_number)
    private TextView mTvLoveNumber;
    @ViewInject(R.id.tv_reply_time)
    private TextView mTvReplyTime;
    @ViewInject(R.id.tv_love)
    private TextView mTvLove;
    public PostReplyTextHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {
        if (datas instanceof PostDetail.DataBean.ForumReplyBean) {
            PostDetail.DataBean.ForumReplyBean forumReplyBean = (PostDetail.DataBean.ForumReplyBean) datas;
            mTvReplyNickName.setText(forumReplyBean.getNick_name());
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
