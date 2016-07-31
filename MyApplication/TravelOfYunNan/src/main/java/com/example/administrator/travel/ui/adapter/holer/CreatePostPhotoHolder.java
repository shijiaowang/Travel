package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;

/**
 * Created by android on 2016/7/31.
 */
public class CreatePostPhotoHolder extends BaseHolder {
    public CreatePostPhotoHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_create_post_photo_normal, null);
        return inflate;
    }
}
