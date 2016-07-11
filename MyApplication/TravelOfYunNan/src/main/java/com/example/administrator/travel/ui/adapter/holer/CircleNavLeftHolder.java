package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleNavLeft;
import com.example.administrator.travel.ui.view.SingleView;
import com.example.administrator.travel.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavLeftHolder extends BaseHolder<CircleNavLeft> {
    public SingleView mSvSingle;

    public CircleNavLeftHolder(Context context) {
        super(context);
    }


    @Override
    protected void initItemDatas(CircleNavLeft datas, Context mContext) {
    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_fragment_circle_nav_left_single, null);
        mSvSingle = (SingleView) inflate.findViewById(R.id.sv_single);
        return inflate;
    }
}
