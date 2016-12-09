package com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.findcommon.DestinationBean;
import com.yunspeak.travel.utils.FrescoUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/8 0008.
 * 选择景点
 */
public class SelectDestinationHolder extends BaseRecycleViewHolder<DestinationBean.DataBean.BodyBean> {
    @BindView(R.id.iv_spot) SimpleDraweeView mIvSpot;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.rb_star) RatingBar mRbStart;
    @BindView(R.id.tv_add) TextView mTvAdd;
    @BindView(R.id.tv_select) TextView mTvSelect;

    public SelectDestinationHolder(View itemView) {
        super(itemView);
    }

    private float getStar(DestinationBean.DataBean.BodyBean datas, float star) {
        try {
            star = Float.parseFloat(datas.getStar());
        }catch (Exception e){
            e.printStackTrace();
        }
        return star;
    }

    @Override
    public void childBindView(int position, DestinationBean.DataBean.BodyBean datas, Context mContext) {
        FrescoUtils.displayNormal(mIvSpot,datas.getLogo_img());
        mTvName.setText(datas.getTitle());
        mTvAdd.setText(datas.getAddress());
        float star=5.0f;
        star = getStar(datas, star);
        mRbStart.setRating(star);
        mTvSelect.setTextColor(GlobalValue.clickPosition ==position?mContext.getResources().getColor(R.color.Ffbf75):mContext.getResources().getColor(R.color.colorb5b5b5));
    }
}
