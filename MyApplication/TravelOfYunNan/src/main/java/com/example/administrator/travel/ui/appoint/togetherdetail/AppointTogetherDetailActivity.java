package com.example.administrator.travel.ui.appoint.togetherdetail;

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
import com.example.administrator.travel.bean.PricebasecBean;
import com.example.administrator.travel.event.AppointDetailEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.adapter.ProviderAdapter;
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
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.TypefaceUtis;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
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
public class AppointTogetherDetailActivity extends LoadingBarBaseActivity implements View.OnClickListener {
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

    private List<List<AppointTogetherDetail.DataBean.RoutesBean>> lists;
    private TextView mTvRight;


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
    protected void onLoad() {
        Map<String, String> travelDetailMap = MapUtils.Build().addKey(this).addUserId().add(IVariable.TID,tId).end();
        XEventUtils.getUseCommonBackJson(IVariable.PLAY_TOGETHER_DETAIL, travelDetailMap, 0, new AppointDetailEvent());
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
    @Subscribe
    public void onEvent(AppointDetailEvent event){
        setIsProgress(false);
        lists = new ArrayList<>();
         if (event.isSuccess()){
             dealData(event, lists);
         }else {
             setIsError(true);
             ToastUtils.showToast(event.getMessage());
         }
    }

    /**
     * 填充数据
     * @param event
     * @param lists
     */
    private void dealData(AppointDetailEvent event, List<List<AppointTogetherDetail.DataBean.RoutesBean>> lists) {
        AppointTogetherDetail appointTogetherDetail = GsonUtils.getObject(event.getResult(), AppointTogetherDetail.class);
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
        mTvDayAndNight.setText(data.getMeet_address()+"出发  "+ CalendarUtils.getHowDayHowNight(data.getStar_time(), data.getEnd_time()));
        mTvStartAndLong.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd", data.getStar_time())+"至"+FormatDateUtils.FormatLongTime("yyyy.MM.dd", data.getEnd_time()));
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
     * 分类目的地
     * @param lists
     * @param routes
     */
    private void classificationDay(List<List<AppointTogetherDetail.DataBean.RoutesBean>> lists, List<AppointTogetherDetail.DataBean.RoutesBean> routes) {
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
                    EnterAppointDialog.showDialogSuccess(this);
                break;
            case R.id.tv_right_icon:
                AppointDetailMorePop.showMorePop(this,mTvRight);
                break;
        }
    }
}
