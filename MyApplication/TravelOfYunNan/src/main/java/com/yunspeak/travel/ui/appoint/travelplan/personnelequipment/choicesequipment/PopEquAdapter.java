package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment;

import android.content.Context;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class PopEquAdapter extends TravelBaseAdapter<ChoicePropSelectBean> {


    public PopEquAdapter(Context mContext, List<ChoicePropSelectBean> mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, ChoicePropSelectBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new PopEquHolder(mContext);
    }
}
