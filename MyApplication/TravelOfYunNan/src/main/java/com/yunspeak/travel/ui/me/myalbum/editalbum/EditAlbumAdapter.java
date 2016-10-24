package com.yunspeak.travel.ui.me.myalbum.editalbum;



import android.content.Context;
import android.view.View;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/7/18 0018.
 */
public class EditAlbumAdapter extends TravelBaseAdapter<EditAlbumBean.DataBean.BodyBean> {
    private boolean canDelete=false;
    public EditAlbumAdapter(Context mContext, List<EditAlbumBean.DataBean.BodyBean> mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, EditAlbumBean.DataBean.BodyBean item, final int position) {
        final EditAlbumHolder editAlbumHolder = (EditAlbumHolder) baseHolder;
        editAlbumHolder.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(position);
                editAlbumHolder.setSize(mDatas.size());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new EditAlbumHolder(mContext,mDatas.size());
    }
}
