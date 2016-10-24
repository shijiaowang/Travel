package com.yunspeak.travel.ui.appoint.selectdestination;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Destination;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 选择景点
 */
public class SelectDestinationHolder extends BaseHolder<Destination.DataBean.BodyBean> {
    @BindView(R.id.iv_spot) SimpleDraweeView mIvSpot;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.rb_star) RatingBar mRbStart;
    @BindView(R.id.tv_add) TextView mTvAdd;
    @BindView(R.id.tv_select) TextView mTvSelect;



    public SelectDestinationHolder(Context context) {
        super(context);
    }

    @Override
    public void initItemDatas(Destination.DataBean.BodyBean datas, Context mContext, int position) {
        FrescoUtils.displayNormal(mIvSpot,datas.getLogo_img());
        mTvName.setText(datas.getTitle());
        mTvAdd.setText(datas.getAddress());
        float star=5.0f;
        star = getStar(datas, star);
        mRbStart.setRating(star);
        mTvSelect.setTextColor(GlobalValue.clickPosition ==position?mContext.getResources().getColor(R.color.Ffbf75):mContext.getResources().getColor(R.color.colorb5b5b5));
       if (GlobalValue.mSelectSpot!=null && GlobalValue.mSelectSpot.contains(datas.getId())){
           getRootView().setAlpha(0.3f);
           mTvSelect.setVisibility(View.GONE);
       }else {
           getRootView().setAlpha(1.0f);
           mTvSelect.setVisibility(View.VISIBLE);
       }
    }
    private float getStar(Destination.DataBean.BodyBean datas, float star) {
        try {
            star = Float.parseFloat(datas.getStar());
        }catch (Exception e){
            e.printStackTrace();
        }
        return star;
    }
    @Override
    public View initRootView(Context mContext) {
        return  inflateView(R.layout.item_activity_select_destination);
    }
}
