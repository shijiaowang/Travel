package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.travel.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class PostImageHolder extends BaseHolder<String> {
    @BindView(R.id.iv_picture) ImageView mIvPicture;
    ImageOptions imageOptions= new ImageOptions.Builder().setSize(DensityUtil.dip2px(326), DensityUtil.dip2px(165)).setCrop(true).build();
    public PostImageHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(String datas, Context mContext, int position) {
        x.image().bind(mIvPicture,datas,imageOptions);
    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_post_image);
        return view;
    }
}
