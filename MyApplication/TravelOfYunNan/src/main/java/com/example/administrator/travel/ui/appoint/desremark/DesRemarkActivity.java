package com.example.administrator.travel.ui.appoint.desremark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.TravelsApplication;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.createsuccess.CreateAppointSuccessActivity;
import com.example.administrator.travel.ui.activity.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.appoint.settingtitle.SettingTitle;
import com.example.administrator.travel.ui.appoint.settingtitle.SettingTitleActivity;
import com.example.administrator.travel.ui.appoint.settingtitle.TabEvent;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.utils.ActivityUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/2 0002.
 * 说明备注
 */
public class DesRemarkActivity extends LoadingBarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.bt_select_equ)
    private Button mBtSelect;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @ViewInject(R.id.et_title)
    private EditText mEtTitle;
    @ViewInject(R.id.et_content)
    private EditText mEtContent;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    @ViewInject(R.id.fl_title)
    private FlowLayout mFlTitle;
    private Object json;
    private List<SettingTitle> settingTitles;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ActivityUtils.getInstance().addActivity(this);
        inflater=LayoutInflater.from(this);

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_des_remark;
    }

    @Override
    protected void initEvent() {
        TextView mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        mTvRightNext.setText("发布");
        mTvRightNext.setOnClickListener(this);
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

    @Override
    protected void onLoad() {
        setIsProgress(false);
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "说明备注";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right_icon:
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

    @Subscribe
    public void onEvent(DesRemarkEvent event) {
        if (event.isSuccess()) {
            startActivity(new Intent(this, CreateAppointSuccessActivity.class));
        }
        ToastUtils.showToast(event.getMessage());

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
            Map<String, String> createMap = MapUtils.Build().addKey(this).addJsonTravel(jsonArray.toString()).end();
            XEventUtils.postUseCommonBackJson(url,createMap, 0, new DesRemarkEvent());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
