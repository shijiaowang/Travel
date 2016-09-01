package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.travel.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/1 0001.
 * 费用设置
 */
public class CostSettingActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_cost_setting;
    }

    @Override
    protected void initEvent() {
        TextView mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
        mBtNext.setOnClickListener(this);
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
            case R.id.bt_next:
                startActivity(new Intent(this,CreateAppointSuccessActivity.class));
                break;
        }
    }
}
