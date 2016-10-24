package com.yunspeak.travel.ui.baseui;

import com.yunspeak.travel.R;

/**
 * Created by Administrator on 2016/9/7 0007.
 * 酒店页面-建设中
 */
public class HotelActivity extends BarBaseActivity{
    @Override
    protected int setContentLayout() {
        return R.layout.activity_hotel;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "酒店";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
