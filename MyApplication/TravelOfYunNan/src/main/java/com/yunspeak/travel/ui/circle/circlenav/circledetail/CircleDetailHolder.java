package com.yunspeak.travel.ui.circle.circlenav.circledetail;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/11 0011.
 * 圈子详情
 */
class CircleDetailHolder extends BaseRecycleViewHolder<CircleDetailBean.DataBean.BodyBean> {
    @BindView(R.id.iv_user_icon) SimpleDraweeView mIvUserIcon;
    @BindView(R.id.tv_user_nick_name) TextView mTvUserNickName;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.tv_discuss_number) TextView mTvDiscussNumber;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.rv_photo)
    RecyclerView mRvPhoto;
    @BindView(R.id.tv_icon_love) FontsIconTextView mTvIconLove;
    @BindColor(R.color.otherFf7f6c) @ColorInt
    int loveColor;
    @BindColor(R.color.colorA1a1a1) @ColorInt int notLoveColor;
    CircleDetailPhotoAdapter circleDetailPhotoAdapter;
    public CircleDetailHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(final int position, final CircleDetailBean.DataBean.BodyBean datas, final Context mContext) {
        FrescoUtils.displayIcon(mIvUserIcon,datas.getUser_img());
        mIvUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserCenterActivity.start(mContext,mIvUserIcon,datas.getUser_id());
            }
        });
        mTvDiscussNumber.setText(datas.getCount_reply());
        if (StringUtils.isEmpty(datas.getTitle())) {
            mTvTitle.setVisibility(View.GONE);
        } else {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(datas.getTitle());
        }
        boolean isLike = datas.getIs_like().equals("1");
        mTvIconLove.setTextColor(isLike?loveColor:notLoveColor);
     mTvLoveNumber.setTextColor(isLike?loveColor:notLoveColor);
        String countLike = datas.getCount_like();
        if (StringUtils.isEmpty(countLike)){
            countLike="0";
        }
        mTvLoveNumber.setText(countLike);
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-M-dd HH:mm", datas.getTime()));
       mTvUserNickName.setText(datas.getNick_name());
        mTvContent.setText(datas.getContent());
        mTvIconLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!datas.getIs_like().equals("1")) {
                    //点赞
                    CircleDetailEvent circleDetailEvent = new CircleDetailEvent();
                    circleDetailEvent.setPosition(position);
                    Map<String, String> likeMap = MapUtils.Build().addKey(mContext).addFroumId(datas.getId()).addUserId().addRUserId(datas.getUser_id()).end();
                    XEventUtils.postUseCommonBackJson(IVariable.CIRCLE_LIKE_POST, likeMap, CircleDetailActivity.TYPE_LIKE,circleDetailEvent);
                }else {
                    ToastUtils.showToast("你已经点过赞了");
                }

            }
        });

        final String imageUrl = datas.getForum_img();
        if (!StringUtils.isEmpty(imageUrl)) {
            mRvPhoto.setVisibility(View.VISIBLE);
            String[] split = imageUrl.split(",");
            List<String> list = Arrays.asList(split);
            if (circleDetailPhotoAdapter==null){
                circleDetailPhotoAdapter = new CircleDetailPhotoAdapter(list,mContext,datas.getId(),datas.getCid());
                mRvPhoto.addItemDecoration(new CircleDetailDecoration(6));
                mRvPhoto.setAdapter(circleDetailPhotoAdapter);
                LinearLayoutManager linearLayoutManager=new GridLayoutManager(mContext,3);
                //响应toolebar收缩
                mRvPhoto.setHasFixedSize(true);
                mRvPhoto.setNestedScrollingEnabled(false);
                linearLayoutManager.setSmoothScrollbarEnabled(false);
                mRvPhoto.setLayoutManager(linearLayoutManager);
            }else {
                circleDetailPhotoAdapter.setList(list);
            }
        } else {
            mRvPhoto.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               PostActivity.start(mContext,datas.getId());
            }
        });
    }
}
