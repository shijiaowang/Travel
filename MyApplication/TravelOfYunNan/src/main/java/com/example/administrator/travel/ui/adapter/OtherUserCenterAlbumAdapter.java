package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.OtherCenterAlbumHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class OtherUserCenterAlbumAdapter extends TravelBaseAdapter {
    public OtherUserCenterAlbumAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new OtherCenterAlbumHolder(mContext);
    }
}
