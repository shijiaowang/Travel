package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/23 0023.
 */
public class PostImageHolder extends BaseHolder<String> {
    @BindView(R.id.iv_picture)
    SimpleDraweeView mIvPicture;

    public PostImageHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(String datas, Context mContext, int position) {
        FrescoUtils.displayNormal(mIvPicture,datas);
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_post_image);
    }
}
