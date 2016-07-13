package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.CircleNavLeft;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CircleNavLeftHolder;
import com.example.administrator.travel.ui.view.SingleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavLeftAdapter extends TravelBaseAdapter<CircleNavLeft> {
    private List<SingleView> singleViews=new ArrayList<>();
    public CircleNavLeftAdapter(Context mContext, List<CircleNavLeft> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 20;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, CircleNavLeft item) {

    }


    @Override
    protected BaseHolder initHolder(int position) {
        return new CircleNavLeftHolder(super.mContext);
    }
}
