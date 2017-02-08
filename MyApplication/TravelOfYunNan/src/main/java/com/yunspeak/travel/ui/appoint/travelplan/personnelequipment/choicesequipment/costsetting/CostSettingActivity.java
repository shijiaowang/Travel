package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CostSettingBean;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.DesRemarkActivity;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.bean.BasecPriceBean;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.MoneyUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/1 0001.
 * 费用设置
 */
public class CostSettingActivity extends BaseNetWorkActivity<CostSettingEvent> implements View.OnClickListener {
    @BindView(R.id.bt_next)
    Button mBtNext;
    @BindView(R.id.et_price)
    EditText mEtPrice;//价格
    @BindView(R.id.rv_price)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.tv_money_des)
    TextView mTvMoneyDes;
    @BindView(R.id.tv_cost) TextView mTvCost;
    @BindView(R.id.tv_des) TextView mTvdes;
    private int size;
    private double totalMoney;
    private int countPeople;
    private double payMoney;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_cost_setting;
    }

    @Override
    protected void initEvent() {

        ActivityUtils.getInstance().addActivity(this);
       if (GlobalValue.mAppointType==IVariable.TYPE_WITH_ME){
           mTvMoneyDes.setText("(请输入您的理想费用，具体费用请咨询邀请您的队长！)");
           mTvCost.setText("理想费用");
       }
        mBtNext.setOnClickListener(this);
        mEtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = mEtPrice.getText().toString();
                if (StringUtils.isEmpty(string)){
                    string="0";
                }
                double enterMoney = Double.parseDouble(string);
                if (GlobalValue.mAppointType==IVariable.TYPE_WITH_ME){
                    payMoney = enterMoney+totalMoney;
                }else {
                    if (countPeople<=0){
                        ToastUtils.showToast("人数错误！");
                        setErrorPage(true);
                        return;
                    }
                     payMoney=(enterMoney*countPeople)/5d+totalMoney;
                }
                mTvTotalPrice.setText("合计:\u3000"+payMoney+"元");


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        size = getIntent().getIntExtra(IVariable.PAGE_SIZE, -1);
        countPeople = getIntent().getIntExtra(IVariable.POSITION, -1);
        if (size == -1) {
            ToastUtils.showToast("出行总天数出现错误！");
        }
    }

    @Override
    protected void onLoad(int type) {
        totalMoney=0;
        payMoney=0;
        super.onLoad(type);
    }

    @Override
    protected String initTitle() {
        return "费用设置";
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return IVariable.GET_PRICE;
    }

    @Override
    protected void onSuccess(CostSettingEvent costSettingEvent) {
        if (size == -1) {
            setErrorPage(true);
        } else {
            CostSettingBean costSettingBean = GsonUtils.getObject(costSettingEvent.getResult(), CostSettingBean.class);
            List<BasecPriceBean> data = costSettingBean.getData();
            mTvdes.setText(costSettingBean.getBasectext());
            CostSettingAdapter costSettingAdapter = new CostSettingAdapter(data, this,size);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setAdapter(costSettingAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            totalMoney= MoneyUtils.getMoney(data,size);
            mTvTotalPrice.setText("合计:\u3000"+totalMoney+"元");
        }

    }


    @Override
    protected String initRightText() {
        return "下一步";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        saveData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                saveData();
                break;
        }
    }


    private void saveData() {
        String string = getString(mEtPrice);
        try {
            float price = Float.parseFloat(string);
            JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
            JsonUtils.putString(IVariable.PRICE, String.valueOf(price), basecJsonObject);
            JsonUtils.putString(IVariable.TOTAL_PRICE, String.valueOf(price), basecJsonObject);
            startActivity(new Intent(this, DesRemarkActivity.class));
        } catch (Exception e) {
            ToastUtils.showToast("请设置相关费用！");
            e.printStackTrace();
        }
    }
}
