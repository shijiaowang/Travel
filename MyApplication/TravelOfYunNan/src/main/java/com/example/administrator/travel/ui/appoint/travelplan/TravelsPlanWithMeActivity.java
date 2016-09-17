package com.example.administrator.travel.ui.appoint.travelplan;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.appoint.personnelequipment.PersonnelEquipmentWithMeActivity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by android on 2016/9/17.
 * 找人带-旅行计划
 */
public class TravelsPlanWithMeActivity extends BarBaseActivity {
    @ViewInject(R.id.bt_next)
    private Button mBtNext;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_plan_with_me;
    }

    @Override
    protected void initEvent() {
        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TravelsPlanWithMeActivity.this, PersonnelEquipmentWithMeActivity.class));
            }
        });
    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "旅行计划";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
