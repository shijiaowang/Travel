package com.example.administrator.travel.ui.circle.circlenav.circledetail;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.baseui.LoadMoreRecycleViewAdapter;
import com.example.administrator.travel.ui.me.myappoint.withmeselect.MyWitheMeDecoration;
import com.example.administrator.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.ToShowAllGridView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/11 0011.
 */
public class CircleDetailAdapter extends BaseRecycleViewAdapter<CircleDetailBean.DataBean.BodyBean> {

    public CircleDetailAdapter(List<CircleDetailBean.DataBean.BodyBean> list, Context mContext) {
        super(list, mContext);
    }

    @Override
    protected void childBindView(RecyclerView.ViewHolder holder, final int position, final CircleDetailBean.DataBean.BodyBean datas) {
        final CircleDetailHolder circleDetailHolder = (CircleDetailHolder) holder;
        FrescoUtils.displayIcon(circleDetailHolder.mIvUserIcon,datas.getUser_img());
        circleDetailHolder.mIvUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserCenterActivity.start(mContext,circleDetailHolder.mIvUserIcon,datas.getUser_id());
            }
        });
        circleDetailHolder.mTvDiscussNumber.setText(datas.getCount_reply());
        circleDetailHolder.mTvLoveNumber.setText(datas.getCount_like());
        if (StringUtils.isEmpty(datas.getTitle())) {
            circleDetailHolder.mTvTitle.setVisibility(View.GONE);
        } else {
            circleDetailHolder.mTvTitle.setVisibility(View.VISIBLE);
            circleDetailHolder.mTvTitle.setText(datas.getTitle());
        }
        circleDetailHolder.mTvIconLove.setTextColor(datas.getIs_like().equals("1")?mContext.getResources().getColor(R.color.otherFf7f6c):mContext.getResources().getColor(R.color.colorA1a1a1));
        circleDetailHolder.mTvLoveNumber.setTextColor(datas.getIs_like().equals("1")?mContext.getResources().getColor(R.color.otherFf7f6c):mContext.getResources().getColor(R.color.colorA1a1a1));
        circleDetailHolder.mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-M-dd HH:mm", datas.getTime()));
        circleDetailHolder.mTvUserNickName.setText(datas.getNick_name());
        circleDetailHolder.mTvContent.setText(datas.getContent());
        circleDetailHolder.mTvIconLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!datas.getIs_like().equals("1")) {
                    circleDetailHolder.mTvIconLove.setTextColor(mContext.getResources().getColor(R.color.otherFf7f6c));
                    //点赞
                    CircleDetailEvent circleDetailEvent = new CircleDetailEvent();
                    circleDetailEvent.setPosition(position);
                    Map<String, String> likeMap = MapUtils.Build().addKey(mContext).addFroumId(datas.getId()).addUserId().addRUserId(datas.getUser_id()).end();
                    XEventUtils.postUseCommonBackJson(IVariable.CIRCLE_LIKE_POST, likeMap, CircleDetailActivity.TYPE_LIKE_POST,circleDetailEvent);
                }else {
                    ToastUtils.showToast("你已经点过赞了");
                }

            }
        });

        String imageUrl = datas.getForum_img();
        if (!StringUtils.isEmpty(imageUrl)) {
            circleDetailHolder.mRvPhoto.setVisibility(View.VISIBLE);
            String[] split = imageUrl.split(",");
            List<String> list = Arrays.asList(split);
            if (circleDetailHolder.circleDetailPhotoAdapter==null){
                circleDetailHolder.circleDetailPhotoAdapter = new CircleDetailPhotoAdapter(list,mContext);
                circleDetailHolder.mRvPhoto.addItemDecoration(new CircleDetailDecoration(6));
                circleDetailHolder.mRvPhoto.setAdapter(circleDetailHolder.circleDetailPhotoAdapter);
                LinearLayoutManager linearLayoutManager=new GridLayoutManager(mContext,3);
                circleDetailHolder.mRvPhoto.setLayoutManager(linearLayoutManager);
            }else {
                circleDetailHolder.circleDetailPhotoAdapter.setList(list);
            }
        } else {
            circleDetailHolder.mRvPhoto.setVisibility(View.GONE);
        }

    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_circle, parent, false);
        return new CircleDetailHolder(inflate);
    }


    class CircleDetailHolder extends BaseRecycleViewHolder{
        @BindView(R.id.iv_user_icon) SimpleDraweeView mIvUserIcon;
        @BindView(R.id.tv_user_nick_name) TextView mTvUserNickName;
        @BindView(R.id.tv_time) TextView mTvTime;
        @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
        @BindView(R.id.tv_title) TextView mTvTitle;
        @BindView(R.id.tv_discuss_number) TextView mTvDiscussNumber;
        @BindView(R.id.tv_content) TextView mTvContent;
        @BindView(R.id.rv_photo) RecyclerView mRvPhoto;
        @BindView(R.id.tv_icon_love) FontsIconTextView mTvIconLove;
        CircleDetailPhotoAdapter circleDetailPhotoAdapter;
        public CircleDetailHolder(View itemView) {
            super(itemView);
        }
    }
}
