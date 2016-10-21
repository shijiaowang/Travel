package com.example.administrator.travel.ui.find;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.ui.baseui.ActivateDetailActivity;
import com.example.administrator.travel.ui.baseui.DeliciousDetailActivity;
import com.example.administrator.travel.ui.baseui.DestinationDetailActivity;
import com.example.administrator.travel.ui.baseui.TravelsDetailActivity;

/**
 * Created by wangyang on 2016/10/17 0017.
 */

class MyOnClickListener implements View.OnClickListener {
    private Context mContext;
    private FindBean.DataBean.RecommendBean data;

    public MyOnClickListener(Context mContext,FindBean.DataBean.RecommendBean data) {
        this.mContext = mContext;
        this.data = data;
    }


    @Override
    public void onClick(View v) {
        switch (data.getType()){
            case 1:
                DestinationDetailActivity.start(mContext,data.getId(),data.getTitle());
                break;
            case 2:
                DeliciousDetailActivity.start(mContext,data.getId(),data.getTitle());
                break;
            case 3:
                TravelsDetailActivity.start(mContext,data.getId());
                break;
            case 4:
                ActivateDetailActivity.start(mContext,data.getId());
                break;
        }
    }
}