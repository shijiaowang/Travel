package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.travel.ui.activity.AlbumSelectorActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CreatePostPhotoHolder;
import com.example.administrator.travel.ui.adapter.holer.CreatePostPhotoLastHolder;

import java.util.List;

/**
 * Created by android on 2016/7/31.
 */
public class CreatePostPhotoAdapter extends TravelBaseAdapter {
    public CreatePostPhotoAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 12;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {
         if (baseHolder instanceof CreatePostPhotoLastHolder){
             CreatePostPhotoLastHolder createPostPhotoLastHolder = (CreatePostPhotoLastHolder) baseHolder;
              createPostPhotoLastHolder.mTvAdd.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      mContext.startActivity(new Intent(mContext, AlbumSelectorActivity.class));
                  }
              });
         }
    }

    @Override
    protected BaseHolder initHolder(int position) {
        if (position==11){
            return new CreatePostPhotoLastHolder(mContext);
        }
        return new CreatePostPhotoHolder(mContext);
    }
}
