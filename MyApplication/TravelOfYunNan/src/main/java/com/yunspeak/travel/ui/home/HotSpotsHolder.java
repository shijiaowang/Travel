package com.yunspeak.travel.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.HomeBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.findcommon.BaseFindDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.utils.CityUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/17 0017.
 */

public class HotSpotsHolder extends BaseRecycleViewHolder<HomeBean.DataBean.DestinationBean> {
    @BindView(R.id.iv_picture)
    SimpleDraweeView ivPicture;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_name)
    TextView tvName;
    public HotSpotsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, final HomeBean.DataBean.DestinationBean data, final Context mContext) {
        FrescoUtils.displayNormal(ivPicture,data.getLogo_img());
        String provice = CityUtils.getStringById(data.getProvince());
        String city = CityUtils.getStringById(data.getCity());
        tvCity.setText(provice+city);
        tvName.setText(data.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DestinationDetailActivity.startShareElement(mContext,data.getId(),data.getTitle(),ivPicture,data.getLogo_img(), BaseFindDetailActivity.TYPE_DESTINATION);
            }
        });
    }
}
