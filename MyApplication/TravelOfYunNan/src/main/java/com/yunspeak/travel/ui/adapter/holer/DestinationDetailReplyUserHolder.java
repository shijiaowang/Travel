package com.yunspeak.travel.ui.adapter.holer;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.TravelReplyBean;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/11 0011.
 * 回复其他楼层
 */
public class DestinationDetailReplyUserHolder extends BaseHolder<TravelReplyBean> {
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

    public DestinationDetailReplyUserHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(TravelReplyBean datas, Context mContext, int position) {
        mTvReplyNickName.setText(datas.getNick_name());
        mTvReplyMessage.setText(datas.getContent());
        FrescoUtils.displayIcon(mIvReplyIcon,datas.getUser_img());
        mTvReplyTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd HH:mm", datas.getReply_time()));
        mTvFloorNumber.setText(datas.getFloor() + "楼");
        mTvLoveNumber.setText(datas.getLike_count());
        mTvLove.setTextColor(datas.getIs_like().equals("1") ? mContext.getResources().getColor(R.color.otherFf7f6c) : mContext.getResources().getColor(R.color.color969696));
        TravelReplyBean.ReplyBean reply = datas.getReply();
        if (!StringUtils.isEmpty(reply.getReply_img())) {
            String content = reply.getContent() + "【图片】";
            mTvReplyContent.setText(content);
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
    }
    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_post_reply_user);
    }
}