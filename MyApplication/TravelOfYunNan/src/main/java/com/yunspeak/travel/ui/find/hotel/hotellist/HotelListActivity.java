package com.yunspeak.travel.ui.find.hotel.hotellist;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CityBean;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.find.hotel.hotelreservation.CalendarEvent;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by wangyang on 2017/2/27.
 * 酒店列表
 */

public class HotelListActivity extends BaseNetWorkActivity {
    @BindView(R.id.ll_hotel_list_select)
    LinearLayout llHotelListSelect;
    @BindView(R.id.ll_hotel_list_area)
    LinearLayout llHotelListArea;
    @BindView(R.id.ll_hotel_list_money)
    LinearLayout llHotelListMoney;
    private int currentMoneyMode = -1;
    private static final int UP_OR_PRESS = 1;
    private static final int DOWN_OR_NORMAL = 2;
    private int currentAreaMode = UP_OR_PRESS;
    private int currentSelectMode = UP_OR_PRESS;
    @BindView(R.id.tv_hotel_list_in_out)
    TextView tvHotelListInOut;
    @BindView(R.id.tv_hotel_list_day)
    TextView tvHotelListDay;
    @BindView(R.id.tv_search)
    FontsIconTextView tvSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rl_destination)
    RelativeLayout rlDestination;
    @BindView(R.id.tv_hotel_list_select)
    TextView tvHotelListSelect;
    @BindView(R.id.tv_hotel_list_are)
    TextView tvHotelListAre;
    @BindView(R.id.tv_hotel_list_money)
    TextView tvHotelListMoney;
    private CalendarEvent calendarEvent;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日", Locale.CHINESE);
    private List<CityBean> lefts;
    private Map<String,List<CityBean>> rights;
    private SinglePop singlePop;

    @Override
    protected void initEvent() {
        calendarEvent = (CalendarEvent) getIntent().getSerializableExtra(IVariable.DATA);
        if (calendarEvent == null) finish();

        initDate();

    }

    private void initDate() {
        String start = simpleDateFormat.format(calendarEvent.getStart().getTime());
        String end = simpleDateFormat.format(calendarEvent.getEnd().getTime());
        start = getStringSubZero(start);
        end = getStringSubZero(end);
        String text = "住 " + start + "\n离 " + end;
        tvHotelListInOut.setText(text);
        tvHotelListDay.setText(String.format("%d晚", CalendarUtils.getHowDay(calendarEvent.getStart().getTime().getTime() + "", calendarEvent.getEnd().getTime().getTime() + "") - 1));
        initSelectBean();
    }

    /**
     * 初始化选择
     */
    private void initSelectBean() {
        lefts = new ArrayList<>();
        lefts.add(new CityBean("价格","1",true));
        lefts.add(new CityBean("星级","2",false));
        rights = new HashMap<>();
        List<CityBean> money=new ArrayList<>();
        money.add(new CityBean("11",0,Integer.MAX_VALUE,"1","不限"));
        money.add(new CityBean("12",0,150,"1","0-150元"));
        money.add(new CityBean("13",151,300,"1","151-300元"));
        money.add(new CityBean("14",301,450,"1","301-450元"));
        money.add(new CityBean("15",451,Integer.MAX_VALUE,"1","450元以上"));
        rights.put("1",money);
        List<CityBean> star=new ArrayList<>();
        star.add(new CityBean("21",0,5,"2","不限"));
        star.add(new CityBean("22",3,3,"2","三星"));
        star.add(new CityBean("23",4,4,"2","四星"));
        star.add(new CityBean("24",5,5,"2","五星"));
        rights.put("2",star);
    }

    /**
     * 去掉0
     *
     * @param endText
     * @return
     */
    @NonNull
    private String getStringSubZero(String endText) {
        if (endText.startsWith("0")) {
            endText = endText.substring(1, endText.length());
        }
        return endText;
    }

    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
    }

    @Override
    protected void onSuccess(HttpEvent httpEvent) {

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_hotel_list;
    }

    @Override
    protected String initTitle() {
        return "酒店列表";
    }


    @OnClick({R.id.ll_hotel_list_select, R.id.ll_hotel_list_area, R.id.ll_hotel_list_money})
    public void onClick(View view) {
        Drawable drawable;
        switch (view.getId()) {
            case R.id.ll_hotel_list_select:
                currentSelectMode=UP_OR_PRESS;
                selectCursor();
                if (singlePop==null) {
                    singlePop = new SinglePop(lefts, rights, new SinglePop.OnSelectListener() {
                        @Override
                        public void onChange(Map<String, CityBean> cityBeanMap) {
                           if (cityBeanMap==null || cityBeanMap.size()==0){
                               currentSelectMode=DOWN_OR_NORMAL;
                               selectCursor();
                               tvHotelListSelect.setTextColor(UIUtils.getColor(R.color.color646464));
                           }else {
                               tvHotelListSelect.setTextColor(UIUtils.getColor(R.color.otherTitleBg));
                           }
                        }

                        @Override
                        public void clear() {
                            currentSelectMode=DOWN_OR_NORMAL;
                            selectCursor();
                            tvHotelListSelect.setTextColor(UIUtils.getColor(R.color.color646464));
                        }

                        @Override
                        public void onCancel(int size) {
                            if (size==0){
                                currentSelectMode=DOWN_OR_NORMAL;
                                selectCursor();
                                tvHotelListSelect.setTextColor(UIUtils.getColor(R.color.color646464));
                            }else {
                                tvHotelListSelect.setTextColor(UIUtils.getColor(R.color.otherTitleBg));
                            }
                        }
                    });
                }
                singlePop.showDown(this,tvHotelListSelect);
                break;
            case R.id.ll_hotel_list_area:
                if (currentAreaMode == DOWN_OR_NORMAL) {
                    currentAreaMode = UP_OR_PRESS;
                    drawable = getResources().getDrawable(R.drawable.hotel_list_select_select);
                } else {
                    currentAreaMode = DOWN_OR_NORMAL;
                    drawable = getResources().getDrawable(R.drawable.hotel_list_select_normal);
                }
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvHotelListAre.setCompoundDrawables(null, null, drawable, null);
                break;
            case R.id.ll_hotel_list_money:
                tvHotelListMoney.setTextColor(UIUtils.getColor(R.color.otherTitleBg));
                if (currentMoneyMode == UP_OR_PRESS) {
                    currentMoneyMode = DOWN_OR_NORMAL;
                    drawable = getResources().getDrawable(R.drawable.hotel_list_money_down);
                } else {
                    currentMoneyMode = UP_OR_PRESS;
                    drawable = getResources().getDrawable(R.drawable.hotel_list_money_up);
                }
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvHotelListMoney.setCompoundDrawables(null, null, drawable, null);
                break;
        }
    }

    private void selectCursor() {
        Drawable drawable;
        if (currentSelectMode == DOWN_OR_NORMAL) {
            currentSelectMode = UP_OR_PRESS;
            drawable = getResources().getDrawable(R.drawable.hotel_list_select_select);
        } else {
            currentSelectMode = DOWN_OR_NORMAL;
            drawable = getResources().getDrawable(R.drawable.hotel_list_select_normal);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvHotelListSelect.setCompoundDrawables(null, null, drawable, null);
    }
}
