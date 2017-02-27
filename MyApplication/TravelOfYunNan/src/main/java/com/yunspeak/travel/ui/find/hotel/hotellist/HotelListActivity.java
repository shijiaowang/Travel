package com.yunspeak.travel.ui.find.hotel.hotellist;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.find.hotel.hotelreservation.CalendarEvent;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.MapUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;


/**
 * Created by wangyang on 2017/2/27.
 * 酒店列表
 */

public class HotelListActivity extends BaseNetWorkActivity {

    @BindView(R.id.tv_hotel_list_in_out)
    TextView tvHotelListInOut;
    @BindView(R.id.tv_hotel_list_day)
    TextView tvHotelListDay;
    @BindView(R.id.tv_search)
    FontsIconTextView tvSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rl_destination)
    RelativeLayout rlDestination;
    @BindView(R.id.tv_hotel_list_select)
    TextView tvHotelListSelect;
    @BindView(R.id.tv_hotel_list_are)
    TextView tvHotelListAre;
    @BindView(R.id.tv_hotel_list_money)
    TextView tvHotelListMoney;
    private CalendarEvent calendarEvent;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM月dd日", Locale.CHINESE);

    @Override
    protected void initEvent() {
        calendarEvent = (CalendarEvent) getIntent().getSerializableExtra(IVariable.DATA);
        if (calendarEvent==null)finish();
        initDate();

    }

    private void initDate() {
        String start = simpleDateFormat.format(calendarEvent.getStart().getTime());
        String end = simpleDateFormat.format(calendarEvent.getEnd().getTime());
        start=getStringSubZero(start);
        end=getStringSubZero(end);
        String text="住 "+start+"\n离 "+end;
        tvHotelListInOut.setText(text);
        tvHotelListDay.setText(String.format("%d晚", CalendarUtils.getHowDay(calendarEvent.getStart().getTime().getTime() + "", calendarEvent.getEnd().getTime().getTime() + "") - 1));
    }

    /**
     * 去掉0
     * @param endText
     * @return
     */
    @NonNull
    private String getStringSubZero(String endText) {
        if (endText.startsWith("0")){
            endText=endText.substring(1,endText.length());
        }
        return endText;
    }
    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
    }

    @Override
    protected void onSuccess(HttpEvent httpEvent) {

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_hotel_list;
    }

    @Override
    protected String initTitle() {
        return "酒店列表";
    }


}
