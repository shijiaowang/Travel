package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.TitleManagementHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7 0007.
 * 称号管理
 */
public class TitleManagementAdapter extends TravelBaseAdapter {

    public TitleManagementAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {
        final TitleManagementHolder titleManagementHolder = (TitleManagementHolder) baseHolder;
        titleManagementHolder.mTvToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleManagementHolder.mLlToggle.getVisibility()==View.VISIBLE){
                    titleManagementHolder.mLlToggle.setVisibility(View.GONE);
                    titleManagementHolder.mTvToggle.setText("∨");
                }else {
                    titleManagementHolder.mLlToggle.setVisibility(View.VISIBLE);
                    titleManagementHolder.mTvToggle.setText("＞");
                }
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new TitleManagementHolder(mContext);
    }
}
