package com.example.administrator.travel.ui.circle.circlenav.circledetail.createpost;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/31.
 */
public class CreatePostPhotoHolder extends BaseHolder<String> {
    @BindView(R.id.iv_picture) SimpleDraweeView mIvPicture;
    public CreatePostPhotoHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(String datas, Context mContext, int position) {
        FrescoUtils.displayNormal(mIvPicture,"file://"+datas);

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_create_post_photo_normal);
    }
}
