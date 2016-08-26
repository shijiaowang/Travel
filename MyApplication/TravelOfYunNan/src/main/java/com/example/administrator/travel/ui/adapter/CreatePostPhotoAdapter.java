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
public class CreatePostPhotoAdapter extends TravelBaseAdapter<String> {
    private static  final  int TYPE_NORMAL =0;
    private static final  int TYPE_ADD =1;

    public CreatePostPhotoAdapter(Context mContext, List<String> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==mDatas.size()-1 && mDatas.get(position).equals("add"))
        {
            return TYPE_ADD;
        }
        return TYPE_NORMAL;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, String item, int position) {
    }

    @Override
    protected BaseHolder initHolder(int position) {
        if (getItemViewType(position)==TYPE_ADD){
            return new CreatePostPhotoLastHolder(mContext);
        }
        return new CreatePostPhotoHolder(mContext);
    }
}
