package com.yunspeak.travel.ui.home;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.HomeBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/17 0017.
 */

public class TravelsHolder extends BaseRecycleViewHolder<HomeBean.DataBean.FindTravelBean> {
    @BindView(R.id.iv_travels) SimpleDraweeView ivTravels;
    @BindView(R.id.tv_content) TextView tvContent;
    @BindView(R.id.iv_icon) SimpleDraweeView ivIcon;
    @BindView(R.id.tv_user) TextView tvUser;
    public TravelsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, final HomeBean.DataBean.FindTravelBean data, final Context mContext) {
        FrescoUtils.displayIcon(ivIcon,data.getTitle_img());
        FrescoUtils.displayNormal(ivTravels,data.getLogo_img(),640,360,R.drawable.normal_2_1);
        tvUser.setText(data.getAuthor());
        tvContent.setText(data.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TravelsDetailActivity.start(mContext,data.getId(),data.getTitle());
            }
        });

    }
}
