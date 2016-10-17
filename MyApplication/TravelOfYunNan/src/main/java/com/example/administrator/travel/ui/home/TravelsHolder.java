package com.example.administrator.travel.ui.home;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.baseui.TravelsDetailActivity;
import com.example.administrator.travel.utils.FrescoUtils;
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
        FrescoUtils.displayIcon(ivIcon,data.getLogo_img());
        FrescoUtils.displayNormal(ivTravels,data.getTitle_img());
        tvUser.setText(data.getAuthor());
        tvContent.setText(data.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TravelsDetailActivity.start(mContext,data.getId());
            }
        });

    }
}
