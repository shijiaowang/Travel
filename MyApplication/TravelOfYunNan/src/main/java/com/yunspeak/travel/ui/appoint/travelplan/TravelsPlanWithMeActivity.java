package com.yunspeak.travel.ui.appoint.travelplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.db.DBManager;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination.adddestination.ProvinceBean;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.LineBean;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.LinePlanEvent;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.SelectDestinationActivity;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.ui.view.FontsIconButton;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.JsonUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/9/17.
 * 找人带-旅行计划
 */
public class TravelsPlanWithMeActivity extends TravelsPlanBaseActivity {

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private OptionsPickerView pvOptions;
    private String name;
    private String des;
    private String address;
    private String id;
    private String cityName;
    private LayoutInflater inflater;
    private List<LineBean.Destination> destinations = new ArrayList<>();
    private FlowLayout mFlDestination;
    private TextView mTvStartCity;
    private FontsIconButton mBtDestination;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
        inflater = LayoutInflater.from(this);
    }

    @Override
    protected boolean isHideRight() {
        return true;
    }

    @Override
    protected int initChildLayoutRes() {
        return R.layout.activity_travels_plan_with_me;
    }

    @Override
    protected void initChildEvent() {
        mBtDestination = (FontsIconButton) findViewById(R.id.bt_destination);
        mTvStartCity = (TextView) findViewById(R.id.tv_start_city);
        mFlDestination = (FlowLayout) findViewById(R.id.fl_destination);
        initCity();
        mBtDestination.setOnClickListener(this);
        mTvStartCity.setOnClickListener(this);
    }


    private void initCity() {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    options1Items = DBManager.getProvince();
                    options2Items = DBManager.getCity(options1Items);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




    private void showCitySelect() {
        //选项选择器
        if (pvOptions == null) {
            pvOptions = new OptionsPickerView(this);
            //三级联动效果
            pvOptions.setPicker(options1Items, options2Items, true);
            //设置选择的三级单位
//          pwOptions.setLabels("省", "市", "区");
            pvOptions.setTitle("选择城市");
            //pvOptions.setSelectOptions(24, 1, 1);//默认选中云南 25-1
           pvOptions.setCyclic(false, true, true);
            pvOptions.setCancelable(true);
            //设置默认选中的三级项目
            pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1Items.get(options1).getPickerViewText()
                            + options2Items.get(options1).get(option2);
                    address = tx;
                    id = options1Items.get(options1).getId();//省得id
                    cityName = options2Items.get(options1).get(option2);
                    mTvStartCity.setText(cityName);
                }
            });

        }
        pvOptions.show();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 过滤按键动作
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (pvOptions != null && pvOptions.isShowing()) {
                pvOptions.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void childClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right_icon:
            case R.id.bt_destination:
                startActivity(new Intent(this, SelectDestinationActivity.class));
                break;
            case R.id.tv_start_city:
                showCitySelect();
                break;
        }
    }

    @Override
    protected EditText getEdView() {
        return null;
    }

    @Override
    protected void addChildJson(JSONObject basecJsonObject) throws Exception {
        JsonUtils.putString(IVariable.MEET_ADDRESS, cityName, basecJsonObject);

        if (destinations.size() == 0) {
            throw new Exception("you must to select some destination");
        }
        JSONArray routesJsonArray = JsonUtils.getRoutesJsonArray();
        for (LineBean.Destination destination : destinations) {
            JSONObject jsonObject = new JSONObject();
            JsonUtils.putString(IVariable.TD_ID, destination.getId(), jsonObject);
            routesJsonArray.put(jsonObject);
        }
    }

    @Subscribe
    public void onEvent(LinePlanEvent linePlanEvent) {
        dealData(linePlanEvent);
    }

    private void dealData(LinePlanEvent linePlanEvent) {
        destinations.add(linePlanEvent.getDestination());
        if (destinations.size() >= 3) {
            mBtDestination.setVisibility(View.GONE);
        }
        mFlDestination.setVisibility(View.VISIBLE);
        mFlDestination.removeAllViews();
        for (LineBean.Destination destination : destinations) {
            View inflate = inflater.inflate(R.layout.item_appointt_with_me_people, mFlDestination, false);
            ((TextView) inflate.findViewById(R.id.tv_name)).setText(destination.getName());
            final View mView = inflate.findViewById(R.id.tv_delete);
            mView.setTag(destination);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        LineBean.Destination tag = (LineBean.Destination) mView.getTag();
                        int index = destinations.indexOf(tag);
                        mFlDestination.removeViewAt(index);
                        destinations.remove(index);
                        GlobalValue.mSelectSpot.remove(tag.getId());
                        if (destinations.size() < 3) {
                            mBtDestination.setVisibility(View.VISIBLE);
                        }
                        if (destinations.size() <= 0) {
                            mFlDestination.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mFlDestination.addView(inflate);
        }
    }
}