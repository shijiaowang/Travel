package com.example.administrator.travel.ui.appoint.costsetting;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.appoint.desremark.DesRemarkActivity;
import com.example.administrator.travel.utils.ActivityUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.ToastUtils;

import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/1 0001.
 * 费用设置
 */
public class CostSettingActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_with_me)
    private LinearLayout mLlWithMe;//一起玩页面不显示
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @ViewInject(R.id.et_price)
    private EditText mEtPrice;//价格
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_cost_setting;
    }

    @Override
    protected void initEvent() {
        TextView mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
        mTvRightNext.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
      if (GlobalValue.mAppointType== IVariable.TYPE_TOGETHER){
          mLlWithMe.setVisibility(View.GONE);
      }
    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "费用设置";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_right_icon:
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
