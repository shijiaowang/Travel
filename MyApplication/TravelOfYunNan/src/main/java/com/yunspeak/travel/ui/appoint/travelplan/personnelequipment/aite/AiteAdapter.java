package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite;

import android.content.Context;

import com.yunspeak.travel.bean.AiteFollow;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/8/1 0001.
 */
public class AiteAdapter extends TravelBaseAdapter<AiteFollow> {
    public AiteAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, AiteFollow item, int position) {

    }


    @Override
    protected BaseHolder initHolder(int position) {
        return new AiteHolder(mContext);
    }
}
