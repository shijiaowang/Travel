package com.example.administrator.travel.ui.circle.circlenav.circledetail.createpost;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

/**
 * Created by wangyang on 2016/7/31.
 */
public class CreatePostPhotoLastHolder extends BaseHolder {
    public View mTvAdd;

    public CreatePostPhotoLastHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        mTvAdd = inflateView(R.layout.item_activity_create_post_photo_add);
        return mTvAdd;
    }
}
