package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.administrator.travel.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/2 0002.
 * 说明备注
 */
public class DesRemarkActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.bt_select_equ)
    private Button mBtSelect;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_des_remark;
    }

    @Override
    protected void initEvent() {
        mBtNext.setOnClickListener(this);
        mBtSelect.setOnClickListener(this);
    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "说明备注";
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
            case R.id.bt_select_equ:
                startActivity(new Intent(this,SettingTitleActivity.class));
                break;
        }
    }
}
