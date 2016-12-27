package com.yunspeak.travel.ui.me.messagecenter.appointmessage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.AiteMessageBean;
import com.yunspeak.travel.bean.AppointMessageBean;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailActivity;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.AppointWithMeDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost.CreatePostActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostOptionsDialog;
import com.yunspeak.travel.ui.find.active.activedetail.ActivateDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.EditAlbumActivity;
import com.yunspeak.travel.ui.me.myappoint.MyAppointActivity;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.view.ShowAllTextView;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.HowLongWithCurrentUtils;

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


    public AppointMessageHolder(View itemView, int messageType) {
        super(itemView);
        this.messageType = messageType;
    }


    @Override
    public void childBindView(final int position, Object data, final Context mContext) {
        String title="";
        String des="";
        String nickName="";
        String content="";
        String userImg="";
        String travelImg="";
        String userId = "";
        String tid = "";
        String type = "";
        String replyTime="";
        int floor = -1;
        String cid="";
        String pid="";
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
            mTvMessage.setLimitContent(content);
        }else {
            AiteMessageBean.DataBean dataBean = (AiteMessageBean.DataBean) data;
            title=dataBean.getTitle();
            des=dataBean.getTitle_desc();
            content = dataBean.getContent();
            mTvMessage.setLimitContent(AiteUtils.getSmiedTextWithAiteAndLinke(mContext,content,dataBean.getInform(),dataBean.getUrl()));
            nickName= dataBean.getNick_name();
            userImg = dataBean.getUser_img();
            travelImg = dataBean.getImg();
             userId = dataBean.getUser_id();
            tid = dataBean.getForum_id();
            type =dataBean.getType()+"";
            floor = dataBean.getFloor();
            cid = dataBean.getCid();
            pid = dataBean.getPid();
            replyTime = dataBean.getReply_time();

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
        if (mTvMessage.isShowAll()) {
            mTvCatMore.setVisibility(View.GONE);
        } else {
            mTvCatMore.setVisibility(View.VISIBLE);
            mTvCatMore.setText(R.string.cat_more);
            mTvCatMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTvMessage.isShowAll()) {
                        mTvMessage.setShowAll(false);
                        mTvCatMore.setText(R.string.cat_more);
                    } else {
                        mTvMessage.setShowAll(true);
                        mTvCatMore.setText(R.string.close_more);
                    }
                }
            });
        }
        FrescoUtils.displayIcon(mIvUserIcon,userImg );
        FrescoUtils.displayRoundIcon(mIvImage,travelImg );
        mTvTime.setText(HowLongWithCurrentUtils.getDesStringFromTime(replyTime,"yyyy.MM.dd HH:mm"));


        final String finalUserId = userId;
        mIvUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserCenterActivity.start(mContext, mIvUserIcon, finalUserId);
            }
        });
        final String finalType1 = type;
        final String finalTid1 = tid;
        final String finalPid = pid;
        final String finalUserId1 = userId;
        final int finalFloor = floor;
        final String finalCid = cid;
        mIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (messageType){
                    case AppointMessageAdapter.TYPE_APPOINT:
                        if (finalType1.equals("1")) {//一起玩
                            AppointTogetherDetailActivity.start(mContext, finalTid1);
                        } else if (finalType1.equals("2")){//找人带
                            AppointWithMeDetailActivity.start(mContext, finalTid1);
                        }else if (finalType1.equals("3")){
                            ActivateDetailActivity.start(mContext,finalTid1);
                        }
                        break;
                    default:
                        if (finalType1.equals("1") || finalType1.equals("2")) {//一起玩
                            CircleDetailActivity.start(mContext, finalCid);
                        }else if(finalType1.equals("3")){
                            AppointTogetherDetailActivity.start(mContext, finalTid1);
                        }else if (finalType1.equals("4")){
                            AppointWithMeDetailActivity.start(mContext, finalTid1);
                        }else if (finalType1.equals("5")){
                            TravelsDetailActivity.start(mContext, finalTid1,"游记详情");
                        }else if (finalType1.equals("6")){
                            DeliciousDetailActivity.start(mContext, finalTid1,"美食详情");
                        }else if (finalType1.equals("7")){
                            DestinationDetailActivity.start(mContext, finalTid1,"目的地详情");
                        }else if (finalType1.equals("8")){
                            EditAlbumActivity.start(mContext, finalTid1);
                        }
                        break;
                }

            }
        });
        final String finalNickName = nickName;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (messageType) {
                    case AppointMessageAdapter.TYPE_APPOINT:
                        Intent intent=new Intent(mContext,MyAppointActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case AppointMessageAdapter.TYPE_DISCUSS:
                    case AppointMessageAdapter.TYPE_AITE:
                        if (finalType1.equals("1") || finalType1.equals("2")){
                            showReplyDialog(mContext, finalTid1, finalPid, finalUserId1,finalFloor, finalCid, finalNickName);
                            break;
                        }
                    case AppointMessageAdapter.TYPE_ZAMBIA:
                        if (finalType1.equals("1")){
                            PostActivity.start(mContext, finalTid1);
                        }else if (finalType1.equals("2")){
                            PostActivity.start(mContext, finalTid1, finalFloor);
                        }else if(finalType1.equals("3'")){
                            AppointTogetherDetailActivity.start(mContext, finalTid1);
                        }else if (finalType1.equals("4")){
                            AppointWithMeDetailActivity.start(mContext, finalTid1);
                        }else if (finalType1.equals("5")){
                            TravelsDetailActivity.start(mContext, finalTid1,"游记详情");
                        }else if (finalType1.equals("6")){
                            DeliciousDetailActivity.start(mContext, finalTid1,"美食详情");
                        }else if (finalType1.equals("7")){
                            DestinationDetailActivity.start(mContext, finalTid1,"目的地详情");
                        }else if (finalType1.equals("8")){
                            EditAlbumActivity.start(mContext, finalTid1);
                        }
                        break;
                }


            }
        });

    }
    public void showReplyDialog(final Context context, final String fid, final String pid, final String userId, final int finalFloor, final String cid, final String nickName){
        PostOptionsDialog.showCommonDialog2(context, new ParentPopClick() {
            @Override
            public void onClick(int type) {
                 switch (type){
                     case PostOptionsDialog.TYPE_REPLY:
                         CreatePostActivity.start(context,cid,1,CreatePostActivity.REPLY_POST,fid,userId,pid,nickName);
                         break;
                     case PostOptionsDialog.TYPE_CAT_POST:
                         PostActivity.start(context,fid);
                         break;
                     case PostOptionsDialog.TYPE_CAT_DISCUSS:
                         if (finalFloor==0){
                             PostActivity.start(context,fid);
                         }else {
                             PostActivity.start(context, fid, finalFloor);
                         }
                         break;

                 }
            }
        });
    }

}
