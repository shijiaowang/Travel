package com.yunspeak.travel.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.HomeBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.utils.FrescoUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/8 0008.
 */
public class ChosenHolder extends BaseRecycleViewHolder<HomeBean.DataBean.ForumBean> {
    @BindView(R.id.tv_chosen_text) TextView mTvChosenText;
    @BindView(R.id.iv_chosen_picture) SimpleDraweeView mIvChosenPicture;
    @BindView(R.id.tv_type) TextView mTvType;

    public ChosenHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, final HomeBean.DataBean.ForumBean data, final Context mContext) {
        FrescoUtils.displayNormal(mIvChosenPicture,data.getCircle_img(),R.drawable.normal_2_1);
        mTvType.setText(data.getCname());
        mTvChosenText.setText(data.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostActivity.start(mContext, data.getId());
            }
        });
    }
}
