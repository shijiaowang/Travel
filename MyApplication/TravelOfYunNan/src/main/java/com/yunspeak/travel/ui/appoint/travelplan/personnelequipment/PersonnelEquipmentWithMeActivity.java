package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite.AiteActivity;
import com.yunspeak.travel.ui.baseui.BarBaseActivity;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite.AiteFollow;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.CostSettingActivity;
import com.yunspeak.travel.ui.appoint.popwindow.AppointSpinnerPop;
import com.yunspeak.travel.ui.appoint.popwindow.SpinnerBean;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/30 0030.
 * 人员装备-找人带
 */
public class PersonnelEquipmentWithMeActivity extends BaseToolBarActivity implements View.OnClickListener {
    @BindView(R.id.bt_select_equ) Button mTvSelectEqu;
    @BindView(R.id.bt_next) Button mBtNext;
    @BindView(R.id.fl_people) FlowLayout mFlPeople;
    @BindView(R.id.tv_user_name) TextView mTvUserName;
    @BindView(R.id.rl_auth_select) RelativeLayout mRlAuthSelect;//认证赛选
    @BindView(R.id.rl_sex_select) RelativeLayout mRlSexSelect;//性别赛选
    @BindView(R.id.tv_sex) TextView mTvSex;
    @BindView(R.id.tv_auth) TextView mTvAuth;

    private List<AiteFollow> aiteFollows;
    private List<SpinnerBean> sexs;
    private List<SpinnerBean> auths;
    private String sexType="3";
    private String authType="5";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
        registerEventBus(this);

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_personnel_equipment_with_me;
    }

    @Override
    protected void initOptions() {
     initEvent();
    }

    @Override
    protected String initTitle() {
        return "人员装备";
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
    protected String initRightText() {
        return "下一步";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        onClick(mBtNext);
    }

    protected void initEvent() {
        try {
            String username = GlobalUtils.getUserInfo().getNick_name();
            mTvUserName.setText(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTvSelectEqu.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
        initSpinnerData();
        mRlAuthSelect.setOnClickListener(this);
        mRlSexSelect.setOnClickListener(this);
    }



    private void inflatePeople(final List<AiteFollow> aiteFollows) {
        this.aiteFollows = aiteFollows;
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        if (mFlPeople.getChildCount() > 0) mFlPeople.removeAllViews();
        for (int i = 0; i < aiteFollows.size(); i++) {
            View inflate = layoutInflater.inflate(R.layout.item_appointt_with_me_people, mFlPeople, false);
            ((TextView) inflate.findViewById(R.id.tv_name)).setText(aiteFollows.get(i).getNikeName());
            final View mView = inflate.findViewById(R.id.tv_delete);
            mView.setTag(aiteFollows.get(i));
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AiteFollow tag = (AiteFollow) mView.getTag();
                    int index = aiteFollows.indexOf(tag);
                    mFlPeople.removeViewAt(index);
                    aiteFollows.remove(index);
                }
            });
            mFlPeople.addView(inflate);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_select_equ:
                Intent intent = new Intent(this, AiteActivity.class);
                if (aiteFollows != null && aiteFollows.size() != 0) {
                    intent.putExtra(IVariable.DATA, ((Serializable) aiteFollows));
                }

                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.rl_auth_select:
                AppointSpinnerPop.showSpinnerPop(mRlAuthSelect,auths);
                break;
            case R.id.rl_sex_select:
                AppointSpinnerPop.showSpinnerPop(mRlSexSelect,sexs);
                break;
            case R.id.bt_next:
                try {
                    checkDataAndAddJson();
                    startActivity(new Intent(this, CostSettingActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast("请完善约伴信息");
                }
                break;
        }
    }

    /**
     * 制作json信息
     *
     * @throws Exception
     */
    private void checkDataAndAddJson() throws Exception {
        JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
        JsonUtils.putString(IVariable.SEX_CONDITION, sexType, basecJsonObject);
        JsonUtils.putString(IVariable.BIND_CONDITION, authType, basecJsonObject);
        JSONArray propJsonArray = JsonUtils.getPropJsonArray();//人员信息
        JSONObject jsonObject = new JSONObject();
        JsonUtils.putString(IVariable.USER_ID, GlobalUtils.getUserInfo().getId(), jsonObject);
        propJsonArray.put(jsonObject);
        if (ListIsEmpty(aiteFollows)) return;
        for (int i = 0; i < aiteFollows.size(); i++) {//要加上自己
            jsonObject = new JSONObject();
            JsonUtils.putString(IVariable.USER_ID, aiteFollows.get(i).getFollow().getId(), jsonObject);
            propJsonArray.put(jsonObject);
        }

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
    /**
     * 读取邀请邀请人信息
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE && resultCode == RESULT_CODE) {
            try {
                List<AiteFollow> aiteFollows = (List<AiteFollow>) data.getSerializableExtra(IVariable.DATA);
                if (aiteFollows != null) {
                    try {
                        inflatePeople(aiteFollows);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);
    }
}
