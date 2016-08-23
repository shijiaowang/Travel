package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.PictureSelectorHolder;
import com.example.administrator.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/8/23.
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

                        if (GlobalValue.mSelectImages.size()>=12){
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
