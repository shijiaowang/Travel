package com.example.administrator.travel.ui.appoint.together.togetherdetail;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PeopleBean;
import com.example.administrator.travel.ui.appoint.withme.withmedetail.PricebasecBean;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.adapter.TravelDetailLineAdapter;
import com.example.administrator.travel.ui.appoint.dialog.EnterAppointDialog;
import com.example.administrator.travel.ui.appoint.popwindow.AppointDetailMorePop;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.ImageOptionsUtil;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.TypefaceUtis;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/2 0002.
 * 一起玩约伴详情
 */
public class AppointTogetherDetailActivity extends LoadingBarBaseActivity<AppointDetailEvent> implements View.OnClickListener {
    private static final int TYPE_ENTER_APPOINT=95;
    @ViewInject(R.id.tv_start_add)
    private TextView mTvStartAdd;
    @ViewInject(R.id.tv_end_add)
    private TextView mTvEndAdd;
    @ViewInject(R.id.lv_route_line)
    private ToShowAllListView mLvRouteLine;
    @ViewInject(R.id.iv_user_icon)
    private ImageView mIvUserIcon;
    @ViewInject(R.id.tv_user_nick_name)
    private TextView mTvUserNickName;
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.tv_day)
    private TextView mTvDay;
    @ViewInject(R.id.tv_watch_number)
    private TextView mTvWatchNumber;
    @ViewInject(R.id.tv_love)
    private FontsIconTextView mTvLove;
    @ViewInject(R.id.tv_love_number)
    private TextView mTvLoveNumber;
    @ViewInject(R.id.tv_sex)
    private TextView mTvSex;
    @ViewInject(R.id.iv_appoint_bg)
    private ImageView mIvAppointBg;
    @ViewInject(R.id.tv_title)
    private TextView mTvTitle;
    @ViewInject(R.id.tv_content)
    private TextView mTvContent;
    @ViewInject(R.id.tv_line)
    private TextView mTvLine;
    @ViewInject(R.id.tv_start_and_long)
    private TextView mTvStartAndLong;
    @ViewInject(R.id.tv_day_and_night)
    private TextView mTvDayAndNight;
    @ViewInject(R.id.tv_plan_number)
    private TextView mTvPlanNumber;
    @ViewInject(R.id.tv_have_number)
    private TextView mTvHaveNumber;
    @ViewInject(R.id.tv_have_enter)
    private TextView mTvHaveEnter;//已报名
    @ViewInject(R.id.tv_entering)
    private TextView mTvEntering;//报名中
    @ViewInject(R.id.tv_key)
    private TextView mTvKey;
    @ViewInject(R.id.tv_remark)
    private TextView mTvRemark;
    @ViewInject(R.id.tv_traffic)
    private TextView mTvTraffic;
    @ViewInject(R.id.tv_plan_people)
    private TextView mTvPlanPeople;
    @ViewInject(R.id.tv_switch)
    private TextView mTvSitch;
    @ViewInject(R.id.rv_have_enter)
    private RecyclerView mRvHaveEnter;
    @ViewInject(R.id.rv_entering)
    private RecyclerView mRvEnter;
    @ViewInject(R.id.lv_equ_provider)
    private ToShowAllListView mLvEquProvider;
    @ViewInject(R.id.lv_insurance)
    private ToShowAllListView mLvInsurance;//保险
    @ViewInject(R.id.fl_title)
    private FlowLayout mFlTitle;
    @ViewInject(R.id.tv_price)
    private TextView mTvPrice;
    @ViewInject(R.id.tv_enter)
    private TextView mTvEnter;

   private boolean isDetail=false;//默认缩略图
    private String tId;

    private List<List<AppointTogetherDetail.DataBean.RoutesBean>> lists=new ArrayList<>();
    private TextView mTvRight;
    private String id;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_appoint_together_detail;
    }

    @Override
    protected void initEvent() {
        mTvRight = getmTvRightIcon();
        mTvRight.setTypeface(TypefaceUtis.getTypeface(this));
        mTvRight.setText("掸");
        mTvRight.setOnClickListener(this);
        tId = getIntent().getStringExtra(IVariable.T_ID);
        mTvSitch.setOnClickListener(this);
        mTvEnter.setOnClickListener(this);
    }

    @Override
    protected void onLoad(int type) {
        Map<String, String> travelDetailMap = MapUtils.Build().addKey(this).addUserId().add(IVariable.TID,tId).end();
        XEventUtils.getUseCommonBackJson(IVariable.PLAY_TOGETHER_DETAIL, travelDetailMap,TYPE_REFRESH, new AppointDetailEvent());
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "约伴详情";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected void onSuccess(AppointDetailEvent appointDetailEvent) {
        switch (appointDetailEvent.getType()){
            case TYPE_REFRESH:
                dealData(appointDetailEvent);
                break;
            case TYPE_ENTER_APPOINT:
                EnterAppointDialog.showDialogSuccess(this);
                break;
        }

    }

    @Override
    protected void onFail(AppointDetailEvent appointDetailEvent) {
        super.onFail(appointDetailEvent);
        if (appointDetailEvent.getType()==TYPE_ENTER_APPOINT){
           // ToastUtils.showToast(appointDetailEvent.getMessage());
        }
    }

    /**
     * 填充数据
     * @param event
     */
    private void dealData (AppointDetailEvent event) {
        AppointTogetherDetail appointTogetherDetail = GsonUtils.getObject(event.getResult(), AppointTogetherDetail.class);
        if (appointTogetherDetail==null)return;
        AppointTogetherDetail.DataBean data = appointTogetherDetail.getData();
        List<AppointTogetherDetail.DataBean.RoutesBean> routes = dealDate(data);
        initSomeData(data);
        classificationDay(lists, routes);
        mLvRouteLine.setAdapter(new TravelDetailLineAdapter(this, lists, isDetail));
        List<PeopleBean> ingPeople = data.getIng_people();
        if (ingPeople!=null && ingPeople.size()!=0){
            mRvEnter.setAdapter(new AppointDetailHaveEnterAdapter(this, ingPeople));
            mRvEnter.setLayoutManager(new GridLayoutManager(this, ingPeople.size()));
        }
        List<PeopleBean> intoPeople = data.getInto_people();
        if (intoPeople!=null && intoPeople.size()!=0){
            mRvHaveEnter.setAdapter(new AppointDetailHaveEnterAdapter(this, intoPeople));
            mRvHaveEnter.setLayoutManager(new GridLayoutManager(this, intoPeople.size()));
        }
        List<AppointTogetherDetail.DataBean.PropBean> prop = data.getProp();
        if (prop!=null && prop.size()!=0){
           mLvEquProvider.setAdapter(new ProviderAdapter(this,prop));
            measureHeight(mLvEquProvider);
        }
        List<PricebasecBean> pricebasec = data.getPricebasec();
        if (pricebasec==null){
           pricebasec=new ArrayList<>();
        }
        PricebasecBean pricebasecBean=new PricebasecBean();
        pricebasecBean.setKey("路程费用");
        pricebasecBean.setValue(data.getPrice());
        pricebasec.add(pricebasecBean);
        mLvInsurance.setAdapter(new AppointDetailInsuranceAdapter(this, pricebasec));
        measureHeight(mLvInsurance);
    }

    /**
     * 填充一些数据
     * @param data
     */
    private void initSomeData(AppointTogetherDetail.DataBean data) {
        id = data.getId();
        mTvLove.setTextColor(data.getIs_like().equals("1") ? getResources().getColor(R.color.colorFf8076) : getResources().getColor(R.color.colorb5b5b5));
        x.image().bind(mIvAppointBg, data.getTravel_img(), ImageOptionsUtil.getBySetSize(DensityUtil.dip2px(116), DensityUtil.dip2px(116)));
        x.image().bind(mIvUserIcon, data.getUser_img(), ImageOptionsUtil.getBySetSize(DensityUtil.dip2px(30), DensityUtil.dip2px(30)));
        if (mFlTitle.getChildCount()>0)mFlTitle.removeAllViews();
        String[] split = data.getLabel().split(",");
        for (int i=0;i<split.length;i++){
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_fragment_appoint_title, mFlTitle, false);
            textView.setText(split[i]);
            mFlTitle.addView(textView);
        }
        mTvUserNickName.setText(data.getUser_name());
        mTvLoveNumber.setText(data.getCount_like());
        mTvWatchNumber.setText(data.getBrowse());
        mTvTitle.setText(data.getTitle());
        mTvContent.setText(data.getContent());
        mTvDay.setText(FormatDateUtils.FormatLongTime("MM-dd", data.getAdd_time()));
        mTvTime.setText(FormatDateUtils.FormatLongTime("hh:mm", data.getAdd_time()));
        mTvStartAndLong.setText(data.getMeet_address()+"出发  "+ CalendarUtils.getHowDayHowNight(data.getStart_time()+"000", data.getEnd_time()+"000"));
        mTvDayAndNight.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd", data.getStart_time())+"至"+FormatDateUtils.FormatLongTime("yyyy.MM.dd", data.getEnd_time()));
        mTvHaveNumber.setText("已有："+data.getNow_people()+"人");
        mTvPlanNumber.setText("计划："+data.getMax_people()+"人");
        mTvLine.setText(data.getRoutes_title());
        mTvSex.setText(data.getSex().equals("0")?R.string.activity_member_detail_boy:R.string.activity_member_detail_girl);
        mTvPlanPeople.setText(data.getMin_people()+"-"+data.getMax_people()+"人");
        mTvTraffic.setText(data.getTraffic());
        mTvPrice.setText("¥"+data.getTotal_price());
        mTvRemark.setText(data.getTraffic_text());
        mTvStartAdd.setText(data.getMeet_address());
        mTvEndAdd.setText(data.getOver_address());
        mTvKey.setText(data.getId_code());
        int intoCount=data.getInto_people()==null?0:data.getInto_people().size();
        mTvHaveEnter.setText("已报名("+intoCount+")");
        int ingCount=data.getIng_people()==null?0:data.getIng_people().size();
        mTvEntering.setText("报名中("+ingCount+")");
    }

    /**
     * 处理时间
     * @return
     */
    @NonNull
    private List<AppointTogetherDetail.DataBean.RoutesBean> dealDate( AppointTogetherDetail.DataBean dataBean) {
        List<AppointTogetherDetail.DataBean.RoutesBean> routes = dataBean.getRoutes();
        for (AppointTogetherDetail.DataBean.RoutesBean routesBean: routes){
            String month = FormatDateUtils.FormatLongTime("MM", routesBean.getTime());
            String day = FormatDateUtils.FormatLongTime("dd",routesBean.getTime());
            month=dealZero(month);
            day=dealZero(day);
            String date=month+"月"+day+"日";
            routesBean.setTime(date);
        }
        return routes;
    }

    /**
     * 分类目的地 根据时间进行天数归类
     * @param lists
     * @param routes
     */
    private void classificationDay(List<List<AppointTogetherDetail.DataBean.RoutesBean>> lists, List<AppointTogetherDetail.DataBean.RoutesBean> routes) {

        try {
            List<AppointTogetherDetail.DataBean.RoutesBean> dayList=new ArrayList<>();
            String preTime=routes.get(0).getTime();
            for (AppointTogetherDetail.DataBean.RoutesBean routesBean: routes){
                if (!routesBean.getTime().equals(preTime)){
                    lists.add(dayList);
                    dayList=new ArrayList<>();
                     preTime=routesBean.getTime();
                }
                dayList.add(routesBean);
            }
            lists.add(dayList);//加入最后一列
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String dealZero(String month) {
        if (month.startsWith("0") && month.length()==2){
            String substring = month.substring(1, month.length());
            return substring;
        }
        return month;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_switch:
                isDetail=!isDetail;
                if (lists==null || lists.size()==0)return;
                mLvRouteLine.setAdapter(new TravelDetailLineAdapter(this, lists,isDetail));
                mTvSitch.setText(isDetail?"详情图":"缩略图");
                break;
            case R.id.tv_enter:
                if (StringUtils.isEmpty(id)){
                    ToastUtils.showToast("数据加载错误，请重新进入！");
                    return;
                }
                Map<String, String> enterMap = MapUtils.Build().addKey(this).addtId(id).addUserId().end();
                XEventUtils.postUseCommonBackJson(IVariable.ENTER_APPOINT,enterMap,TYPE_ENTER_APPOINT,new AppointDetailEvent());

                break;
            case R.id.tv_right_icon:
                AppointDetailMorePop.showMorePop(this,mTvRight);
                break;
        }
    }
}
