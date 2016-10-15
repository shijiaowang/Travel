package com.example.administrator.travel.ui.me.othercenter.useralbum.albumdetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/15 0015.
 */

class CatOtherAlbumHolder extends BaseRecycleViewHolder<CatOtherUserBean.DataBean.BodyBean> {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_bg)
    SimpleDraweeView ivBg;
    public CatOtherAlbumHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, CatOtherUserBean.DataBean.BodyBean bodyBean, Context mContext) {
        FrescoUtils.displayNormal(ivBg,bodyBean.getPath());
        tvTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_D,bodyBean.getAdd_time()));
    }
}