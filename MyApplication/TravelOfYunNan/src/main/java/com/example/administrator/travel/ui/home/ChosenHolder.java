package com.example.administrator.travel.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Chosen;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

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
        FrescoUtils.displayNormal(mIvChosenPicture,data.getCircle_img());
        mTvType.setText(data.getCname());
        mTvChosenText.setText(data.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostActivity.start(mContext, data.getId(),data.getCid());
            }
        });
    }
}
