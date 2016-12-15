package com.yunspeak.travel.ui.find.findcommon;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.popwindow.AppointCommonPop;
import com.yunspeak.travel.ui.appoint.popwindow.AppointOrderPop;
import com.yunspeak.travel.ui.appoint.popwindow.AppointOrderPop2;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.LineBean;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.LinePlanEvent;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.SelectDestinationAdapter;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination.CustomDestinationActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/30.
 * 目的地  美食共用
 */
public class FindCommonActivity extends BaseRecycleViewActivity<DestinationEvent, DestinationBean, DestinationBean.DataBean.BodyBean> implements View.OnClickListener {
    private String content = "";//搜索内容
    private String province = "";
    private String city = "";
    private String typelist = "";
    private String playList = "";
    private String star = "";
    private String score = "";
    private int type;
    private String url;
    public static final int DESTINATION_NORMAL = 1;
    public static final int DESTINATION_SELECTION = 2;
    public static final int DELICIOUS_NORMAL = 3;
    private EditText mEtSearch;
    private boolean loactionIsGet = false;
    String[] timeTypePop = {"·\u3000默认", "·\u3000按星级", "·\u3000按评分"};
    String[] orderType = {"默认", "按星级", "按评分"};
    private int orderPosition = 0;//选中的

    private AppointOrderPop appointOrderPop;
    private AppointCommonPop appointCommonPop;
    private TextView mTvLocation;
    private TextView mTvType;
    private TextView mTvOrder;
    private List<CityBean> provinceBean;
    private List<CityBean> cityBean;
    private Map<String, List<CityBean>> citys;
    private Map<String, List<CityBean>> plays;
    private int position;
    private List<CityBean> lefts;
    private AppointCommonPop appointType;
    private List<CityBean> foodBean;
    private String foodType;
    private PopupWindow popupWindow;


