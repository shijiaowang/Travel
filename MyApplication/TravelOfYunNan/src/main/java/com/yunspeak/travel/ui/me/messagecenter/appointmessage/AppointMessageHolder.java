package com.yunspeak.travel.ui.me.messagecenter.appointmessage;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailActivity;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.AppointWithMeDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.view.ShowAllTextView;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/26 0026.
 */
public class AppointMessageHolder extends BaseRecycleViewHolder {
    private final int messageType;
    @BindView(R.id.iv_user_icon)
    SimpleDraweeView mIvUserIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_cat_more)
    TextView mTvCatMore;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_message)
    ShowAllTextView mTvMessage;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.iv_image)
    SimpleDraweeView mIvImage;
    @BindView(R.id.tv_des)
    TextView mTvDes;
    @BindColor(R.color.Ffbf75)
    @ColorInt
    int yellow;
    @BindColor(R.color.otherTitleBg)
    @ColorInt
    int green;
    @BindColor(R.color.colorFf8076)
    @ColorInt
    int red;
    @BindColor(R.color.color97cb66)
    @ColorInt
    int green2;
    private String userId;
    private String tid;
    private String type;

    public AppointMessageHolder(View itemView, int messageType) {
        super(itemView);
        this.messageType = messageType;
    }


    @Override
    public void childBindView(int position,  Object data, final Context mContext) {
        String title="";
        String des="";
        String nickName="";
        String content="";
        String userImg="";
        String travelImg="";
        userId = "";
        tid = "";
        type = "";
        String replyTime="";
        if (messageType==AppointMessageAdapter.TYPE_APPOINT){
            AppointMessageBean.DataBean datas = (AppointMessageBean.DataBean) data;
            title=datas.getTitle();
            des=datas.getTitle_desc();
            content = datas.getContent();
            nickName= datas.getNick_name();
             userImg = datas.getUser_img();
            travelImg = datas.getTravel_img();
             userId = datas.getUser_id();
             tid = datas.getTid();
            type =datas.getType();
             replyTime = datas.getReply_time();
        }else {

        }
        mTvDes.setText(des);
        mTvName.setText(nickName);
        int color = green2;
        if (title.equals("报名了")) {
            color = green;
        } else if (title.equals("退出了")) {
            color = yellow;
        } else if (title.equals("拒绝了")) {
            color = red;
        } else if (title.equals("通过了")) {
            color = green2;
        } else if (title.equals("评论了")) {
            color = green2;
        } else if (title.equals("回复了")) {
            color = green;
        }
        mTvStatus.setTextColor(color);
        mTvStatus.setText(title);
        mTvMessage.setText(content);
        int lineCount = mTvMessage.getLineCount();
        if (lineCount <= 1) {
            mTvCatMore.setVisibility(View.GONE);
        } else {
            mTvCatMore.setVisibility(View.VISIBLE);
            mTvCatMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTvMessage.isShowAll()) {
                        mTvMessage.setShowAll(false);
                        mTvCatMore.setText(R.string.close_more);

                    } else {
                        mTvMessage.setShowAll(true);
                        mTvCatMore.setText(R.string.cat_more);
                    }
                }
            });
        }
        FrescoUtils.displayIcon(mIvUserIcon,userImg );
        FrescoUtils.displayRoundIcon(mIvImage,travelImg );
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:ss",replyTime));


        mIvUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserCenterActivity.start(mContext, mIvUserIcon, userId);
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (messageType) {
                    case AppointMessageAdapter.TYPE_APPOINT:

                        if (type.equals("1")) {//一起玩
                            AppointTogetherDetailActivity.start(mContext, tid);
                        } else {//找人带
                            AppointWithMeDetailActivity.start(mContext, tid);
                        }
                        break;
                    case AppointMessageAdapter.TYPE_AITE:
                        PostActivity.start(mContext, tid);
                        break;
                }


            }
        });

    }
}
