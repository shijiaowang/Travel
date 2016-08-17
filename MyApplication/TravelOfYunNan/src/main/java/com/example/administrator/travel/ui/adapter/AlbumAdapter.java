package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.Album;
import com.example.administrator.travel.ui.adapter.holer.AlbumHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class AlbumAdapter extends TravelBaseAdapter<Album> {

    public AlbumAdapter(Context mContext, List<Album> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 20;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Album item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AlbumHolder(super.mContext);
    }
}
