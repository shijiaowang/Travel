package com.yunspeak.travel.ui.find.hotel.hotellist.hoteldetail;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.flexbox.FlexboxLayout;
import com.hyphenate.util.DensityUtil;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.find.active.activedetail.ChangeColorSpan;
import com.yunspeak.travel.ui.find.hotel.hotelreservation.CalendarEvent;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangyang on 2017/2/28.
 * 宾馆详情
 */

public class HotelDetailActivity extends BaseNetWorkActivity<HotelDetaiEvent> {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日", Locale.CHINESE);
    @BindView(R.id.sdv_bg)
    SimpleDraweeView sdvBg;
    @BindView(R.id.tv_hotel_detail_city)
    TextView tvHotelDetailCity;
    @BindView(R.id.tv_hotel_detail_name)
    TextView tvHotelDetailName;
    @BindView(R.id.tv_hotel_detail_number)
    TextView tvHotelDetailNumber;
    @BindView(R.id.tv_hotel_detail_address)
    TextView tvHotelDetailAddress;
    @BindView(R.id.tv_hotel_detail_in)
    TextView tvHotelDetailIn;
    @BindView(R.id.tv_hotel_detail_sum_day)
    TextView tvHotelDetailSumDay;
    @BindView(R.id.tv_hotel_detail_out)
    TextView tvHotelDetailOut;
    @BindView(R.id.rv_hotel_detail_room_list)
    RecyclerView rvHotelDetailRoomList;
    @BindView(R.id.fl_hotel_detail_support)
    FlexboxLayout flHotelDetailSupport;
    @BindView(R.id.tv_hotel_detail_des)
    TextView tvHotelDetailDes;

    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void initEvent() {
        CalendarEvent calendarEvent = (CalendarEvent) getIntent().getSerializableExtra(IVariable.DATA);
        if (calendarEvent == null || calendarEvent.isEmpty()) {
            ToastUtils.showToast("未获取到住店离店日期");
            finish();
            return;
        }
        Date start = calendarEvent.getStart().getTime();
        SpannableString spannableString=new SpannableString("入住\n"+simpleDateFormat.format(start));
        spannableString.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.color646464)),0,2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvHotelDetailIn.setText(spannableString);
        Date end = calendarEvent.getStart().getTime();
        SpannableString spannableString1=new SpannableString("离开\n"+simpleDateFormat.format(end));
        spannableString1.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.color646464)),0,2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvHotelDetailIn.setText(spannableString1);
        String text=String.format(getString(R.string.how_night),calendarEvent.getHowDay());
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder(text);
        ColorStateList colorStateList=ColorStateList.valueOf(UIUtils.getColor(R.color.otherTitleBg));
        spannableStringBuilder.setSpan(new TextAppearanceSpan(null, Typeface.BOLD, DensityUtil.sp2px(this,24),colorStateList,null)
                ,1,text.length()-1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        );
        tvHotelDetailNumber.setText(spannableStringBuilder);
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
    }

    @Override
    protected void onSuccess(HotelDetaiEvent hotelDetaiEvent) {

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_hotel_detail;
    }

    @Override
    protected String initTitle() {
        return "酒店详情";
    }



    @OnClick(R.id.sdv_bg)
    public void onClick() {
    }
}
