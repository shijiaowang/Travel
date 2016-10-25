package com.yunspeak.travel.ui.appoint.together.togetherdetail;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.PeopleBean;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.popwindow.AppointDetailMorePop;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.PricebasecBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.adapter.TravelDetailLineAdapter;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.ui.view.ToShowAllListView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.ImageOptionsUtil;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by wangyang on 2016/9/2 0002.
 * 一起玩约伴详情
 */
public class AppointTogetherDetailActivity extends BaseNetWorkActivity<AppointDetailEvent> implements View.OnClickListener {
    private static final int TYPE_ENTER_APPOINT = 95;
    private static final  int TYPE_OUT_APPOINT=59;
    @BindView(R.id.tv_start_add) TextView mTvStartAdd;
    @BindView(R.id.tv_end_add) TextView mTvEndAdd;
    @BindView(R.id.lv_route_line) ToShowAllListView mLvRouteLine;
    @BindView(R.id.iv_user_icon) ImageView mIvUserIcon;
    @BindView(R.id.tv_user_nick_name) TextView mTvUserNickName;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_day) TextView mTvDay;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;
    @BindView(R.id.tv_love) FontsIconTextView mTvLove;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_sex) TextView mTvSex;
    @BindView(R.id.iv_appoint_bg) ImageView mIvAppointBg;
    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_line)
    TextView mTvLine;
    @BindView(R.id.tv_start_and_long)
    TextView mTvStartAndLong;
    @BindView(R.id.tv_day_and_night)
    TextView mTvDayAndNight;
    @BindView(R.id.tv_plan_number)
    TextView mTvPlanNumber;
    @BindView(R.id.tv_have_number)
    TextView mTvHaveNumber;
    @BindView(R.id.tv_have_enter)
    TextView mTvHaveEnter;//已报名
    @BindView(R.id.tv_entering)
    TextView mTvEntering;//报名中
    @BindView(R.id.tv_key)
    TextView mTvKey;
    @BindView(R.id.tv_remark)
    TextView mTvRemark;
    @BindView(R.id.tv_traffic)
    TextView mTvTraffic;
    @BindView(R.id.tv_plan_people)
    TextView mTvPlanPeople;
    @BindView(R.id.tv_switch)
    TextView mTvSitch;
    @BindView(R.id.rv_have_enter)
    RecyclerView mRvHaveEnter;
    @BindView(R.id.rv_entering)
    RecyclerView mRvEnter;
    @BindView(R.id.lv_equ_provider)
    ToShowAllListView mLvEquProvider;
    @BindView(R.id.lv_insurance)
    ToShowAllListView mLvInsurance;//保险
    @BindView(R.id.fl_title)
    FlowLayout mFlTitle;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.bt_enter)
    Button mBvEnter;

    private boolean isDetail = false;//默认缩略图
    private String tId;
    private List<List<AppointTogetherDetailBean.DataBean.RoutesBean>> lists = new ArrayList<>();
    private String id;
    private String payType = "-1";
    private int payStatus=-1;
    private String isCollect;


    @Override
    protected void initEvent() {
        ShareSDK.initSDK(this,"18450bb6d1b67");
        tId = getIntent().getStringExtra(IVariable.T_ID);
        mTvSitch.setOnClickListener(this);
        mBvEnter.setOnClickListener(this);
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.addtId(tId);
    }

    @Override
    protected String initUrl() {
        return IVariable.PLAY_TOGETHER_DETAIL;
    }


    @Override
    protected void onSuccess(AppointDetailEvent appointDetailEvent) {
        switch (appointDetailEvent.getType()) {
            case TYPE_REFRESH:
                dealData(appointDetailEvent);
                break;
            case TYPE_ENTER_APPOINT:
                EnterAppointDialog.showDialogSuccess(this);
                initAction(7+"");
                break;
            case TYPE_DELETE:
               ToastUtils.showToast("取消成功");
                initAction(1+"");
                break;
            case TYPE_OUT_APPOINT:
                ToastUtils.showToast("申请成功");
                initAction(6+"");
                break;
            case TYPE_COLLECTION:
                ToastUtils.showToast("收藏成功");
                isCollect=isTrue;

                break;
            case TYPE_CANCEL_COLLECTION:
                ToastUtils.showToast("取消收藏成功");
                isCollect=isFalse;
                break;
        }

    }

    @Override
    protected void onFail(AppointDetailEvent appointDetailEvent) {
        super.onFail(appointDetailEvent);
        if (appointDetailEvent.getType() == TYPE_ENTER_APPOINT) {
            // ToastUtils.showToast(appointDetailEvent.getMessage());
        }
    }

    /**
     * 填充数据
     *
     * @param event
     */
    private void dealData(AppointDetailEvent event) {
        AppointTogetherDetailBean appointTogetherDetail = GsonUtils.getObject(event.getResult(), AppointTogetherDetailBean.class);
        if (appointTogetherDetail == null) return;
        AppointTogetherDetailBean.DataBean data = appointTogetherDetail.getData();
        String action = data.getAction();
        isCollect = data.getIs_collect();
        initAction(action);
        List<AppointTogetherDetailBean.DataBean.RoutesBean> routes = dealDate(data);
        initSomeData(data);
        classificationDay(lists, routes);
        mLvRouteLine.setAdapter(new TravelDetailLineAdapter(this, lists, isDetail));
        List<PeopleBean> ingPeople = data.getIng_people();
        if (ingPeople != null && ingPeople.size() != 0) {
            mRvEnter.setAdapter(new AppointDetailHaveEnterAdapter(this, ingPeople));
            mRvEnter.setLayoutManager(new GridLayoutManager(this, ingPeople.size()));
        }
        List<PeopleBean> intoPeople = data.getInto_people();
        if (intoPeople != null && intoPeople.size() != 0) {
            mRvHaveEnter.setAdapter(new AppointDetailHaveEnterAdapter(this, intoPeople));
            mRvHaveEnter.setLayoutManager(new GridLayoutManager(this, intoPeople.size()));
        }
        List<AppointTogetherDetailBean.DataBean.PropBean> prop = data.getProp();
        if (prop != null && prop.size() != 0) {
            mLvEquProvider.setAdapter(new ProviderAdapter(this, prop));
            //measureHeight(mLvEquProvider);
        }
        List<PricebasecBean> pricebasec = data.getPricebasec();
        if (pricebasec == null) {
            pricebasec = new ArrayList<>();
        }
        PricebasecBean pricebasecBean = new PricebasecBean();
        pricebasecBean.setKey("路程费用");
        pricebasecBean.setValue(data.getPrice());
        pricebasec.add(pricebasecBean);
        mLvInsurance.setAdapter(new AppointDetailInsuranceAdapter(this, pricebasec));
        //measureHeight(mLvInsurance);
    }

    /**
     * 判断状态
     *
     * @param action
     */
    private void initAction(String action) {
        try {
            String[] stringArray = getResources().getStringArray(R.array.together_appoint_detail);
            int i = Integer.parseInt(action);
            payStatus = i;
            payType = stringArray[i];
            mBvEnter.setText(payType);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 填充一些数据
     *
     * @param data
     */
    private void initSomeData(AppointTogetherDetailBean.DataBean data) {
        id = data.getId();
        mTvLove.setTextColor(data.getIs_like().equals("1") ? getResources().getColor(R.color.colorFf8076) : getResources().getColor(R.color.colorb5b5b5));
        mTvLove.setText(data.getIs_like().equals("1") ? getString(R.string.activity_circle_love_full) : getString(R.string.activity_circle_love_empty));
        x.image().bind(mIvAppointBg, data.getTravel_img(), ImageOptionsUtil.getBySetSize(DensityUtil.dip2px(116), DensityUtil.dip2px(116)));
        x.image().bind(mIvUserIcon, data.getUser_img(), ImageOptionsUtil.getBySetSize(DensityUtil.dip2px(30), DensityUtil.dip2px(30)));
        if (mFlTitle.getChildCount() > 0) mFlTitle.removeAllViews();
        dealLabel(data);
        mTvUserNickName.setText(data.getUser_name());
        mTvLoveNumber.setText(data.getCount_like());
        mTvWatchNumber.setText(data.getBrowse());
        mTvTitle.setText(data.getTitle());
        mTvContent.setText(data.getContent());
        mTvDay.setText(FormatDateUtils.FormatLongTime("MM-dd", data.getAdd_time()));
        mTvTime.setText(FormatDateUtils.FormatLongTime("hh:mm", data.getAdd_time()));
        mTvStartAndLong.setText(data.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(data.getStart_time() + "000", data.getEnd_time() + "000"));
        mTvDayAndNight.setText(FormatDateUtils.FormatLongTime(IVariable.YMD, data.getStart_time()) + "至" + FormatDateUtils.FormatLongTime(IVariable.YMD, data.getEnd_time()));
        mTvHaveNumber.setText("已有：" + data.getNow_people() + "人");
        mTvPlanNumber.setText("计划：" + data.getMax_people() + "人");
        mTvLine.setText(data.getRoutes_title());
        mTvSex.setText(data.getSex().equals("0") ? R.string.activity_member_detail_boy : R.string.activity_member_detail_girl);
        mTvPlanPeople.setText(data.getMin_people() + "-" + data.getMax_people() + "人");
        mTvTraffic.setText(data.getTraffic());
        mTvPrice.setText("¥" + data.getTotal_price());
        mTvRemark.setText(data.getTraffic_text());
        mTvStartAdd.setText(data.getMeet_address());
        mTvEndAdd.setText(data.getOver_address());
        mTvKey.setText(data.getId_code());
        int intoCount = data.getInto_people() == null ? 0 : data.getInto_people().size();
        mTvHaveEnter.setText("已报名(" + intoCount + ")");
        int ingCount = data.getIng_people() == null ? 0 : data.getIng_people().size();
        mTvEntering.setText("报名中(" + ingCount + ")");
    }

    /**
     * 处理标签
     * @param data
     */
    private void dealLabel(AppointTogetherDetailBean.DataBean data) {
        try {
            String label = data.getLabel();
            if (StringUtils.isEmpty(label))return;
            String[] split = label.split(",");
            for (int i = 0; i < split.length; i++) {
                TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_fragment_appoint_title, mFlTitle, false);
                textView.setText(split[i]);
                mFlTitle.addView(textView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理时间
     *
     * @return
     */
    @NonNull
    private List<AppointTogetherDetailBean.DataBean.RoutesBean> dealDate(AppointTogetherDetailBean.DataBean dataBean) {
        List<AppointTogetherDetailBean.DataBean.RoutesBean> routes = dataBean.getRoutes();
        for (AppointTogetherDetailBean.DataBean.RoutesBean routesBean : routes) {
            String month = FormatDateUtils.FormatLongTime("MM", routesBean.getTime());
            String day = FormatDateUtils.FormatLongTime("dd", routesBean.getTime());
            month = dealZero(month);
            day = dealZero(day);
            String date = month + "月" + day + "日";
            routesBean.setTime(date);
        }
        return routes;
    }

    /**
     * 分类目的地 根据时间进行天数归类
     *
     * @param lists
     * @param routes
     */
    private void classificationDay(List<List<AppointTogetherDetailBean.DataBean.RoutesBean>> lists, List<AppointTogetherDetailBean.DataBean.RoutesBean> routes) {

        try {
            List<AppointTogetherDetailBean.DataBean.RoutesBean> dayList = new ArrayList<>();
            String preTime = routes.get(0).getTime();
            for (AppointTogetherDetailBean.DataBean.RoutesBean routesBean : routes) {
                if (!routesBean.getTime().equals(preTime)) {
                    lists.add(dayList);
                    dayList = new ArrayList<>();
                    preTime = routesBean.getTime();
                }
                dayList.add(routesBean);
            }
            lists.add(dayList);//加入最后一列
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 去掉月份0开头
     *
     * @param month
     * @return
     */
    private String dealZero(String month) {
        if (month.startsWith("0") && month.length() == 2) {
            return month.substring(1, month.length());
        }
        return month;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_switch:
                isDetail = !isDetail;
                if (lists == null || lists.size() == 0) return;
                mLvRouteLine.setAdapter(new TravelDetailLineAdapter(this, lists, isDetail));
                mTvSitch.setText(isDetail ? "详情图" : "缩略图");
                break;
            case R.id.bt_enter:
                if (StringUtils.isEmpty(id) || payStatus==-1) {
                    ToastUtils.showToast("数据加载错误，请重新进入！");
                    return;
                }
                clickType();
                break;

        }
    }

    private void clickType() {
        final Map<String, String> map = MapUtils.Build().addKey(this).addtId(id).addUserId().end();
        switch (payStatus) {
            case 1:
                XEventUtils.postUseCommonBackJson(IVariable.ENTER_APPOINT, map, TYPE_ENTER_APPOINT, new AppointDetailEvent());
                break;
            case 3:
                EnterAppointDialog.showCommonDialog(this, "退出约伴", "确定", "退出约伴团队", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        XEventUtils.postUseCommonBackJson(IVariable.OUT_APPOINT,map,TYPE_OUT_APPOINT,new AppointDetailEvent());
                    }
                });

            case 7:
                EnterAppointDialog.showCommonDialog(this, "取消申请", "确定", "取消发起的约伴请求！", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        XEventUtils.postUseCommonBackJson(IVariable.CANCEL_APPOINT,map,TYPE_DELETE,new AppointDetailEvent());
                    }
                });

                break;
            default:
                ToastUtils.showToast(payType);
                break;
        }
    }

    @Override
    protected String initRightText() {
        return "更多";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        String collection=isCollect.equals(isTrue)?"已收藏":"收藏";
        AppointDetailMorePop.showMorePop(this, mToolbar,collection, new ParentPopClick() {
            @Override
            public void onClick(int type) {
                  String url=isCollect.equals(isTrue)?IVariable.CANCEL_COMMON_COLLECTION:IVariable.COLLECTION;
                   Map<String, String> collectionMap = MapUtils.Build().addKey(AppointTogetherDetailActivity.this).addUserId().addType("1").addId(id).end();
                   XEventUtils.postUseCommonBackJson(url, collectionMap, TYPE_COLLECTION, new AppointDetailEvent());

            }
        });
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_appoint_together_detail;
    }

    @Override
    protected String initTitle() {
        return "约伴详情";
    }
}