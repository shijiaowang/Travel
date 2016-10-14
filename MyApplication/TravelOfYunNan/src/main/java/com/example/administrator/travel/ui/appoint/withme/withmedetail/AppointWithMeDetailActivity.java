package com.example.administrator.travel.ui.appoint.withme.withmedetail;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PeopleBean;
import com.example.administrator.travel.ui.appoint.together.togetherdetail.AppointDetailEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.together.togetherdetail.AppointDetailHaveEnterAdapter;
import com.example.administrator.travel.ui.appoint.together.togetherdetail.AppointDetailInsuranceAdapter;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.ImageOptionsUtil;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/6 0006.
 * 带我玩约伴详情
 */
public class AppointWithMeDetailActivity extends BaseNetWorkActivity<AppointDetailEvent> {

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
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.tv_line) TextView mTvLine;
    @BindView(R.id.tv_start_and_long) TextView mTvStartAndLong;
    @BindView(R.id.tv_day_and_night) TextView mTvDayAndNight;
    @BindView(R.id.tv_have_number) TextView mTvHaveNumber;
    @BindView(R.id.tv_have_enter) TextView mTvHaveEnter;//已报名
    @BindView(R.id.rv_have_enter) RecyclerView mRvHaveEnter;
    @BindView(R.id.lv_insurance) ToShowAllListView mLvInsurance;//保险
    @BindView(R.id.fl_title) FlowLayout mFlTitle;
    @BindView(R.id.tv_price) TextView mTvPrice;
    @BindView(R.id.tv_enter_end_time) TextView mTvEnterEndTime;
    @BindView(R.id.tv_surplus_day) TextView mTvSurplusDay;//剩余日期
    private String tId;




    @Override
    protected void initEvent() {
        init();
    }

    private void init() {
        tId = getIntent().getStringExtra(IVariable.TID);
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
       builder.addtId(tId);
    }

    @Override
    protected String initUrl() {
        return IVariable.WITHE_ME_DETAIL;
    }



    @Override
    protected void onSuccess(AppointDetailEvent appointDetailEvent) {
        dealData(appointDetailEvent);
    }


    private void dealData(AppointDetailEvent event) {
        AppointWithMeDetailBean appointWithMeDetail = GsonUtils.getObject(event.getResult(), AppointWithMeDetailBean.class);
        try {
            AppointWithMeDetailBean.DataBean data = appointWithMeDetail.getData();
           mTvDay.setText(FormatDateUtils.FormatLongTime("MM-dd",data.getAdd_time()));
           mTvTime.setText(FormatDateUtils.FormatLongTime("HH:mm",data.getAdd_time()));
            x.image().bind(mIvUserIcon, data.getUser_img(), ImageOptionsUtil.getBySetSize(DensityUtil.dip2px(30), DensityUtil.dip2px(30)));
            mTvUserNickName.setText(data.getUser_name());
            x.image().bind(mIvAppointBg, data.getTravel_img(), ImageOptionsUtil.getBySetSize(DensityUtil.dip2px(116), DensityUtil.dip2px(116)));
            if (mFlTitle.getChildCount()>0)mFlTitle.removeAllViews();
            dealLabel(data);
            mTvLoveNumber.setText(data.getCount_like());
            mTvWatchNumber.setText(data.getBrowse());
            mTvTitle.setText(data.getTitle());
            mTvContent.setText(data.getContent());
            mTvLove.setTextColor(data.getIs_like().equals("1") ? getResources().getColor(R.color.colorFf8076) : getResources().getColor(R.color.colorb5b5b5));
            mTvStartAndLong.setText(data.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(data.getStart_time()+"000", data.getEnd_time()+"000"));
            mTvDayAndNight.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd", data.getStart_time()) + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", data.getEnd_time()));
           // mTvHaveNumber.setText("已有："+data.get()+"人");
            String currentTime=new Date().getTime()+"";
            mTvSurplusDay.setText("剩余："+CalendarUtils.getHowDay(currentTime,data.getEnd_time()+"000")+"天");
            mTvLine.setText(data.getRoutes_title());
            mTvSex.setText(data.getSex().equals("0")?R.string.activity_member_detail_boy:R.string.activity_member_detail_girl);
            mTvPrice.setText("¥"+data.getTotal_price());
            mTvEnterEndTime.setText("招募截止日期："+FormatDateUtils.FormatLongTime("yyyy-MM-dd",data.getEnd_time()));
            List<PricebasecBean> pricebasec = data.getPricebasec();
            if (pricebasec==null){
                pricebasec=new ArrayList<>();
            }
            List<PeopleBean> people = data.getPeople();
            if (people!=null && people.size()!=0){
                mRvHaveEnter.setAdapter(new AppointDetailHaveEnterAdapter(this, people));
                mRvHaveEnter.setLayoutManager(new GridLayoutManager(this, people.size()));
            }
            int count=people==null?0:people.size();
            mTvHaveEnter.setText("已加入("+count+")");
            mTvHaveNumber.setText("已有："+count+"人");
            PricebasecBean pricebasecBean=new PricebasecBean();//添加用户设置的路程费用
            pricebasecBean.setKey("路程费用");
            pricebasecBean.setValue(data.getPrice());
            pricebasec.add(pricebasecBean);
            mLvInsurance.setAdapter(new AppointDetailInsuranceAdapter(this, pricebasec));
            //measureHeight(mLvInsurance);
            List<AppointWithMeDetailBean.DataBean.RoutesBean> routes = data.getRoutes();
            if (routes!=null && routes.size()!=0){
                mLvRouteLine.setAdapter(new AppointWithMeDetailDestinationAdapter(this,routes));
            }
        }catch (Exception e){
            LogUtils.e("找人带详情页发生异常了");
            e.printStackTrace();
        }
    }

    private void dealLabel(AppointWithMeDetailBean.DataBean data) {
        String label = data.getLabel();
        if (StringUtils.isEmpty(label))return;
        String[] split = label.split(",");
        for (int i=0;i<split.length;i++){
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_fragment_appoint_title, mFlTitle, false);
            textView.setText(split[i]);
            mFlTitle.addView(textView);
        }
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_appoint_with_me_detail;
    }

    @Override
    protected String initTitle() {
        return "约伴详情";
    }
}
