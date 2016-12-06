package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.payresult;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/12/6 0006.
 * 订单结果页面
 */

public class PayResultActivity extends BaseToolBarActivity {
    @BindView(R.id.tv_result) TextView tvResult;
    @BindView(R.id.tv_des) TextView tvDes;
    @BindView(R.id.tv_pay_icon) TextView tvPayIcon;
    @BindView(R.id.bt_next) AvoidFastButton btNext;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initOptions() {
        Intent intent = getIntent();
        String result =  intent.getStringExtra("result");
        String des =  intent.getStringExtra("des");
        boolean isWX =  intent.getBooleanExtra("isWX",false);
        tvResult.setText(result);
        tvDes.setText(des);
        if (!isWX){
            tvPayIcon.setText(getString(R.string.activity_confirm_orders_zfb));
            tvPayIcon.setTextColor(Color.parseColor("#11B1F0"));
        }
        btNext.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                 setResult(RESULT_CODE);
                 finish();
            }
        });
    }

    @Override
    protected String initTitle() {
        return "支付结果";
    }


}
