package com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.pictureselector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.x;

import java.io.File;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/23.
 * 使用xutils
 */
public class PictureSelectorHolder extends BaseHolder<String> {
    @BindView(R.id.iv_picture) SimpleDraweeView mIvPicture;
    @BindView(R.id.tv_select) FontsIconTextView mTvSelect;
    @BindView(R.id.iv_picture_1)  View mImage;


    private String dir;

    public PictureSelectorHolder(Context context, String dir) {
        super(context);
        this.dir = dir;
    }

    @Override
    protected void initItemDatas(String datas, Context mContext, int position) {
        String url="file://"+dir + File.separator + datas;
        FrescoUtils.displayNormal(mIvPicture,url,200,200);
        if (GlobalValue.mSelectImages!=null && GlobalValue.mSelectImages.contains(url)){
            mImage.setBackgroundColor(Color.parseColor("#77000000"));
            mTvSelect.setTextColor(mContext.getResources().getColor(R.color.otherTitleBg));
        }else {
            mImage.setBackgroundColor(Color.TRANSPARENT);
            mTvSelect.setTextColor(mContext.getResources().getColor(R.color.colorFAFAFA));
        }
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_picture_selector);
    }
}
