package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.DesRemarkActivity;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.json.JSONObject;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/1 0001.
 * 费用设置
 */
public class CostSettingActivity extends BaseToolBarActivity implements View.OnClickListener {
    @BindView(R.id.ll_with_me) LinearLayout mLlWithMe;//一起玩页面不显示
    @BindView(R.id.bt_next) Button mBtNext;
    @BindView(R.id.et_price) EditText mEtPrice;//价格

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_cost_setting;
    }

    @Override
    protected void initOptions() {
        ActivityUtils.getInstance().addActivity(this);
        initEvent();
    }

    @Override
    protected String initTitle() {
        return "费用设置";
    }

    protected void initEvent() {

        mBtNext.setOnClickListener(this);
      if (GlobalValue.mAppointType== IVariable.TYPE_TOGETHER){
          mLlWithMe.setVisibility(View.GONE);
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
        switch (v.getId()){
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
            JsonUtils.putString(IVariable.PRICE,String.valueOf(price),basecJsonObject);
            JsonUtils.putString(IVariable.TOTAL_PRICE,String.valueOf(price),basecJsonObject);
            startActivity(new Intent(this, DesRemarkActivity.class));
        } catch (Exception e) {
            ToastUtils.showToast("请设置相关费用！");
            e.printStackTrace();
        }
    }
}
