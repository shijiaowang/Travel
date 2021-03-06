package com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.FrescoUtils;
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
        mIvPicture.setBackgroundColor(Color.TRANSPARENT);
        FrescoUtils.displayNormal(mIvPicture,"file://"+datas);

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_create_post_photo_normal);
    }
}
