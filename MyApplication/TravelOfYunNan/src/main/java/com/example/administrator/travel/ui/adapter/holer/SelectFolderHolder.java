package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.ImageFolder;


import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by android on 2016/8/22.
 */
public class SelectFolderHolder extends BaseHolder<ImageFolder> {
    @ViewInject(R.id.iv_picture)
    private ImageView mIvPicture;
    @ViewInject(R.id.tv_count)
    private TextView mTvCount;

    public SelectFolderHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(ImageFolder datas, Context mContext) {
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
