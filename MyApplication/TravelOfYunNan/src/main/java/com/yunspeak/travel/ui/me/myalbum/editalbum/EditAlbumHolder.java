package com.yunspeak.travel.ui.me.myalbum.editalbum;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.utils.FrescoUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/18 0018.
 */
public class EditAlbumHolder extends BaseRecycleViewHolder<EditAlbumBean.DataBean.BodyBean> {
    public static boolean canDelete;
    private final List<EditAlbumBean.DataBean.BodyBean> size;
    @BindView(R.id.iv_image) SimpleDraweeView mIvImage;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.tv_delete) TextView mTvDelete;

    public EditAlbumHolder(View itemView, List<EditAlbumBean.DataBean.BodyBean> size) {
        super(itemView);
        this.size = size;
        canDelete=false;
    }


    @Override
    public void childBindView(final int position, EditAlbumBean.DataBean.BodyBean datas, Context mContext) {
        FrescoUtils.displayNormal(mIvImage,datas.getPath(),640,360,R.drawable.normal_2_1);
        mTvNumber.setText((position+1)+"/"+size.size());
        mTvDelete.setVisibility(canDelete?View.VISIBLE:View.GONE);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAlbumEvent event = new EditAlbumEvent();
                event.setIsSuccess(true);
                event.setType(IState.TYPE_OTHER);
                event.setPosition(position);
                EventBus.getDefault().post(event);
            }
        });
        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAlbumEvent event = new EditAlbumEvent();
                event.setIsSuccess(true);
                event.setType(IState.TYPE_DELETE);
                event.setPosition(position);
                EventBus.getDefault().post(event);
            }
        });

    }
}
