package com.yunspeak.travel.ui.find.active;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.active.ActiveBean;
import com.yunspeak.travel.utils.FrescoUtils;

import org.xutils.x;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/25 0025.
 */
public class ActiveHolder extends BaseRecycleViewHolder<ActiveBean.DataBean> {
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
    public void childBindView(int position, ActiveBean.DataBean datas, Context mContext) {
        mTvName.setText(datas.getTitle());
        mTvPeople.setText(datas.getNow_people()+"人参赛");
        mTvType.setText(datas.getType().equals("1")?"线下活动":"线上活动");
        FrescoUtils.displayNormal(mIvBg,datas.getActivity_img());
    }
}
