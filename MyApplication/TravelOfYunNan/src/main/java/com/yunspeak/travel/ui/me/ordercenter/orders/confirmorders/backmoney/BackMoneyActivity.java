package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.backmoney;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.BackMoneyBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.find.active.activedetail.ChangeColorSpan;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/12/9 0009.
 */

public class BackMoneyActivity extends BaseNetWorkActivity<BackMoneyEvent> implements AvoidFastButton.AvoidFastOnClickListener {
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
    private String tid;
    private String type;
    private boolean isBoos;

    @Override
    protected void initEvent() {
        tid = getIntent().getStringExtra(IVariable.TID);
        type = getIntent().getStringExtra(IVariable.TYPE);
        isBoos = getIntent().getBooleanExtra(IVariable.DATA, false);
        btBackMoneyCancel.setOnAvoidFastOnClickListener(this);
        btBackMoneySure.setOnAvoidFastOnClickListener(this);
    }

    public static void start(Context context, String tid, String type, boolean isBoos) {
        Intent intent = new Intent(context, BackMoneyActivity.class);
        intent.putExtra(IVariable.TID, tid);
        intent.putExtra(IVariable.TYPE, type);
        intent.putExtra(IVariable.DATA, isBoos);
        context.startActivity(intent);
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int t) {
        builder.addType(type).addtId(tid);
    }

    @Override
    protected String initUrl() {
        return IVariable.BACK_MONEY;
    }

    @Override
    protected void onSuccess(BackMoneyEvent httpEvent) {
        switch (httpEvent.getType()){
            case TYPE_DELETE:
                ToastUtils.showToast(httpEvent.getMessage());
                EventBus.getDefault().post(new BackMoneyNotifyEvent());//通知退款更新
                finish();
                break;
            default:
                refreshData(httpEvent);
                break;
        }

    }

    private void refreshData(BackMoneyEvent httpEvent) {
        BackMoneyBean backMoneyBean = GsonUtils.getObject(httpEvent.getResult(), BackMoneyBean.class);
        BackMoneyBean.DataBean data = backMoneyBean.getData();
        tvBackMoneyPayMoney.setText(data.getPay_money() + "\u3000元");
        String refundMoney = data.getRefund_money();
        int length = refundMoney.length();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(refundMoney + "\u3000元");
        spannableStringBuilder.setSpan(new ChangeColorSpan(getResources().getColor(R.color.otherFf7f6c)), 0, length, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvBackMoneyBackMoney.setText(spannableStringBuilder);
        String startTime = data.getStart_time();
        String day = data.getDay();
        String time = FormatDateUtils.FormatLongTime("yyyy.MM.dd", startTime);
        String timeText = "距离出发日期\u0020" + time + "\u0020" + "还有\u0020" + day + "\u0020天";
        SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder(timeText);
        spannableStringBuilder1.setSpan(new ChangeColorSpan(), 7, 7 + time.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringBuilder1.setSpan(new ChangeColorSpan(), timeText.length() - 2 - day.length(), timeText.length() - 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvBackMoneyTime.setText(spannableStringBuilder1);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_back_money;
    }

    @Override
    protected String initTitle() {
        return "退款说明";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back_money_sure:
                MapUtils.Builder builder = MapUtils.Build().addKey().addtId(tid).addUserId();
                Map<String, String> end;
                String url;
                if (isBoos) {
                    end = builder.end();
                    url=IVariable.DELETE_APPOINT;
                } else {
                    end = builder.addType(type).end();
                    url=IVariable.OUT_APPOINT;
                }
                XEventUtils.postUseCommonBackJson(url,end,TYPE_DELETE,new BackMoneyEvent());
                break;
            case R.id.bt_back_money_cancel:
                finish();
                break;
        }
    }
}
