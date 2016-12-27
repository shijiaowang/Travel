package com.yunspeak.travel.ui.find.findcommon;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.FindDestinationBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FrescoUtils;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/30.
 */
public class DestinationHoler extends BaseRecycleViewHolder<FindDestinationBean.DataBean.BodyBean> {
    private final boolean isDestination;
    @BindView(R.id.iv_spot) SimpleDraweeView mIvSpot;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.rb_star) RatingBar mRbStart;
    @BindView(R.id.tv_add)TextView mTvAdd;
    @BindString(R.string.item_fragment_find_add) String addressIcon;


    public DestinationHoler(View itemView, boolean isDestination) {
        super(itemView);
        this.isDestination = isDestination;
    }



    private float getStar(FindDestinationBean.DataBean.BodyBean datas, float star) {
        try {
            star = Float.parseFloat(datas.getStar());
        }catch (Exception e){
            e.printStackTrace();
        }
        return star;
    }


    @Override
    public void childBindView(int position, final FindDestinationBean.DataBean.BodyBean datas, final Context mContext) {
        FrescoUtils.displayNormal(mIvSpot,datas.getLogo_img(),R.drawable.normal_2_1);
        mTvName.setText(datas.getTitle());
        AiteUtils.setIconText(addressIcon,datas.getAddress(),mContext,mTvAdd);
        float star=5.0f;
        star = getStar(datas, star);
        mRbStart.setRating(star);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDestination) {
                    DestinationDetailActivity.startShareElement(mContext,datas.getId(),datas.getTitle(),mIvSpot,datas.getLogo_img(),BaseFindDetailActivity.TYPE_DESTINATION);
                } else {
                    DeliciousDetailActivity.startShareElement(mContext,datas.getId(),datas.getTitle(),mIvSpot,datas.getLogo_img(),BaseFindDetailActivity.TYPE_DELICIOUS);
                }

            }
        });
    }


}
