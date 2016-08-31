package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.travel.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/30 0030.
 * 人员装备
 */
public class PersonnelEquipmentActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.bt_select_equ)
    private Button mTvSelectEqu;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_personnel_equipment;
    }

    @Override
    protected void initEvent() {
        TextView mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
       mTvSelectEqu.setOnClickListener(this);
    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "人员装备";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_select_equ:
                startActivity(new Intent(this,ChoicePropsActivity.class));
                break;
        }
    }
}
