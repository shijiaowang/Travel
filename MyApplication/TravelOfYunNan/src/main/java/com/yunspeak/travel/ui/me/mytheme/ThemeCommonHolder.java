package com.yunspeak.travel.ui.me.mytheme;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.InformBean;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.ui.find.findcommon.FindCommonActivity;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.ui.find.travels.TravelsActivity;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.x;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/3 0003.
 */
public class ThemeCommonHolder extends BaseRecycleViewHolder {


    @BindView(R.id.tv_delete) FontsIconTextView mTvDelete;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_name)  TextView mTvName;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.iv_user_icon)
    SimpleDraweeView mIvUserIcon;
    @BindView(R.id.tv_circle) TextView mTvCircle;
    @BindView(R.id.tv_discuss_number) TextView mTvDiscussNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.diss) TextView mTvDiscuss;


    public ThemeCommonHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void childBindView(final int position, final Object data1, final Context mContext) {
        if (data1 instanceof MyPostBean.DataBean) {
            final MyPostBean.DataBean datas=((MyPostBean.DataBean) data1);
            FrescoUtils.displayIcon(mIvUserIcon,datas.getImg());

            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm:ss", datas.getAdd_time()));
            mTvLoveNumber.setText(datas.getCount_like());
            mTvCircle.setText("#" + datas.getCname() + "#");
            AiteUtils.parseTextMessage(mTvContent,datas.getInform(),datas.getContent(),mContext);
            mTvDiscussNumber.setText(datas.getCount_reply());
            if (StringUtils.isEmpty(datas.getTitle())){
                mTvName.setVisibility(View.GONE);
            }else {
                mTvName.setVisibility(View.VISIBLE);
                AiteUtils.parseTextMessage(mTvName,null,datas.getTitle(),mContext);
            }
            mIvUserIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OtherUserCenterActivity.start(mContext,mIvUserIcon,datas.getUser_id());
                }
            });
            mTvCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CircleDetailActivity.start(mContext,datas.getCid());
                }
            });
            mTvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> end = MapUtils.Build().addKey().addFroumId(datas.getId()).addUserId().end();
                    MyPostEvent myPostEvent = new MyPostEvent();
                    myPostEvent.setPosition(position);
                    XEventUtils.postUseCommonBackJson(IVariable.DELETE_POST,end,IState.TYPE_DELETE, myPostEvent);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostActivity.start(mContext,datas.getId());
                }
            });
        }else if (data1 instanceof MyPublishBean.DataBean){
            final MyPublishBean.DataBean datas=((MyPublishBean.DataBean) data1);
            FrescoUtils.displayIcon(mIvUserIcon,datas.getImg());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm:ss", datas.getAdd_time()));
            mTvLoveNumber.setText(datas.getCount_like());
            mTvCircle.setText("#" + datas.getCname() + "#");
            AiteUtils.parseTextMessage(mTvContent,datas.getInform(),datas.getContent(),mContext);
            mTvContent.setText(AiteUtils.getSmiedTextWithAiteAndLinke(mContext,datas.getContent(),datas.getInform(),datas.getUrl()));
            mTvDiscuss.setVisibility(View.GONE);
            if (StringUtils.isEmpty(datas.getTitle())){
                mTvName.setVisibility(View.GONE);
            }else {
                mTvName.setVisibility(View.VISIBLE);
                AiteUtils.parseTextMessage(mTvName,null,datas.getTitle(),mContext);
            }
            mTvDiscussNumber.setVisibility(View.GONE);
            final String type = datas.getType();
            final int findType = datas.getFind_type();
            mTvCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type.equals("1")){//帖子
                         CircleDetailActivity.start(mContext,datas.getCid()+"");
                    }else {//发现
                        switch (findType){
                            case 1://游记
                                Intent intent=new Intent(mContext,TravelsActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 2://美食
                                FindCommonActivity.start(mContext,FindCommonActivity.DELICIOUS_NORMAL,0);
                                break;
                            case 3://目的地
                                FindCommonActivity.start(mContext,FindCommonActivity.DESTINATION_NORMAL,0);
                                break;
                        }
                    }
                }
            });
            mTvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> end = MapUtils.Build().addKey().addUserId().addRId(datas.getR_id()+"").addType(type).end();
                    MyPublishEvent event = new MyPublishEvent();
                    event.setPosition(position);
                    XEventUtils.postUseCommonBackJson(IVariable.DELETE_PUBLISH,end,IState.TYPE_DELETE, event);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if (type.equals("1")){//帖子
                      PostActivity.start(mContext,datas.getId());
                  }else {//发现
                      switch (findType){
                          case 1://游记
                              TravelsDetailActivity.start(mContext,datas.getId(),datas.getTitle());
                              break;
                          case 2://美食
                              DeliciousDetailActivity.start(mContext,datas.getId(),datas.getTitle());
                              break;
                          case 3://目的地
                              DestinationDetailActivity.start(mContext,datas.getId(),datas.getTitle());
                              break;
                      }
                  }
                }
            });
        }
    }

}
