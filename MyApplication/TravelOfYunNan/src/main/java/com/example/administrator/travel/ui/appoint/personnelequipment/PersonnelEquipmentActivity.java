package com.example.administrator.travel.ui.appoint.personnelequipment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.choicesequipment.ChoicePropSelectBean;
import com.example.administrator.travel.ui.appoint.choicesequipment.ChoicePropsActivity;
import com.example.administrator.travel.ui.appoint.costsetting.CostSettingActivity;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.appoint.popwindow.AppointSpinnerPop;
import com.example.administrator.travel.ui.appoint.popwindow.SpinnerBean;
import com.example.administrator.travel.ui.appoint.togetherdetail.ProviderAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.ActivityUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    @ViewInject(R.id.et_least)
    private EditText mEtLeast;
    @ViewInject(R.id.et_most)
    private EditText mEtMost;
    @ViewInject(R.id.rl_auth_select)
    private RelativeLayout mRlAuthSelect;//认证赛选
    @ViewInject(R.id.rl_sex_select)
    private RelativeLayout mRlSexSelect;//性别赛选
    @ViewInject(R.id.tv_sex)
    private TextView mTvSex;
    @ViewInject(R.id.tv_auth)
    private TextView mTvAuth;
    @ViewInject(R.id.s_toggle)
    private Switch mSToggle;
    @ViewInject(R.id.lv_equ)
    private ToShowAllListView mLvEqu;
    private List<SpinnerBean> sexs;
    private List<SpinnerBean> auths;
    private String sexType="3";
    private String authType="5";
    private String autoPass="2";//自动通过
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_personnel_equipment;
    }

    @Override
    protected void initEvent() {
        TextView mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
        mTvRightNext.setOnClickListener(this);
        mTvSelectEqu.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
        initSpinnerData();
        mRlAuthSelect.setOnClickListener(this);
        mRlSexSelect.setOnClickListener(this);
        mSToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              autoPass=isChecked?"1":"2";//自动通过验证
            }
        });
    }
    /**
     * 数据
     */
    private void initSpinnerData() {
        sexs = new ArrayList<>();
        sexs.add(new SpinnerBean("男", "1",SEX_TYPE));
        sexs.add(new SpinnerBean("女", "2",SEX_TYPE));
        sexs.add(new SpinnerBean("不限", "3",SEX_TYPE));
        auths = new ArrayList<>();
        auths.add(new SpinnerBean("电话", "1",AUTH_TYPE));
        auths.add(new SpinnerBean("身份证", "2",AUTH_TYPE));
        auths.add(new SpinnerBean("驾驶证", "3",AUTH_TYPE));
        auths.add(new SpinnerBean("行驶证", "4",AUTH_TYPE));
        auths.add(new SpinnerBean("不限", "5",AUTH_TYPE));
    }
    @Override
    protected void onLoad(int typeRefresh) {
        Map<String, String> remarkMap = MapUtils.Build().addKey(this).end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_PROP_REMARK, remarkMap, 0, new PersonnelEquipmentEvent());
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
                Intent intent = new Intent(this, ChoicePropsActivity.class);
                startActivityForResult(intent,REQ_CODE);
                break;
            case R.id.tv_right_icon:
            case R.id.bt_next:
                try {
                    saveData();

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast("请完善人员信息");
                }

                break;
            case R.id.rl_auth_select:
                AppointSpinnerPop.showSpinnerPop(this,mRlAuthSelect,auths);
                break;
            case R.id.rl_sex_select:
                AppointSpinnerPop.showSpinnerPop(this,mRlSexSelect,sexs);
                break;
        }
    }

    /**
     * 保存数据
     */
    private void saveData() throws Exception {
        String least = getString(mEtLeast);
        String most = getString(mEtMost);
        if (Integer.parseInt(least)>Integer.parseInt(most)){
            ToastUtils.showToast("最多人数不能少于最少人数！");
            return;
        }
        if (Integer.parseInt(least)<=0 || Integer.parseInt(most)<=0){
            ToastUtils.showToast("人数不能少于0！");
            return;
        }
        JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
        JsonUtils.putString(IVariable.MIN_PEOPLE,least,basecJsonObject);
        JsonUtils.putString(IVariable.MAX_PEOPLE,most,basecJsonObject);
        JsonUtils.putString(IVariable.SEX_CONDITION,sexType,basecJsonObject);
        JsonUtils.putString(IVariable.BIND_CONDITION,authType,basecJsonObject);
        JsonUtils.putString(IVariable.AGREE,autoPass,basecJsonObject);
        startActivity(new Intent(this, CostSettingActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE && resultCode==RESULT_CODE){
            JSONArray propJsonArray = JsonUtils.getPropJsonArray();
            try {
                if ( propJsonArray.length()>0){//不为空就说明有数据,用户确定了
                      showSelect();
                }else {
                    GlobalValue.mPropSelects=null;//清理之前选中的
                    mLvEqu.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
    private void showSelect() {
        if (GlobalValue.mPropSelects==null)return;
        mLvEqu.setVisibility(View.VISIBLE);
        Iterator<String> iterator = GlobalValue.mPropSelects.keySet().iterator();
        List<ChoicePropSelectBean> choicePropSelectBeans = new ArrayList<>();
        while (iterator.hasNext()) {
            ChoicePropSelectBean choicePropSelectBean = GlobalValue.mPropSelects.get(iterator.next());
            choicePropSelectBeans.add(choicePropSelectBean);
        }
        ProviderAdapter popEquAdapter = new ProviderAdapter(this,choicePropSelectBeans);
        mLvEqu.setAdapter(popEquAdapter);
    }
    @Subscribe
    public void onEvent(SpinnerBean spinnerBean){
        if (spinnerBean.getSpinnerId()==AUTH_TYPE){
            authType=spinnerBean.getId();
            mTvAuth.setText(spinnerBean.getType());
        }else if (spinnerBean.getSpinnerId()==SEX_TYPE){
            sexType=spinnerBean.getId();
            mTvSex.setText(spinnerBean.getType());
        }
    }
}
