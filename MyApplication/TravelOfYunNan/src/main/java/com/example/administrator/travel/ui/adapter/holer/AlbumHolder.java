package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Album;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class AlbumHolder extends BaseHolder<Album>{
    public AlbumHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Album datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_fragment_album_in_other);
        return inflate;
    }
}
