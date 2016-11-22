package com.yunspeak.travel.ui.find.findcommon.deliciousdetail;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.yunspeak.travel.R;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.adapter.holer.SomeTextClick;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Map;

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
    @BindView(R.id.tv_love)public FontsIconTextView mTvLove;
    @BindView(R.id.tv_reply_content)TextView mTvReplyContent;
    @BindView(R.id.tv_reply_name)TextView mTvReplyName;
    @BindView(R.id.tv_reply_floor_number) TextView mTvReplyFloorNumber;
    @BindView(R.id.iv_image) public SimpleDraweeView mIvImage;

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
        mTvLoveNumber.setText(datas.getLike_count());
        mTvLove.setTextColor(datas.getIs_like().equals("1") ? mContext.getResources().getColor(R.color.otherFf7f6c) : mContext.getResources().getColor(R.color.color969696));
        TravelReplyBean.ReplyBean reply = datas.getReply();
        if (!StringUtils.isEmpty(reply.getReply_img())) {
            String content = reply.getContent() + "【图片】";
            Spannable replySpan = EaseSmileUtils.getSmiledText(mContext, content);
            // 设置内容
            mTvReplyMessage.setText(replySpan, TextView.BufferType.SPANNABLE);
            mTvReplyContent.setMovementMethod(LinkMovementMethod.getInstance());
            SpannableStringBuilder spannable = new SpannableStringBuilder(content);
            spannable.setSpan(new SomeTextClick(mContext, ""), content.length() - 4, content.length()
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvReplyContent.setText(spannable);
        } else {
            mTvReplyContent.setText(reply.getContent());
        }
        mTvReplyName.setText(reply.getNick_name());
        mTvReplyFloorNumber.setText(reply.getFloor() + "楼");
        mTvLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickReq(datas,mTvLove, position,mContext);
            }
        });
        mIvReplyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserCenterActivity.start(mContext,v,datas.getUser_id());
            }
        });
    }
    private void clickReq(TravelReplyBean item, FontsIconTextView mTvLove, int position,Context mContext) {
        //无法取消赞
        if (!item.getIs_like().equals("1")) {
            mTvLove.setTextColor(mContext.getResources().getColor(R.color.otherFf7f6c));
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
