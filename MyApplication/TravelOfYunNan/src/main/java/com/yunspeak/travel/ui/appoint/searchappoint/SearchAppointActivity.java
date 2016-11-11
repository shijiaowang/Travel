package com.yunspeak.travel.ui.appoint.searchappoint;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.global.SendTextClick;
import com.yunspeak.travel.ui.appoint.AppointBean;
import com.yunspeak.travel.ui.appoint.AppointEvent;
import com.yunspeak.travel.ui.appoint.popwindow.AppointCommonPop;
import com.yunspeak.travel.ui.appoint.popwindow.AppointOrderPop2;
import com.yunspeak.travel.ui.appoint.together.AppointTogetherBean;
import com.yunspeak.travel.ui.appoint.withme.AppointWithMeBean;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.find.findcommon.CityBean;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/11/11 0011.
 * 搜索约伴页面
 */

public class SearchAppointActivity extends BaseRecycleViewActivity<SearchAppointEvent, AppointWithMeBean, Object> implements View.OnClickListener {
    private boolean isTogether = true;
    private EditText mEtSearch;
    private TextView mTvSearch;
    private TextView mTvSelect;
    private boolean isInitLabel;
    private Map<String, List<CityBean>> rights;
    private List<CityBean> lefts;
    private TextView mTvType;
    private TextView mTvTime;
    private TextView mTvOrder;
    String[] orderType = {"最新发布", "价格升序", "价格降序","出行人数","行程天数"};
    String[] timeType = {"默认时序","一周内", "一个月内", "一个月以上"};
    public  int timePosition = 1;
    public  int orderPosition = 1;//选中的
    public  String label="";
    private AppointCommonPop appointCommonPop;
    private String content;

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<Object> mDatas) {
        return new SearchAppointAdapter(mDatas, this);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        initLeftSelect();
        mVsContent.setLayoutResource(R.layout.activity_search_appoint_head);
        mVsContent.inflate();
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mTvSelect = (TextView) findViewById(R.id.tv_select);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvOrder = (TextView) findViewById(R.id.tv_order);
        content = getIntent().getStringExtra(IVariable.DATA);
        if (!StringUtils.isEmpty(content)){
            mEtSearch.setText(content);
        }
        mTvSelect.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);
        mTvOrder.setOnClickListener(this);
        mTvType.setOnClickListener(this);
        mTvTime.setOnClickListener(this);
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

    }

    /**
     * 搜索
     */
    private void search() {
        content = mEtSearch.getText().toString().trim();
        resetIsFirstInflate();
        setIsProgress(true);
        onLoad(TYPE_REFRESH);
    }

    /**
     * 初始化左侧列表
     */
    private void initLeftSelect() {
        lefts = new ArrayList<>(3);
        CityBean cityBean1=new CityBean();
        cityBean1.setId("1");
        cityBean1.setName("用户标签");
        CityBean cityBean2=new CityBean();
        cityBean2.setId("2");
        cityBean2.setName("认证标签");
        CityBean cityBean3=new CityBean();
        cityBean3.setId("3");
        cityBean3.setName("玩法");
        lefts.add(cityBean1);
        lefts.add(cityBean2);
        lefts.add(cityBean3);
    }

    public static void start(Context context,String content){
       Intent intent=new Intent(context,SearchAppointActivity.class);
       intent.putExtra(IVariable.DATA,content);
       context.startActivity(intent);
   }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addLabel(label).add("time",timePosition+"").add("sort",orderPosition+"").addContent(content);
    }

    @Override
    protected String initUrl() {
        if (isTogether) {
            return IVariable.PLAY_TOGETHER;
        }
        return IVariable.PLAY_WITHE_ME;
    }
    @Override
    protected boolean isUseChild() {
        return true;
    }

    @Override
    protected Class<? extends ParentBean> useChildedBean() {
        if (isTogether) {
            return AppointTogetherBean.class;
        } else {
            return AppointWithMeBean.class;
        }
    }

    @Override
    protected String initTitle() {
        return "搜索约伴";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_type:
                if (isInitLabel){
                    showType();
                }else {
                    getLabel();
                }

                break;
            case R.id.tv_order:
                AppointOrderPop2.showOrderPop(this,mTvOrder, new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        orderPosition=type;
                        mTvOrder.setText(orderType[type-1]);
                        onLoad(TYPE_REFRESH);
                    }
                },orderPosition-1);
                break;
            case R.id.tv_time:
                AppointOrderPop2.showTimePop(this, mTvTime, new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        timePosition=type;
                        mTvTime.setText(timeType[type-1]);
                        //refreshAppoint();
                    }
                },timePosition-1);
                break;
            case R.id.tv_search:
                search();
                break;
            case R.id.tv_select:
                AppointOrderPop2.showAppointType(this, mTvSelect, new SendTextClick() {
                    @Override
                    public void onClick(String text) {
                        mTvSelect.setText(text);
                        isTogether=!isTogether;
                        onLoad(TYPE_REFRESH);
                    }
                },isTogether);
                break;
        }
    }

    @Subscribe
    public void onEvent(AppointEvent appointEvent){
        if (isInitLabel)return;
        AppointBean appointBean = GsonUtils.getObject(appointEvent.getResult(), AppointBean.class);
        try {
            AppointBean.DataBean data = appointBean.getData();
            List<CityBean> labelUser = data.getLabel_user();
            List<CityBean> labelOffice = data.getLabel_office();
            List<CityBean> labelPlay = data.getLabel_play();
            rights = new HashMap<>();
            rights.put("1",labelUser);
            rights.put("2",labelOffice);
            rights.put("3",labelPlay);
            isInitLabel=true;
        } catch (Exception e) {
            e.printStackTrace();
            isInitLabel = false;
        }

    }
    private void showType() {
        if (appointCommonPop == null) {
            lefts.get(0).setChecked(true);
            appointCommonPop = AppointCommonPop.newInstance(lefts, rights, new ParentPopClick() {
                @Override
                public void onClick(int type) {
                    label = appointCommonPop.getTyepName();
                    onLoad(TYPE_REFRESH);
                }
            });
        }
        if (appointCommonPop.isShowing()) {
            appointCommonPop.dismiss();
        } else {
            appointCommonPop.showDown(this,mTvType);
        }
    }
    private void getLabel() {
        Map<String, String> end = MapUtils.Build().addKey().end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_PLAY_LABEL,end,0,new AppointEvent());
    }
}
