package com.example.administrator.travel.ui.me.albumselector;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/8/22.
 */
public class SelectFolderAdapter extends TravelBaseAdapter<ImageFolder> {
    public SelectFolderAdapter(Context mContext, List<ImageFolder> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, ImageFolder item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new SelectFolderHolder(mContext);
    }
}