    @Override
    protected void initEvent() {
        super.initEvent();

        mVsContent.setLayoutResource(R.layout.activity_destination);
        mVsContent.inflate();
        changeMargin(0, 5);
        type = getIntent().getIntExtra(IVariable.TYPE, -1);
        mTvTitle.setText(initTitle());
        url = type == DELICIOUS_NORMAL ? IVariable.FIND_FOOD : IVariable.FIND_DESTINATION;
        FrameLayout mFlTop = (FrameLayout) findViewById(R.id.fl_top);
        View inflate;
        if (type == DESTINATION_SELECTION) {
            position = getIntent().getIntExtra(IVariable.POSITION, -1);
            inflate = LayoutInflater.from(this).inflate(R.layout.activity_select_destination, mFlTop, false);
            Button mBtDiy = (Button) inflate.findViewById(R.id.bt_diy);
            if (GlobalValue.mAppointType == IVariable.TYPE_WITH_ME) {
                mBtDiy.setVisibility(View.GONE);
            } else {
                mBtDiy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FindCommonActivity.this, CustomDestinationActivity.class);
                        intent.putExtra(IVariable.POSITION, position);
                        startActivityForResult(intent, REQ_CODE);
                    }
                });

            }
        } else {
            inflate = LayoutInflater.from(this).inflate(R.layout.activity_findcommon_top, mFlTop, false);
        }
        mFlTop.addView(inflate);
        TextView mTvSearch = (TextView) findViewById(R.id.tv_search);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mTvOrder = (TextView) findViewById(R.id.tv_order);
        mTvLocation = (TextView) findViewById(R.id.tv_location);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvOrder.setOnClickListener(this);
        mTvLocation.setOnClickListener(this);
        mTvType.setOnClickListener(this);
        mTvType.setOnClickListener(this);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //一般输入法或搜狗输入法点击搜索按键
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //这里调用搜索方法
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0); //强制隐藏键盘
                    search();
                    return true;
                }
                return false;
            }
        });
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        if (type!=DELICIOUS_NORMAL) {
            CityBean cityBean1 = new CityBean();
            cityBean1.setId("1");
            cityBean1.setName("按玩法");
            cityBean1.setChecked(true);
            CityBean cityBean2 = new CityBean();
            cityBean2.setId("2");
            cityBean2.setName("按类型");
            lefts = new ArrayList<>();
            lefts.add(cityBean1);
            lefts.add(cityBean2);
        }
    }

    @Override
    public void onItemClick(int position) {
        super.onItemClick(position);
        if (DESTINATION_SELECTION==type){
            GlobalValue.clickPosition = position;
            mAdapter.notifyItemChanged(position);
        }
    }

    public static void start(Context context, int type, int position) {
        Intent intent = new Intent(context, FindCommonActivity.class);
        intent.putExtra(IVariable.TYPE, type);
        intent.putExtra(IVariable.POSITION,position);
        context.startActivity(intent);
    }

    private void search() {
        content = mEtSearch.getText().toString().trim();
        resetIsFirstInflate();
        setIsProgress(true);
        onLoad(TYPE_REFRESH);
    }


    @Override
    protected List<DestinationBean.DataBean.BodyBean> childChangeData(final DestinationBean parentBean, DestinationEvent destinationEvent) {
        if (!loactionIsGet) {
            try {
                x.task().run(new Runnable() {
                    @Override
                    public synchronized void run() {
                        if (loactionIsGet) return;
                        provinceBean = parentBean.getData().getProvince();
                        cityBean = parentBean.getData().getCity();
                        citys = new HashMap<>();
                        for (CityBean provice : provinceBean) {
                            List<CityBean> cityBeanList = new ArrayList<>();
                            citys.put(provice.getId(), cityBeanList);
                        }
                        for (CityBean cityBean1 : cityBean) {
                            if (cityBean1 != null && citys.containsKey(cityBean1.getUpid())) {
                                //包含就放入集合
                                citys.get(cityBean1.getUpid()).add(cityBean1);
                            }
                        }
                        if (type==DELICIOUS_NORMAL){
                            foodBean = parentBean.getData().getFood_type();
                        }else {
                            plays = new HashMap<>();
                            List<CityBean> travelPlay = parentBean.getData().getTravel_play();
                            List<CityBean> travelType = parentBean.getData().getTravel_type();
                            plays.put("1", travelPlay);
                            plays.put("2", travelType);
                        }
                        loactionIsGet = true;
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return parentBean.getData().getBody();
    }

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<DestinationBean.DataBean.BodyBean> mDatas) {
        if (type == DESTINATION_SELECTION) {
            return new SelectDestinationAdapter(mDatas, this);
        } else {
            return new DestinationAdapter(mDatas, this, type == DESTINATION_NORMAL);
        }
    }

    @Override
    protected boolean isChangeData() {
        return true;
    }

    @Override
    protected String initRightText() {
        if (type == DESTINATION_SELECTION) {
            return "确定";
        }
        return super.initRightText();
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        if (type == DESTINATION_SELECTION) {
            if (GlobalValue.clickPosition < 0 || GlobalValue.clickPosition > mDatas.size()) {
                ToastUtils.showToast("您尚未选择任何景点。");
                return;
            }
            DestinationBean.DataBean.BodyBean bodyBean = mDatas.get(GlobalValue.clickPosition);
            String add = bodyBean.getCity() + "·" + bodyBean.getTitle();
            String id = bodyBean.getId();
            LinePlanEvent linePlanEvent = new LinePlanEvent();
            linePlanEvent.setPosition(position);
            linePlanEvent.setDestination(new LineBean.Destination(id,add));
            EventBus.getDefault().post(linePlanEvent);
            finish();
        }else {
            super.otherOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE && resultCode == RESULT_CODE) {
            finish();
        }
    }

    @Override
    protected String initUrl() {
        return url;
    }

    private void showLocation() {
        if (!loactionIsGet) return;
        if (appointCommonPop == null && provinceBean != null && provinceBean.size() != 0) {
            provinceBean.get(0).setChecked(true);
            appointCommonPop = AppointCommonPop.newInstance(provinceBean, citys, new ParentPopClick() {
                @Override
                public void onClick(int type) {
                    city = appointCommonPop.getTyepString();
                    onLoad(TYPE_REFRESH);

                }
            });
        }
        if (appointCommonPop.isShowing()) {
            appointCommonPop.dismiss();
        } else {
            appointCommonPop.showDown(this,mTvLocation);
        }
    }

    private void orderPop(final TextView textView, final String[] titile, int clickPosition) {
        if (appointOrderPop == null) {
            appointOrderPop = new AppointOrderPop();
        }
        appointOrderPop.setOnItemClickListener(new AppointOrderPop.OnItemClickListener() {
            @Override
            public void onItemClick(int type) {
                textView.setText(orderType[type]);
                orderPosition = type;
                if (orderPosition == 1) {
                    score = "1";
                    star = "";
                } else if (orderPosition == 2) {
                    star = "1";
                    score = "";
                } else {
                    star = "";
                    score = "";
                }
                onLoad(TYPE_REFRESH);
            }
        });
        appointOrderPop.showOrderPop(this,mTvOrder, titile, clickPosition);
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.add(IVariable.CONTENT, content).add(IVariable.PROVINCE, province).add(IVariable.CITY, city)
                .add(IVariable.STAR, star).add(IVariable.SCORE, score);
        if (type==DELICIOUS_NORMAL){
            builder.add(IVariable.TYPELIST, foodType);
        }else {
            builder.add(IVariable.TYPELIST, typelist).add("playlist",playList);
        }
    }


    @Override
    protected String initTitle() {
        switch (type) {
            case DELICIOUS_NORMAL:
                return "美食";
            case DESTINATION_NORMAL:
                return "目的地";
            default:
                return "选择目的地";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_location:
                showLocation();
                break;
            case R.id.tv_order:
                orderPop(mTvOrder, timeTypePop, orderPosition);
                break;
            case R.id.tv_type:
                if (type==DELICIOUS_NORMAL) {
                    if (foodBean == null || foodBean.size() == 0) return;
                    if (popupWindow != null) {
                        popupWindow.showAsDropDown(mTvType);
                    } else{
                        popupWindow = AppointOrderPop2.showDeliciousType(this, foodBean, mTvOrder, new ParentPopClick() {
                            @Override
                            public void onClick(int type) {
                                CityBean cityBean = foodBean.get(type);
                                foodType = cityBean.getId();
                                mTvType.setText(cityBean.getName());
                                onLoad(TYPE_REFRESH);
                            }
                        });
                }
                }else {
                    showType();
                }

                break;
        }
    }

    private void showType() {
        if (!loactionIsGet) return;
        if (appointType == null && plays != null && plays.size() != 0) {
            provinceBean.get(0).setChecked(true);
            appointType = AppointCommonPop.newInstance(lefts, plays, new ParentPopClick() {
                @Override
                public void onClick(int type) {
                   typelist=appointType.getTypeList();
                    playList=appointType.getPlayList();
                    onLoad(TYPE_REFRESH);
                }
            });
        }
        if (appointType.isShowing()) {
            appointType.dismiss();
        } else {
            appointType.showDown(this,mTvType);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAdapter != null && type == DESTINATION_SELECTION) {//选择景点了需要刷新
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValue.clickPosition = -1;
    }
}
