package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.ui.view.FontsIconTextView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * Created by android on 2016/8/23.
 */
public class PictureSelectorHolder extends BaseHolder<String> {
    @ViewInject(R.id.iv_picture)
    public ImageView mIvPicture;
    @ViewInject(R.id.tv_select)
    public FontsIconTextView mTvSelect;
    @ViewInject(R.id.iv_picture_1)
     public View mImage;

    ImageOptions imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(100), DensityUtil.dip2px(100)).setCrop(true).build();
    private String dir;

    public PictureSelectorHolder(Context context, String dir) {
        super(context);
        this.dir = dir;
    }

    @Override
    protected void initItemDatas(String datas, Context mContext) {
        mImage.setBackgroundColor(Color.TRANSPARENT);
        mTvSelect.setTextColor(mContext.getResources().getColor(R.color.colorFAFAFA));
        x.image().bind(mIvPicture, dir + File.separator + datas, imageOptions);
        if (GlobalValue.mSelectImages!=null && GlobalValue.mSelectImages.contains(dir+File.separator+datas)){
            mImage.setBackgroundColor(Color.parseColor("#77000000"));
            mTvSelect.setTextColor(mContext.getResources().getColor(R.color.otherTitleBg));
        }
    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_picture_selector);
        return view;
    }
}
