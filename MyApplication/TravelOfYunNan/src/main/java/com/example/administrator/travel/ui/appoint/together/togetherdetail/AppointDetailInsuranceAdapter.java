package com.example.administrator.travel.ui.appoint.together.togetherdetail;

import android.content.Context;

import com.example.administrator.travel.bean.PricebasecBean;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;


/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class AppointDetailInsuranceAdapter extends TravelBaseAdapter<PricebasecBean> {
    public AppointDetailInsuranceAdapter(Context mContext, List<PricebasecBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, PricebasecBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointDetailInsuranceHolder(mContext);
    }
}
