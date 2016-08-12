package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CatOtherAlbum;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class CatOtherAlbumHolder extends BaseHolder<CatOtherAlbum> {
    public CatOtherAlbumHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CatOtherAlbum datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_cat_other_album);
        return inflate;
    }
}
