package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.travel.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by android on 2016/7/31.
 */
public class CreatePostPhotoHolder extends BaseHolder<String> {
    @ViewInject(R.id.iv_picture)
    private ImageView mIvPicture;
    public CreatePostPhotoHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(String datas, Context mContext, int position) {
        x.image().bind(mIvPicture,datas,getImageOptions(DensityUtil.dip2px(80),DensityUtil.dip2px(80)));
    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_create_post_photo_normal);
        return inflate;
    }
}
