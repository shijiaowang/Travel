package com.example.administrator.travel.ui.appoint.choicesequipment;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.LoadingBarBaseActivity;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.LogUtils;
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
import java.util.Set;

/**
 * Created by Administrator on 2016/8/31 0031.
 * 选择道具界面
 */
public class ChoicePropsActivity extends LoadingBarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.lv_type)
    private ListView mLvType;
    @ViewInject(R.id.lv_equ)
    private ListView mLvEqu;
    @ViewInject(R.id.bt_sure)
    private Button mBtSure;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    @ViewInject(R.id.tv_equ_number)
    private TextView mTvEquNumber;
    @ViewInject(R.id.rl_equ)
    private RelativeLayout mRlEqu;
    @ViewInject(R.id.rl_bottom)
    private RelativeLayout mRlBottom;
    @ViewInject(R.id.rl_select)
    private RelativeLayout mRlSelect;
    @ViewInject(R.id.lv_select_equ)
    private ListView mLvSelect;
    private PopEquAdapter popEquAdapter;
    private String type = "1";//默认先读取第一个
    private String preType = "1";
    private List<ChoicePropBean.DataBean.ProptypeBean> mPropType;
    private ChoicePropsLeftAdapter choicePropsLeftAdapter;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_choice_props;
    }

    @Override
    protected void initEvent() {
        mRlEqu.setOnClickListener(this);
        mBtSure.setOnClickListener(this);
        mRlSelect.setOnClickListener(this);
        mLvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = mPropType.get(position).getId();
                if (type.equals(preType)) {
                    return;
                }
                GlobalValue.choicePropType = position;
                choicePropsLeftAdapter.notifyDataSetChanged();
                reqData();
            }
        });
    }

    @Override
    protected void onLoad() {
        reqData();
    }

    private void reqData() {
        Map<String, String> propMap = MapUtils.Build().addKey(this).addType(type).end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_PROP_LIST, propMap, 0, new ChoicePropEvent());
        preType = type;
    }

    @Override
    protected Activity initViewData() {
        changeNumber();//初始化
        return this;
    }

    @Subscribe
    public void onEvent(ChoicePropEvent event) {
        setIsProgress(false);
        if (event.isSuccess()) {
            try {
                dealData(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showToast(event.getMessage());
        }
    }

    private void dealData(ChoicePropEvent event) {
        if (event.isSelectEqu()) {//处理选中后更新个数
            changeNumber();
            return;
        }
        ChoicePropBean choicePropBean = GsonUtils.getObject(event.getResult(), ChoicePropBean.class);
        if (mPropType == null) {
            mPropType = choicePropBean.getData().getProptype();
            choicePropsLeftAdapter = new ChoicePropsLeftAdapter(this, mPropType);
            mLvType.setAdapter(choicePropsLeftAdapter);
        }
        List<ChoicePropBean.DataBean.ProplistsBean> mPropList = choicePropBean.getData().getProplists();
        mLvEqu.setAdapter(new ChoicePropsRightAdapter(this, mPropList));
    }

    private void changeNumber() {
        if (GlobalValue.mPropSelects==null)return;
        Iterator<String> iterator = GlobalValue.mPropSelects.keySet().iterator();
        int selectTotal = 0;
        while (iterator.hasNext()) {
            ChoicePropSelectBean choicePropSelectBean = GlobalValue.mPropSelects.get(iterator.next());
            selectTotal += choicePropSelectBean.getNumber();
        }
        mTvNumber.setText(getString(R.string.prop_select_number, selectTotal));
        mTvEquNumber.setText(selectTotal + "");
    }

    @Override
    protected String setTitleName() {
        return "选择道具";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sure:
                try {
                    saveData();
                    LogUtils.e(JsonUtils.getPropJsonArray().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_equ:
                showSelected();
                break;
            case R.id.rl_select:
                goneEqu();
                break;
        }
    }

    private void saveData() throws Exception {

        if (GlobalValue.mPropSelects == null) {
            ToastUtils.showToast("您未租用任何道具。");
            finish();
            return;
        }
        JSONArray jsonArray=new JSONArray();
        Set<String> set = GlobalValue.mPropSelects.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            ChoicePropSelectBean choicePropSelectBean = GlobalValue.mPropSelects.get(iterator.next());
            JSONObject jsonObject = new JSONObject();
            JsonUtils.putString(IVariable.TP_ID, choicePropSelectBean.getId(), jsonObject);
            JsonUtils.putString(IVariable.NUMBER, choicePropSelectBean.getNumber() + "", jsonObject);
            jsonArray.put(jsonObject);
        }
        JsonUtils.setPropArray(jsonArray);
        setResult(RESULT_CODE);//没有出异常设置result
        finish();
    }

    private void goneEqu() {
        mRlSelect.setVisibility(View.GONE);
    }

    /**
     * 已选择的道具
     */
    private void showSelected() {
        if (GlobalValue.mPropSelects == null || GlobalValue.mPropSelects.size() == 0) {
            ToastUtils.showToast("您尚未选择任何道具。");
            return;
        }
        if (mRlSelect.getVisibility() == View.VISIBLE) {
            mRlSelect.setVisibility(View.INVISIBLE);
        } else {
            mRlSelect.setVisibility(View.VISIBLE);
            mRlSelect.setClickable(true);
            mRlSelect.setFocusable(true);
            mRlSelect.requestFocus();
            showSelect();
        }
    }

    private void showSelect() {

        Iterator<String> iterator = GlobalValue.mPropSelects.keySet().iterator();
        List<ChoicePropSelectBean> choicePropSelectBeans = new ArrayList<>();
        while (iterator.hasNext()) {
            ChoicePropSelectBean choicePropSelectBean = GlobalValue.mPropSelects.get(iterator.next());
            choicePropSelectBeans.add(choicePropSelectBean);
        }
        if (popEquAdapter == null) {
            popEquAdapter = new PopEquAdapter(this,choicePropSelectBeans);
            mLvSelect.setAdapter(popEquAdapter);
        }else {
            popEquAdapter.notifyData(choicePropSelectBeans);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && mRlSelect.getVisibility() == View.VISIBLE) {
            mRlSelect.setVisibility(View.GONE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValue.choicePropType = 0;
    }
}
