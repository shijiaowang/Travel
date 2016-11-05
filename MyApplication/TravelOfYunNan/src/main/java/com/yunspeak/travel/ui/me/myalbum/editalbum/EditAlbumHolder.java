package com.yunspeak.travel.ui.me.myalbum.editalbum;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/18 0018.
 */
public class EditAlbumHolder extends BaseRecycleViewHolder<EditAlbumBean.DataBean.BodyBean> {
    public static boolean canDelete=false;
    private final List<EditAlbumBean.DataBean.BodyBean> size;
    @BindView(R.id.iv_image) SimpleDraweeView mIvImage;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.tv_delete) TextView mTvDelete;

    public EditAlbumHolder(View itemView, List<EditAlbumBean.DataBean.BodyBean> size) {
        super(itemView);
        this.size = size;
    }


    @Override
    public void childBindView(int position, EditAlbumBean.DataBean.BodyBean datas, Context mContext) {
        FrescoUtils.displayNormal(mIvImage,datas.getPath());
        mTvNumber.setText((position+1)+"/"+size.size());
        mTvDelete.setVisibility(canDelete?View.VISIBLE:View.GONE);

    }
}
