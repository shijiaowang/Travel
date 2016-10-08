package com.example.administrator.travel.ui.me.myalbum.editalbum.albumselector.pictureselector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.view.FontsIconTextView;

import org.xutils.x;

import java.io.File;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/23.
 */
public class PictureSelectorHolder extends BaseHolder<String> {
    @BindView(R.id.iv_picture) public ImageView mIvPicture;
    @BindView(R.id.tv_select)public FontsIconTextView mTvSelect;
    @BindView(R.id.iv_picture_1) public View mImage;


    private String dir;

    public PictureSelectorHolder(Context context, String dir) {
        super(context);
        this.dir = dir;
    }

    @Override
    protected void initItemDatas(String datas, Context mContext, int position) {
        x.image().bind(mIvPicture, dir + File.separator + datas, getImageOptions(100,100));
        if (GlobalValue.mSelectImages!=null && GlobalValue.mSelectImages.contains(dir+File.separator+datas)){
            mImage.setBackgroundColor(Color.parseColor("#77000000"));
            mTvSelect.setTextColor(mContext.getResources().getColor(R.color.otherTitleBg));
        }else {
            mImage.setBackgroundColor(Color.TRANSPARENT);
            mTvSelect.setTextColor(mContext.getResources().getColor(R.color.colorFAFAFA));
        }
    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_picture_selector);
        return view;
    }
}
