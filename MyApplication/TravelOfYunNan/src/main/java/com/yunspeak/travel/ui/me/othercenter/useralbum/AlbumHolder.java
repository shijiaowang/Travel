package com.yunspeak.travel.ui.me.othercenter.useralbum;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.OtherAlbumBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/15 0015.
 */
class AlbumHolder extends BaseRecycleViewHolder<OtherAlbumBean.DataBean.MoreBean> {
    @BindView(R.id.iv_cover)
    SimpleDraweeView ivCover;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_watch_number)
    TextView tvWatchNumber;

    public AlbumHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, OtherAlbumBean.DataBean.MoreBean moreBean, Context mContext) {
        FrescoUtils.displayNormal(ivCover,moreBean.getCover_img());
       tvName.setText(moreBean.getTitle());
        tvWatchNumber.setText(moreBean.getBrowse());
    }
}