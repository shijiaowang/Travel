package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.EditAlbum;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class EditAlbumNormalHolder extends BaseHolder<EditAlbum>{
    public EditAlbumNormalHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(EditAlbum datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_edit_album, null);
        return inflate;
    }
}
