package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class OtherCenterAlbumHolder extends BaseHolder {
    public OtherCenterAlbumHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_fragment_album_in_other);
        return view;
    }
}
