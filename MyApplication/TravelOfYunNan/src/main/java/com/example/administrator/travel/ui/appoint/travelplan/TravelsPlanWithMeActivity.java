package com.example.administrator.travel.ui.appoint.travelplan;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.TravelsApplication;
import com.example.administrator.travel.bean.Destination;
import com.example.administrator.travel.db.DBManager;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.appoint.adddestination.ProvinceBean;
import com.example.administrator.travel.ui.appoint.aite.AiteFollow;
import com.example.administrator.travel.ui.appoint.lineplan.LineBean;
import com.example.administrator.travel.ui.appoint.lineplan.LinePlanEvent;
import com.example.administrator.travel.ui.appoint.personnelequipment.PersonnelEquipmentWithMeActivity;
import com.example.administrator.travel.ui.appoint.selectdestination.SelectDestinationActivity;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.FontsIconButton;
import com.example.administrator.travel.utils.ActivityUtils;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by android on 2016/9/17.
 * 找人带-旅行计划
 */
public class TravelsPlanWithMeActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @ViewInject(R.id.tv_start)
    private TextView mTvStart;
    @ViewInject(R.id.tv_end)
    private TextView mTvEnd;
    @ViewInject(R.id.tv_end_time)
    private TextView mTvEndTime;
    @ViewInject(R.id.tv_start_time)
    private TextView mTvStartTime;
    @ViewInject(R.id.bt_destination)
    private FontsIconButton mBtDestination;
    @ViewInject(R.id.tv_start_city)
    private TextView mTvStartCity;
    @ViewInject(R.id.fl_destination)
    private FlowLayout mFlDestination;
    private TextView mTvRightNext;
    private int dayOfYear;
    private int year;
    private TimePickerView pvTime;
    private Date startDate = new Date();
    private Date endDate = new Date();
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private OptionsPickerView pvOptions;
    private String name;
    private String des;
    private String address;
    private String id;
    private String cityName;
    private LayoutInflater inflater;
    private List<LineBean.Destination> destinations=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
        registerEventBus(this);
        inflater = LayoutInflater.from(this);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_plan_with_me;
    }

    @Override
    protected void initEvent() {
        initCity();
        mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(new Date());
        dayOfYear = currentDay.get(Calendar.DAY_OF_YEAR);
        year = currentDay.get(Calendar.YEAR);
        mTvStartTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        mTvEndTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        mTvRightNext.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
        mTvStart.setOnClickListener(this);
        mTvEnd.setOnClickListener(this);
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

    @Override
    protected void initViewData() {

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
            pvOptions.setSelectOptions(24, 1, 1);//默认选中云南 25-1
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
    protected String setTitleName() {
        return "旅行计划";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 过滤按键动作
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (pvTime != null && pvTime.isShowing()) {
                pvTime.dismiss();
                return true;
            }
            if (pvOptions!=null && pvOptions.isShowing()){
                pvOptions.dismiss();
                return true;
            }

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                showTime(mTvStartTime);
                break;
            case R.id.tv_end:
                showTime(mTvEndTime);
                break;
            case R.id.tv_right_icon:
            case R.id.bt_next:
                if (checkTimeAgain()) {
                    try {
                        checkJson();
                        startActivity(new Intent(this, PersonnelEquipmentWithMeActivity.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.showToast("请完善路线相关信息！");
                    }

                }
                break;
            case R.id.bt_destination:
                startActivity(new Intent(this, SelectDestinationActivity.class));
                break;
            case R.id.tv_start_city:
                showCitySelect();
                break;
        }
    }

    /**
     * 检查并且添加json数据
     */
    private void checkJson() throws Exception {
        JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
        JsonUtils.putString(IVariable.USER_ID, GlobalUtils.getUserInfo().getId(), basecJsonObject);
        JsonUtils.putString(IVariable.START_TIME, startDate.getTime() / 1000 + "", basecJsonObject);
        JsonUtils.putString(IVariable.END_TIME, endDate.getTime() / 1000 + "", basecJsonObject);
        JsonUtils.putString(IVariable.MEET_ADDRESS, cityName, basecJsonObject);

        if (destinations.size()==0){
            throw new Exception("you must to select some destination");
        }
        JSONArray routesJsonArray = JsonUtils.getRoutesJsonArray();
        for (LineBean.Destination destination:destinations){
            JSONObject jsonObject=new JSONObject();
            JsonUtils.putString(IVariable.TD_ID,destination.getId(),jsonObject);
            routesJsonArray.put(jsonObject);

        }
    }

    private void showTime(final TextView currentText) {
        if (pvTime == null) {
            pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
            //控制时间范围
            Calendar calendar = Calendar.getInstance();
            pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);//要在setTime 之前才有效果
            pvTime.setTime(new Date());
            pvTime.setCyclic(false);
            pvTime.setCancelable(true);
            pvTime.setTitle(currentText == mTvStartTime ? "开始时间" : "结束时间");
        } else {
            pvTime.setTitle(currentText == mTvStartTime ? "开始时间" : "结束时间");
        }
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                checkTime(currentText, date);
            }


        });
        pvTime.show();

    }

    /**
     * 检查日期是否合法
     *
     * @param currentText
     * @param date
     */
    public void checkTime(TextView currentText, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_YEAR) < dayOfYear && calendar.get(Calendar.YEAR) == year) {
            ToastUtils.showToast("对不起，您所选的时间已过期");
            return;
        }
        currentText.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        if (currentText == mTvStartTime) {
            startDate = date;
        } else {
            endDate = date;
        }

    }

    private boolean checkTimeAgain() {
        if (endDate.getTime() < startDate.getTime()) {
            ToastUtils.showToast("对不起，结束时间不能小于出发时间");
            return false;
        }

        String howDay = CalendarUtils.getHowDay(startDate.getTime() + "", endDate.getTime() + "");
        if (Integer.parseInt(howDay) > 30) {
            ToastUtils.showToast("对不起，计划最长时间为30天，若有特殊需要请联系客服。");
            return false;
        }
        return true;
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
        for (LineBean.Destination destination:destinations) {
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        JsonUtils.reset();
        GlobalValue.mSelectSpot = null;
        GlobalValue.mLineBeans = null;
        unregisterEventBus(this);
    }
}

