package com.yunspeak.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Destination;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/30.
 */
public class DestinationHoler extends BaseHolder<Destination.DataBean.BodyBean> {
    @BindView(R.id.iv_spot) SimpleDraweeView mIvSpot;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.rb_star) RatingBar mRbStart;
    @BindView(R.id.tv_add)TextView mTvAdd;


    public DestinationHoler(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Destination.DataBean.BodyBean datas, Context mContext, int position) {
        FrescoUtils.displayNormal(mIvSpot,datas.getLogo_img());
        mTvName.setText(datas.getTitle());
        mTvAdd.setText(datas.getAddress());
        float star=5.0f;
        star = getStar(datas, star);
        mRbStart.setRating(star);
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
        return inflateView(R.layout.item_activity_destination);
    }
}
