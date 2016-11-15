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
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.DesRemarkActivity;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.MapUtils;
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
    private int size;
    private double totalMoney;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_cost_setting;
    }



    @Override
    protected void initEvent() {
        ActivityUtils.getInstance().addActivity(this);
        mBtNext.setOnClickListener(this);
        mEtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = mEtPrice.getText().toString();
                double enterMoney = Double.parseDouble(string);
                double payMoney=enterMoney+totalMoney*size;
                mTvTotalPrice.setText("合计:\u3000"+payMoney+"元");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        size = getIntent().getIntExtra(IVariable.PAGE_SIZE, -1);
        if (size == -1) {
            ToastUtils.showToast("出行总天数出现错误！");
        }
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
            List<CostSettingBean.DataBean> data = costSettingBean.getData();
            CostSettingAdapter costSettingAdapter = new CostSettingAdapter(data, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setAdapter(costSettingAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            double money;
            for (CostSettingBean.DataBean dataBean:data){
                String value = dataBean.getValue();
                money=Double.parseDouble(value);
                totalMoney+=money;
            }
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
