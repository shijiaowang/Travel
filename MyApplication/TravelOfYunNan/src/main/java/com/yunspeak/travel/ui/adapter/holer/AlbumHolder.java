package com.yunspeak.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Album;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class AlbumHolder extends BaseHolder<Album>{
    public AlbumHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Album datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_fragment_album_in_other);
    }
}