package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.backmoney;

import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.utils.MapUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/12/9 0009.
 */

public class BackMoneyActivity extends BaseNetWorkActivity<BackMoneyEvent> {
    @BindView(R.id.tv_back_money_pay_money)
    TextView tvBackMoneyPayMoney;
    @BindView(R.id.tv_back_money_back_money)
    TextView tvBackMoneyBackMoney;
    @BindView(R.id.tv_back_money_time)
    TextView tvBackMoneyTime;
    @BindView(R.id.bt_back_money_sure)
    AvoidFastButton btBackMoneySure;
    @BindView(R.id.bt_back_money_cancel)
    AvoidFastButton btBackMoneyCancel;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
    }

    @Override
    protected void onSuccess(BackMoneyEvent httpEvent) {

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_back_money;
    }

    @Override
    protected String initTitle() {
        return "退款说明";
    }

}
