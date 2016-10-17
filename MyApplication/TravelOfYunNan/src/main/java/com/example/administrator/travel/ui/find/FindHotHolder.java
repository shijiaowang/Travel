package com.example.administrator.travel.ui.find;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.baseui.ActivateDetailActivity;
import com.example.administrator.travel.ui.baseui.DeliciousDetailActivity;
import com.example.administrator.travel.ui.baseui.DestinationDetailActivity;
import com.example.administrator.travel.ui.baseui.TravelsDetailActivity;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/15 0015.
 */

public class FindHotHolder extends BaseRecycleViewHolder<FindBean.DataBean.RecommendBean> {
    @BindView(R.id.iv_photo)
    SimpleDraweeView ivPhoto;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    public FindHotHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, FindBean.DataBean.RecommendBean data, Context mContext) {
        itemView.setOnClickListener(new MyOnClickListener(mContext,data));
        FrescoUtils.displayNormal(ivPhoto,data.getLogo_img());
        tvContent.setText(data.getTitle());
        tvAddress.setText(data.getProvince()+data.getCity()+data.getAddress());
        int type = data.getType();
        if (type==1){
            tvType.setText("热门景点");
        }else if (type==2){
           tvType.setText("美食名店");
        }else if (type==3){
          tvType.setText("旅行游记");
        }else {
          tvType.setText("线上活动");
        }
    }

}
