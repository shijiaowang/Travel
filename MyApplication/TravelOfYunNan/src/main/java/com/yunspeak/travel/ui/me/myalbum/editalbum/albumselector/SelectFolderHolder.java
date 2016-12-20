package com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.ImageFolder;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;


import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/22.
 */
public class SelectFolderHolder extends BaseHolder<ImageFolder> {
    @BindView(R.id.iv_picture) SimpleDraweeView mIvPicture;
    @BindView(R.id.tv_count) TextView mTvCount;

    public SelectFolderHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(ImageFolder datas, Context mContext, int position) {
        String firstImagePath = datas.getFirstImagePath();
        FrescoUtils.displayNormal(mIvPicture,"file://"+firstImagePath);
        mTvCount.setText(datas.getName() + "(" + datas.getCount() + ")");
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_photo_select);
    }
}
