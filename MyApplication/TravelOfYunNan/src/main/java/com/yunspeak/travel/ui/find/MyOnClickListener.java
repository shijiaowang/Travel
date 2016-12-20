package com.yunspeak.travel.ui.find;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.bean.FindBean;
import com.yunspeak.travel.ui.find.active.activedetail.ActivateDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;

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
                TravelsDetailActivity.start(mContext,data.getId(),data.getTitle());
                break;
            case 4:
                ActivateDetailActivity.start(mContext,data.getId());
                break;
        }
    }
}