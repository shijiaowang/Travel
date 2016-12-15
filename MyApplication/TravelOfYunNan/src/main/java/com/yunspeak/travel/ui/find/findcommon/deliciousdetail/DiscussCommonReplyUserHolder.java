package com.yunspeak.travel.ui.find.findcommon.deliciousdetail;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DetailCommonEvent;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/11 0011.
 * 回复其他楼层
 */
public class DiscussCommonReplyUserHolder extends BaseRecycleViewHolder<TravelReplyBean> {
    private final String typeDestination;
    @BindView(R.id.v_line)public View line;
    @BindView(R.id.iv_reply_icon) public SimpleDraweeView mIvReplyIcon;
    @BindView(R.id.tv_reply_nick_name) TextView mTvReplyNickName;
    @BindView(R.id.tv_reply_message) TextView mTvReplyMessage;
    @BindView(R.id.tv_floor_number) TextView mTvFloorNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_reply_time) TextView mTvReplyTime;
    @BindView(R.id.tv_reply_content)TextView mTvReplyContent;
    @BindView(R.id.tv_reply_name)TextView mTvReplyName;
    @BindView(R.id.tv_reply_floor_number) TextView mTvReplyFloorNumber;
    @BindView(R.id.iv_image) public SimpleDraweeView mIvImage;
    @BindString(R.string.activity_circle_love_empty) String emptyLove;
    @BindString(R.string.activity_circle_love_full) String fullLove;

    public DiscussCommonReplyUserHolder(View itemView, String typeDestination) {
        super(itemView);
        this.typeDestination = typeDestination;
    }



    @Override
    public void childBindView(final int position, final TravelReplyBean datas, final Context mContext) {
        mIvImage.setVisibility(View.GONE);
        mTvReplyNickName.setText(datas.getNick_name());
        Spannable span = EaseSmileUtils.getSmiledText(mContext, datas.getContent());
        // 设置内容
        mTvReplyMessage.setText(span, TextView.BufferType.SPANNABLE);
        FrescoUtils.displayIcon(mIvReplyIcon,datas.getUser_img());
        mTvReplyTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd HH:mm", datas.getReply_time()));
        mTvFloorNumber.setText(datas.getFloor() + "楼");

        TravelReplyBean.ReplyBean reply = datas.getReply();
        Spannable smiledText = EaseSmileUtils.getSmiledText(mContext, datas.getContent());
        mTvReplyContent.setText(smiledText, TextView.BufferType.SPANNABLE);
        mTvReplyName.setText(reply.getNick_name());
        mTvReplyFloorNumber.setText(reply.getFloor() + "楼");
        boolean equals = datas.getIs_like().equals("1");
        AiteUtils.setIconText(equals,equals?fullLove:emptyLove,-1,datas.getLike_count(),mContext,mTvLoveNumber,R.dimen.x14sp);
        mTvLoveNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickReq(datas, position);
            }
        });
        mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserCenterActivity.start(mContext,v,datas.getUser_id());
            }
        });
    }
    private void clickReq(TravelReplyBean item,int position) {
        //无法取消赞
        if (!item.getIs_like().equals("1")) {
            Map<String, String> likeMap = MapUtils.Build().addKey().addFId(item.getF_id()).addUserId().
                    addRId(item.getId()).addType(typeDestination).addRUserId(item.getUser_id()).
                    end();
            DetailCommonEvent detailCommonEvent = new DetailCommonEvent();
            detailCommonEvent.setClickPosition(position);
            XEventUtils.postUseCommonBackJson(IVariable.FIND_CLICK_LIKE, likeMap, IState.TYPE_LIKE_DISCUSS, detailCommonEvent);
        }else {
            ToastUtils.showToast("你已经点过赞了");
        }
    }
}
