package com.yunspeak.travel.ui.find.find;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.aop.CheckNetwork;

import com.yunspeak.travel.ui.find.active.activedetail.ActivateDetailActivity;
import com.yunspeak.travel.ui.find.find.model.RecommendModel;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;


/**
 * Created by wangyang on 2016/10/17 0017.
 * 发现click事件管理
 */

public class FindClickListener{

    public static void onClick(View v, RecommendModel data) {
        Context mContext=v.getContext();
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