package com.yunspeak.travel.ui.find.hotel.hotellist.hoteldetail.hotelorder;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseEventBusActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.utils.PhoneUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyang on 2017/2/28.
 * 酒店订单提交
 */

public class SubmitHotelOrderActivity extends BaseEventBusActivity<SubmitHotelEvent> {
    @BindView(R.id.tv_order_hotel_name)
    TextView tvOrderHotelName;
    @BindView(R.id.tv_order_hotel_information)
    TextView tvOrderHotelInformation;
    @BindView(R.id.tv_order_room_number)
    TextView tvOrderRoomNumber;
    @BindView(R.id.rl_order_room_number)
    RelativeLayout rlOrderRoomNumber;
    @BindView(R.id.rv_order_room_number)
    RecyclerView rvOrderRoomNumber;
    @BindView(R.id.iv_hotel_order_contacts)
    ImageView ivHotelOrderContacts;
    @BindView(R.id.rv_hotel_contacts)
    RecyclerView rvHotelContacts;
    @BindView(R.id.ed_hotel_order_phone)
    EditText edHotelOrderPhone;
    @BindView(R.id.tv_hotel_order_keep_time)
    TextView tvHotelOrderKeepTime;
    @BindView(R.id.tv_hotel_order_money)
    TextView tvHotelOrderMoney;
    @BindView(R.id.bt_next)
    AvoidFastButton btNext;

    @Override
    protected void onFail(SubmitHotelEvent submitHotelEvent) {

    }

    @Override
    protected void onSuccess(SubmitHotelEvent submitHotelEvent) {

    }

    @Override
    protected void initEvent() {
        edHotelOrderPhone.setText(UserUtils.getUserInfo().getTel());

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_submit_hotel_order;
    }

    @Override
    protected String initTitle() {
        return getString(R.string.submit_order);
    }


    @OnClick({R.id.iv_hotel_order_contacts,R.id.bt_next})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_hotel_order_contacts:
                break;
            case R.id.bt_next:
                if (PhoneUtils.checkPhoneNumber(getString(edHotelOrderPhone))){
                    ToastUtils.showToast("请输入正确的手机号码！");
                    edHotelOrderPhone.requestFocus();
                    return;
                }
                break;
        }
    }
}
