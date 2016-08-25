package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.DestinationDetail;
import com.example.administrator.travel.bean.PostDetail;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.StringUtils;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/11 0011.
 * 回复其他楼层
 */
public class DestinationDetailReplyUserHolder extends BaseHolder<DestinationDetail.DataBean.TravelReplyBean> {
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


    @ViewInject(R.id.tv_reply_content)
    private TextView mTvReplyContent;
    @ViewInject(R.id.tv_reply_name)
    private TextView mTvReplyName;
    @ViewInject(R.id.tv_reply_floor_number)
    private TextView mTvReplyFloorNumber;

    public DestinationDetailReplyUserHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(DestinationDetail.DataBean.TravelReplyBean datas, Context mContext) {
        mTvReplyNickName.setText(datas.getNick_name());
        mTvReplyMessage.setText(datas.getContent());
        mTvReplyTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd HH:mm", datas.getReply_time()));
        mTvFloorNumber.setText(datas.getFloor() + "楼");
        mTvLoveNumber.setText(datas.getLike_count());
        mTvLove.setTextColor(datas.getIs_like().equals("1") ? mContext.getResources().getColor(R.color.otherFf7f6c) : mContext.getResources().getColor(R.color.color969696));
        DestinationDetail.DataBean.TravelReplyBean.ReplyBean reply = datas.getReply();
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
        View inflate = inflateView(R.layout.item_activity_post_reply_user);
        return inflate;
    }
}
