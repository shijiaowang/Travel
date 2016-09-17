package com.example.administrator.travel.ui.appoint.personnelequipment;

import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.activity.ChoicePropsActivity;
import com.example.administrator.travel.ui.activity.CostSettingActivity;
import com.example.administrator.travel.ui.view.FlowLayout;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/30 0030.
 * 人员装备-找人带
 */
public class PersonnelEquipmentWithMeActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.bt_select_equ)
    private Button mTvSelectEqu;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @ViewInject(R.id.fl_people)
    private FlowLayout mFlPeople;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_personnel_equipment_with_me;
    }

    @Override
    protected void initEvent() {
        TextView mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
       mTvSelectEqu.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
    }

    @Override
    protected void initViewData() {
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        if (mFlPeople.getChildCount()>0)mFlPeople.removeAllViews();
        for (int i=0;i<7;i++){
            View inflate = layoutInflater.inflate(R.layout.item_appointt_with_me_people, mFlPeople, false);
            final int index = i;
            inflate.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlPeople.removeViewAt(index);
                }
            });
            mFlPeople.addView(inflate);
        }
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
            case R.id.bt_next:
                startActivity(new Intent(this,CostSettingActivity.class));
                break;
        }
    }
}
