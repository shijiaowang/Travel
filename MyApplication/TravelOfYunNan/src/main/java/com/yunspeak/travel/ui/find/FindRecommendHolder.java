package com.yunspeak.travel.ui.find;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.FindBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/15 0015.
 */
class FindRecommendHolder extends BaseRecycleViewHolder<FindBean.DataBean.RecommendBean> {
    @BindView(R.id.iv_photo)
    SimpleDraweeView ivPhoto;
    @BindView(R.id.tv_text)
    TextView tvText;
    public FindRecommendHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, FindBean.DataBean.RecommendBean recommendBean, Context mContext) {
        FrescoUtils.displayNormal(ivPhoto,recommendBean.getLogo_img(),640,360,R.drawable.normal_2_1);
        tvText.setText(recommendBean.getTitle());
        itemView.setOnClickListener(new MyOnClickListener(mContext,recommendBean));
    }
}