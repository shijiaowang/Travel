package com.yunspeak.travel.ui.find.find.model;

import android.content.Intent;
import android.view.View;

import com.yunspeak.travel.ui.find.active.ActiveActivity;
import com.yunspeak.travel.ui.find.findcommon.FindCommonActivity;
import com.yunspeak.travel.ui.find.hotel.hotelreservation.HotelReservationActivity;
import com.yunspeak.travel.ui.find.travels.TravelsActivity;

/**
 * Created by wangyang on 2017/3/14.
 * 其他点击事件控制器
 */

public class OtherControlModel {
    public void onActiveClick(View view){
        view.getContext().startActivity(new Intent(view.getContext(), ActiveActivity.class));
    }
    public void onFoodClick(View view){
        FindCommonActivity.start(view.getContext(),FindCommonActivity.DELICIOUS_NORMAL, 0);
    }
    public void onTravelsClick(View view){
        view.getContext().startActivity(new Intent(view.getContext(), TravelsActivity.class));
    }
    public void onDestinationClick(View view){
        FindCommonActivity.start(view.getContext(),FindCommonActivity.DESTINATION_NORMAL, 0);
    }
    public void  onHotelClick(View view){
        view.getContext().startActivity(new Intent(view.getContext(), HotelReservationActivity.class));
    }

}
