package com.example.administrator.travel.ui.circle.circlenav.circledetail;
import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.x;

/**
 * Created by wangyang on 2016/8/17 0017.
 * 圈子图片
 */
public class CircleDetailPhotoHolder extends BaseHolder<String> {

    private SimpleDraweeView mImage;

    public CircleDetailPhotoHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(final String datas, Context mContext, int position) {
        mImage.setTag(datas);
        x.task().run(new Runnable() {
            @Override
            public void run() {
                FrescoUtils.displayNormal(mImage, (String) mImage.getTag());
            }
        });
    }

    @Override
    public View initRootView(Context mContext) {
        mImage = (SimpleDraweeView) inflateView(R.layout.item_activity_circle_detail_photo);
        return mImage;
    }
}
