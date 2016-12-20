package com.yunspeak.travel.ui.find.active;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.bean.ActivityBean;
import com.yunspeak.travel.ui.find.active.activedetail.ActivateDetailActivity;
import com.yunspeak.travel.utils.FrescoUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/25 0025.
 */
public class ActiveHolder extends BaseRecycleViewHolder<ActivityBean> {
    @BindView(R.id.tv_air) TextView mTvAir;
    @BindView(R.id.tv_type) TextView mTvType;
    @BindView(R.id.tv_people) TextView mTvPeople;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.iv_bg)
    SimpleDraweeView mIvBg;

    public ActiveHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void childBindView(int position, final ActivityBean datas, final Context mContext) {
        mTvName.setText(datas.getTitle());
        mTvPeople.setText(datas.getNow_people()+"人参加");
        mTvType.setText(datas.getType().equals("2")?"线下活动":"线上活动");
        FrescoUtils.displayNormal(mIvBg,datas.getActivity_img());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivateDetailActivity.start(mContext,datas.getId());
            }
        });
    }
}
