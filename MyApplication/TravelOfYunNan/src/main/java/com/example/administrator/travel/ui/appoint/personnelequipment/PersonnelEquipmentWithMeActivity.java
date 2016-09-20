package com.example.administrator.travel.ui.appoint.personnelequipment;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.aite.AiteActivity;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.appoint.aite.AiteFollow;
import com.example.administrator.travel.ui.appoint.costsetting.CostSettingActivity;
import com.example.administrator.travel.ui.appoint.popwindow.AppointSpinnerPop;
import com.example.administrator.travel.ui.appoint.popwindow.SpinnerBean;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @ViewInject(R.id.tv_user_name)
    private TextView mTvUserName;
    @ViewInject(R.id.rl_auth_select)
    private RelativeLayout mRlAuthSelect;//认证赛选
    @ViewInject(R.id.rl_sex_select)
    private RelativeLayout mRlSexSelect;//性别赛选
    @ViewInject(R.id.tv_sex)
    private TextView mTvSex;
    @ViewInject(R.id.tv_auth)
    private TextView mTvAuth;

    private List<AiteFollow> aiteFollows;
    private List<SpinnerBean> sexs;
    private List<SpinnerBean> auths;
    private String sexType="3";
    private String authType="5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalValue.mActivity.add(this);
        registerEventBus(this);

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
    protected int setContentLayout() {
        return R.layout.activity_personnel_equipment_with_me;
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
    }

    @Override
    protected void initViewData() {

        try {
            String username = GlobalUtils.getUserInfo().getNick_name();
            mTvUserName.setText(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                Intent intent = new Intent(this, AiteActivity.class);
                if (aiteFollows != null && aiteFollows.size() != 0) {
                    intent.putExtra(IVariable.DATA, ((Serializable) aiteFollows));
                }

                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.rl_auth_select:
                AppointSpinnerPop.showSpinnerPop(this,mRlAuthSelect,auths);
                break;
            case R.id.rl_sex_select:
                AppointSpinnerPop.showSpinnerPop(this,mRlSexSelect,sexs);
                break;
            case R.id.tv_right_icon:
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
