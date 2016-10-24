package com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.pictureselector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/8/23.
 */
public class PictureSelectorAdapter extends TravelBaseAdapter<String> {
    private String dir;


    public PictureSelectorAdapter(Context mContext, List<String> mDatas, String dir) {
        super(mContext, mDatas);
        this.dir = dir;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final String item, int position) {
        if (baseHolder instanceof PictureSelectorHolder){
            final PictureSelectorHolder pictureSelectorHolder = (PictureSelectorHolder) baseHolder;
            pictureSelectorHolder.mIvPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (GlobalValue.mSelectImages==null){
                        GlobalValue.mSelectImages=new ArrayList<String>();
                    }
                    String url=dir+ File.separator+item;
                    if (GlobalValue.mSelectImages.contains(url)){
                        GlobalValue.mSelectImages.remove(url);
                        pictureSelectorHolder.mTvSelect.setTextColor(mContext.getResources().getColor(R.color.colorb5b5b5));
                        pictureSelectorHolder.mImage.setBackgroundColor(Color.TRANSPARENT);
                    }else {

                        if (GlobalValue.mSelectImages.size()>=GlobalValue.size){
                            ToastUtils.showToast("对不起，最多添加12张");
                            return;
                        }
                        GlobalValue.mSelectImages.add(url);
                        pictureSelectorHolder.mImage.setBackgroundColor(Color.parseColor("#77000000"));
                        pictureSelectorHolder.mTvSelect.setTextColor(mContext.getResources().getColor(R.color.otherTitleBg));
                    }
                   if (onSelectChangeListener!=null){
                       onSelectChangeListener.change(GlobalValue.mSelectImages.size());
                   }
                }
            });
        }

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new PictureSelectorHolder(mContext,dir);
    }
    private  OnSelectChangeListener onSelectChangeListener;

    public void setOnSelectChangeListener(OnSelectChangeListener onSelectChangeListener) {
        this.onSelectChangeListener = onSelectChangeListener;
    }

   public interface OnSelectChangeListener{
        void change(int size);
    }



}
