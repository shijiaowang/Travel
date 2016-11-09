package com.yunspeak.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost.CreatePostActivity;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/11 0011.
 *
 * 文字回复  帖子楼层
 */
public class PostReplyTextHolder extends BaseRecycleViewHolder {
    private final String cId;
    @BindView(R.id.v_line) public View line;
    @BindView(R.id.iv_reply_icon)  SimpleDraweeView mIvReplyIcon;
    @BindView(R.id.tv_reply_nick_name) TextView mTvReplyNickName;
    @BindView(R.id.tv_reply_message) TextView mTvReplyMessage;
    @BindView(R.id.tv_floor_number) TextView mTvFloorNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_reply_time) TextView mTvReplyTime;
    @BindView(R.id.tv_love) TextView mTvLove;
    @BindView(R.id.iv_image) SimpleDraweeView mIvImage;
    @BindString(R.string.activity_circle_love_empty) String emptyLove;
    @BindString(R.string.activity_circle_love_full) String fullLove;

    public PostReplyTextHolder(View itemView,String cId) {
        super(itemView);
        this.cId = cId;
    }


    @Override
    public void childBindView(final int position, final Object data, final Context t) {
        if (data instanceof PostDetailBean.DataBean.ForumReplyBean) {
            final PostDetailBean.DataBean.ForumReplyBean forumReplyBean = (PostDetailBean.DataBean.ForumReplyBean) data;
            line.setVisibility(position==1?View.GONE:View.VISIBLE);
            mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OtherUserCenterActivity.start(t, v, forumReplyBean.getUser_id());
                }
            });
            if (StringUtils.isEmpty(forumReplyBean.getReply_img())){
                mIvImage.setVisibility(View.GONE);
            }else {
                mIvImage.setVisibility(View.VISIBLE);
                FrescoUtils.displayNormal(mIvImage,forumReplyBean.getReply_img(),600,450);
            }
            mTvReplyNickName.setText(forumReplyBean.getNick_name());
            FrescoUtils.displayIcon(mIvReplyIcon,forumReplyBean.getUser_img());
            AiteUtils.parseTextMessage(mTvReplyMessage,forumReplyBean.getInform(),forumReplyBean.getContent(),t);
            mTvReplyTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd HH:mm", forumReplyBean.getReply_time()));
            mTvFloorNumber.setText(forumReplyBean.getFloor() + "楼");
            mTvLoveNumber.setText(forumReplyBean.getLike_count());
            boolean equals = forumReplyBean.getIs_like().equals("1");
            mTvLove.setTextColor(equals? t.getResources().getColor(R.color.otherFf7f6c) : t.getResources().getColor(R.color.color969696));
            mTvLove.setText(equals?fullLove:emptyLove);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(t,forumReplyBean,position);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDialog(t, forumReplyBean,position);
                    return true;
                }
            });
            mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OtherUserCenterActivity.start(t,mIvReplyIcon,forumReplyBean.getUser_id());
                }
            });
            mIvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = forumReplyBean.getReply_img();
                    PostEvent postEvent=new PostEvent();
                    postEvent.setIsSuccess(true);
                    postEvent.setUrl(url);
                    postEvent.setType(IState.TYPE_OTHER);
                    EventBus.getDefault().post(postEvent);
                }
            });

        }
    }

    private void showDialog(final Context t, final PostDetailBean.DataBean.ForumReplyBean forumReplyBean, final int position) {
        PostOptionsDialog.showCommonDialog(t, new ParentPopClick() {
            @Override
            public void onClick(int type) {
                switch (type){
                    case PostOptionsDialog.TYPE_REPLY:
                        CreatePostActivity.start(t,cId,1,CreatePostActivity.REPLY_POST,forumReplyBean.getForum_id(),forumReplyBean.getUser_id(),forumReplyBean.getId());
                        break;
                    case PostOptionsDialog.TYPE_ZAN:
                        Map<String, String> end = MapUtils.Build().addKey().addUserId().addRUserId(forumReplyBean.getUser_id()).add(IVariable.REPLAY_ID, forumReplyBean.getId()).addFroumId(forumReplyBean.getForum_id()).end();
                        PostEvent event = new PostEvent();
                        event.setPosition(position);
                        XEventUtils.postUseCommonBackJson(IVariable.CIRCLE_RELPLY_LIKE,end,IState.TYPE_LIKE, event);
                        break;

                }
            }
        });
    }
}
