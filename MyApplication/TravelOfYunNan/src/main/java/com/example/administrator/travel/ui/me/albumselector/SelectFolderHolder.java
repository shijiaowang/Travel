package com.example.administrator.travel.ui.me.albumselector;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.me.albumselector.ImageFolder;


import org.xutils.x;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/22.
 */
public class SelectFolderHolder extends BaseHolder<ImageFolder> {
    @BindView(R.id.iv_picture) ImageView mIvPicture;
    @BindView(R.id.tv_count) TextView mTvCount;

    public SelectFolderHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(ImageFolder datas, Context mContext, int position) {
        String firstImagePath = datas.getFirstImagePath();
        x.image().bind(mIvPicture, firstImagePath, getImageOptions(76,78));
        mTvCount.setText(datas.getName() + "(" + datas.getCount() + ")");
    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_photo_select);
        return view;
    }
}
