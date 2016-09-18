package com.example.administrator.travel.ui.appoint.personnelequipment;

import android.app.Activity;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.ChoicePropsActivity;
import com.example.administrator.travel.ui.activity.CostSettingActivity;
import com.example.administrator.travel.ui.activity.LoadingBarBaseActivity;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/30 0030.
 * 人员装备
 */
public class PersonnelEquipmentActivity extends LoadingBarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.bt_select_equ)
    private Button mTvSelectEqu;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @ViewInject(R.id.tv_remark)
    private TextView mTvRemark;
    @ViewInject(R.id.tv_least)
    private TextView mTvLeast;
    @ViewInject(R.id.tv_most)
    private TextView mTvMost;
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
        mBtNext.setOnClickListener(this);
    }

    @Override
    protected void onLoad() {
        Map<String, String> remarkMap = MapUtils.Build().addKey(this).end();
        XEventUtils.postUseCommonBackJson(IVariable.GET_PROP_REMARK, remarkMap, 0, new PersonnelEquipmentEvent());
    }

    @Override
    protected Activity initViewData() {
        return this;
    }
    @Subscribe
    public void onEvent(PersonnelEquipmentEvent event) {
        setIsProgress(false);
        if (event.isSuccess()){
            PropRemarkBean propRemarkBean = GsonUtils.getObject(event.getResult(), PropRemarkBean.class);
            mTvRemark.setText(propRemarkBean.getData().getContent());
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
        switch (v.getId()) {
            case R.id.bt_select_equ:
                startActivity(new Intent(this, ChoicePropsActivity.class));
                break;
            case R.id.bt_next:
                saveData();
                startActivity(new Intent(this, CostSettingActivity.class));
                break;
        }
    }

    /**
     * 保存数据
     */
    private void saveData() {

    }
}
