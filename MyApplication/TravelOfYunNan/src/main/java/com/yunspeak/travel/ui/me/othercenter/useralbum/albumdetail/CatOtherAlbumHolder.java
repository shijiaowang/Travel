package com.yunspeak.travel.ui.me.othercenter.useralbum.albumdetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CatOtherUserBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
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
        FrescoUtils.displayNormal(ivBg,bodyBean.getPath(),600,300);
        tvTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_D,bodyBean.getAdd_time()));
    }
}