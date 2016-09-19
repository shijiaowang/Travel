package com.example.administrator.travel.ui.appoint.personnelequipment;

import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.aite.AiteActivity;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.appoint.aite.AiteFollow;
import com.example.administrator.travel.ui.appoint.costsetting.CostSettingActivity;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

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
    private List<AiteFollow> aiteFollows;

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
        try {
            String username= GlobalUtils.getUserInfo().getNick_name();
            mTvUserName.setText(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inflatePeople (final List<AiteFollow> aiteFollows) {
            this.aiteFollows = aiteFollows;
            LayoutInflater layoutInflater=LayoutInflater.from(this);
            if (mFlPeople.getChildCount()>0)mFlPeople.removeAllViews();
            for (int i=0;i<7;i++){
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
        switch (v.getId()){
            case R.id.bt_select_equ:
                startActivityForResult(new Intent(this,AiteActivity.class),REQ_CODE);
                break;
            case R.id.bt_next:
                try {
                    checkDataAndAddJson();
                    startActivity(new Intent(this,CostSettingActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast("请完善约伴信息");
                }

                break;
        }
    }

    /**
     * 制作json信息
     * @throws Exception
     */
    private void checkDataAndAddJson() throws Exception {
        JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
        JsonUtils.putString(IVariable.SEX_CONDITION,"1",basecJsonObject);
        JsonUtils.putString(IVariable.BIND_CONDITION,"1",basecJsonObject);
        JSONArray propJsonArray = JsonUtils.getPropJsonArray();//人员信息
        JSONObject jsonObject=new JSONObject();
        JsonUtils.putString(IVariable.USER_ID,GlobalUtils.getUserInfo().getId(),jsonObject);
        propJsonArray.put(jsonObject);
        if (ListIsEmpty(aiteFollows))return;
        for (int i=0;i<aiteFollows.size();i++){//要加上自己
            jsonObject=new JSONObject();
            JsonUtils.putString(IVariable.USER_ID,aiteFollows.get(i).getFollow().getId(),jsonObject);
            propJsonArray.put(jsonObject);
        }

    }

    /**
     * 读取邀请邀请人信息
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE && resultCode==RESULT_CODE){
            try {
                List<AiteFollow> aiteFollows = (List<AiteFollow>) data.getSerializableExtra(IVariable.DATA);
                if (aiteFollows!=null && aiteFollows.size()!=0){
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
}
