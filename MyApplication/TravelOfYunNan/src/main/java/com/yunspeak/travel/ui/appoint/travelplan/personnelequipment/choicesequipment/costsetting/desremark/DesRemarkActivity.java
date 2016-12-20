package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CreateBean;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.createsuccess.CreateAppointSuccessActivity;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.settingtitle.SettingTitle;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.settingtitle.SettingTitleActivity;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.ConfirmOrdersActivity;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/2 0002.
 * 说明备注
 */
public class DesRemarkActivity extends BaseToolBarActivity implements View.OnClickListener {
    @BindView(R.id.bt_select_equ) Button mBtSelect;
    @BindView(R.id.bt_next) Button mBtNext;
    @BindView(R.id.et_title) EditText mEtTitle;
    @BindView(R.id.et_content) EditText mEtContent;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.fl_title) FlowLayout mFlTitle;
    private List<SettingTitle> settingTitles;
    private LayoutInflater inflater;



    @Override
    protected int initLayoutRes() {
        return  R.layout.activity_des_remark;
    }

    @Override
    protected void initOptions() {
        registerEventBus(this);
        ActivityUtils.getInstance().addActivity(this);
        inflater=LayoutInflater.from(this);
        initEvent();
    }

    @Override
    protected String initTitle() {
        return "说明备注";
    }

    @Override
    protected String initRightText() {
        return "发布";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        saveJsonData();
    }

    protected void initEvent() {

        mBtNext.setOnClickListener(this);
        mBtSelect.setOnClickListener(this);
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = getString(mEtContent);
                mTvNumber.setText(getString(R.string.text_limit_200, string.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }








    @Subscribe
    public void onEvent(DesRemarkEvent desRemarkEvent) {
        ToastUtils.showToast(desRemarkEvent.getMessage());
        try {
            if (desRemarkEvent.isSuccess()) {
                if (GlobalValue.mAppointType==IVariable.TYPE_WITH_ME){
                    startActivity(new Intent(this, CreateAppointSuccessActivity.class));
                }else {
                    CreateBean object = GsonUtils.getObject(desRemarkEvent.getResult(), CreateBean.class);
                    CreateBean.DataBean data = object.getData();
                    Intent intent = new Intent(this, ConfirmOrdersActivity.class);
                    intent.putExtra(IVariable.ID, data.getRet_order());
                    intent.putExtra("pay_type", "1");
                    startActivity(intent);
                }
                ActivityUtils.getInstance().exit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                saveJsonData();
                break;
            case R.id.bt_select_equ:
                Intent intent = new Intent(this, SettingTitleActivity.class);
                if (settingTitles!=null && settingTitles.size()!=0){
                    intent.putExtra(IVariable.DATA, (Serializable) settingTitles);
                }
                startActivityForResult(intent,REQ_CODE);
                break;
        }
    }



    private void saveJsonData() {
        try {
            String content = getString(mEtContent);
            String title = getString(mEtTitle);
            JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
            JsonUtils.putString(IVariable.TITLE, title, basecJsonObject);
            JsonUtils.putString(IVariable.CONTENT, content, basecJsonObject);
            changeJson();
        } catch (Exception e) {
            ToastUtils.showToast("请完善约伴简介。");
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE && resultCode==RESULT_CODE){
            if (data!=null){
                settingTitles = (List<SettingTitle>) data.getSerializableExtra(IVariable.DATA);
                mFlTitle.removeAllViews();
                if (settingTitles.size()==0)return;
                mFlTitle.setVisibility(View.VISIBLE);
                for (SettingTitle settingTitle:settingTitles){
                    View inflate = inflater.inflate(R.layout.item_activity_setting_title_select, mFlTitle, false);
                    final View view = inflate.findViewById(R.id.tv_delete);
                    view.setTag(settingTitle);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SettingTitle tag = ((SettingTitle) view.getTag());
                            if (tag==null)return;
                            int index = settingTitles.indexOf(tag);
                            mFlTitle.removeViewAt(index);
                            settingTitles.remove(index);
                            if (settingTitles.size()==0)mFlTitle.setVisibility(View.GONE);
                        }
                    });
                    TextView mTvTitle = (TextView) inflate.findViewById(R.id.tv_title);
                    mTvTitle.setText(settingTitle.getTitle());
                    if (mFlTitle.getChildCount() >= 7) return;
                    mFlTitle.addView(inflate);
                }
            }
        }
    }



    /**
     * 提交时所用的json
     */
    public void changeJson() {
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            String type=GlobalValue.mAppointType==IVariable.TYPE_WITH_ME?IVariable.USER:IVariable.PROP;
            String url=GlobalValue.mAppointType==IVariable.TYPE_WITH_ME?IVariable.CREATE_WITH_ME:IVariable.CREATE_APPOINT_TOGETHER;
            jsonObject.put(IVariable.BASEC, JsonUtils.getBasecJsonObject());
            jsonObject.put(IVariable.ROUTES, JsonUtils.getRoutesJsonArray());
            jsonObject.put(type, JsonUtils.getPropJsonArray());
            jsonArray.put(jsonObject);
            List<String> file = new ArrayList<>();
            file.add(GlobalValue.mFileName);
            Map<String, String> createMap = MapUtils.Build().addKey().addJsonTravel(jsonArray.toString()).end();
            XEventUtils.postFileCommonBackJson(url,createMap,file,0, new DesRemarkEvent());


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);
    }
}
