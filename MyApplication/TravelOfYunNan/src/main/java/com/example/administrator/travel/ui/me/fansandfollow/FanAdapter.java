package com.example.administrator.travel.ui.me.fansandfollow;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.appoint.aite.Follow;


import java.util.List;

/**
 * Created by wangyang on 2016/7/18 0018.
 */
public class FanAdapter extends TravelBaseAdapter<Follow> {
    public FanAdapter(Context mContext, List<Follow> mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, Follow item, int position) {
        FanHolder fanHolder = (FanHolder) baseHolder;
        if (position==mDatas.size()-1){
            fanHolder.mVLine.setVisibility(View.GONE);
        }else {
            fanHolder.mVLine.setVisibility(View.VISIBLE);
        }
    }





    @Override
    protected BaseHolder initHolder(int position) {
        return new FanHolder(super.mContext);
    }
}
