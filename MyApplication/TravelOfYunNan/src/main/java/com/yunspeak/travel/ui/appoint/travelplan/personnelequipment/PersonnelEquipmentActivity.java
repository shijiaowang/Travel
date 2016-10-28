package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.ChoicePropSelectBean;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.ChoicePropsActivity;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.CostSettingActivity;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.appoint.popwindow.AppointSpinnerPop;
import com.yunspeak.travel.ui.appoint.popwindow.SpinnerBean;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.ProviderAdapter;
import com.yunspeak.travel.ui.view.ToShowAllListView;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/30 0030.
 * 人员装备
 */
public class PersonnelEquipmentActivity extends BaseNetWorkActivity<PersonnelEquipmentEvent> implements View.OnClickListener {
    @BindView(R.id.bt_select_equ) Button mTvSelectEqu;
    @BindView(R.id.bt_next) Button mBtNext;
    @BindView(R.id.tv_remark) TextView mTvRemark;
    @BindView(R.id.et_least) EditText mEtLeast;
    @BindView(R.id.et_most) EditText mEtMost;
    @BindView(R.id.rl_auth_select) RelativeLayout mRlAuthSelect;//认证赛选
    @BindView(R.id.rl_sex_select) RelativeLayout mRlSexSelect;//性别赛选
    @BindView(R.id.tv_sex) TextView mTvSex;
    @BindView(R.id.tv_auth) TextView mTvAuth;
    @BindView(R.id.s_toggle) Switch mSToggle;
    @BindView(R.id.lv_equ) ToShowAllListView mLvEqu;
    private List<SpinnerBean> sexs;
    private List<SpinnerBean> auths;
    private String sexType="3";
    private String authType="5";
    private String autoPass="2";//自动通过

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_personnel_equipment;
    }

    @Override
    protected String initTitle() {
        return "人员装备";
    }


    @Override
    protected void initEvent() {
        ActivityUtils.getInstance().addActivity(this);
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

    @Override
    protected String initRightText() {
        return "下一步";
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
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return IVariable.GET_PROP_REMARK;
    }




    @Override
    protected void onSuccess(PersonnelEquipmentEvent personnelEquipmentEvent) {
        PropRemarkBean propRemarkBean = GsonUtils.getObject(personnelEquipmentEvent.getResult(), PropRemarkBean.class);
        mTvRemark.setText(propRemarkBean.getData().getContent());
    }

    @Override
    protected void onFail(PersonnelEquipmentEvent event) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_select_equ:
                Intent intent = new Intent(this, ChoicePropsActivity.class);
                startActivityForResult(intent,REQ_CODE);
                break;
            case R.id.bt_next:
                next();

                break;
            case R.id.rl_auth_select:
                AppointSpinnerPop.showSpinnerPop(this,mRlAuthSelect,auths);
                break;
            case R.id.rl_sex_select:
                AppointSpinnerPop.showSpinnerPop(this,mRlSexSelect,sexs);
                break;
        }
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        next();
    }

    /**
     * 下一步
     */
    private void next() {
        try {
            saveData();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast("请完善人员信息");
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
