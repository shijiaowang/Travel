package com.yunspeak.travel.ui.find.hotel.hotellist.searchhotel;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseEventBusActivity;

/**
 * Created by wangyang on 2017/2/28.
 * 酒店搜索
 */

public class SearchHotelActivity  extends BaseEventBusActivity<SearchHotelEvent>{
    @Override
    protected void onFail(SearchHotelEvent searchHotelEvent) {

    }

    @Override
    protected void onSuccess(SearchHotelEvent searchHotelEvent) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_search_hotel;
    }

    @Override
    protected String initTitle() {
        return getString(R.string.hotel_search);
    }
}
