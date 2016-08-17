package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.CatOtherAlbum;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CatOtherAlbumHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class CatOtherAlbumAdapter extends TravelBaseAdapter<CatOtherAlbum> {
    public CatOtherAlbumAdapter(Context mContext, List<CatOtherAlbum> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, CatOtherAlbum item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new CatOtherAlbumHolder(mContext);
    }
}
