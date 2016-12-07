package com.yunspeak.travel.ui.find;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.utils.FrescoUtils;

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

    public FindHotHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, FindBean.DataBean.RecommendBean data, Context mContext) {
        itemView.setOnClickListener(new MyOnClickListener(mContext,data));
        FrescoUtils.displayNormal(ivPhoto,data.getLogo_img(),640,360,R.drawable.normal_2_1);
        tvContent.setText(data.getTitle());
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
