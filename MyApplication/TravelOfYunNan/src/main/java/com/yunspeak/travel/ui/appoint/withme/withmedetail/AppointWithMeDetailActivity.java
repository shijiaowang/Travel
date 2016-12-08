package com.yunspeak.travel.ui.appoint.withme.withmedetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.easeui.EaseConstant;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.PeopleBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.AppointDetailHaveEnterAdapter;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.AppointDetailInsuranceAdapter;
import com.yunspeak.travel.ui.appoint.travelplan.TravelsPlanActivity;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.myappoint.MyAppointActivity;
import com.yunspeak.travel.ui.me.myappoint.chat.ChatActivity;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.ui.view.ToShowAllListView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/6 0006.
 * 带我玩约伴详情
 */
public class AppointWithMeDetailActivity extends BaseNetWorkActivity<AppointWithMeDetailEvent> {

    @BindView(R.id.lv_route_line) ToShowAllListView mLvRouteLine;
    @BindView(R.id.iv_user_icon)
    SimpleDraweeView mIvUserIcon;
    @BindView(R.id.tv_user_nick_name) TextView mTvUserNickName;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_day) TextView mTvDay;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;
    @BindView(R.id.tv_love) FontsIconTextView mTvLove;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_sex) TextView mTvSex;
    @BindView(R.id.iv_appoint_bg) SimpleDraweeView mIvAppointBg;
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
    @BindView(R.id.bt_enter)
    AvoidFastButton btEnter;
    @BindView(R.id.bt_chat)
    AvoidFastButton mBtChat;
    @BindString(R.string.activity_circle_love_empty) String emptyLove;
    @BindString(R.string.activity_circle_love_full) String fullLove;
    private String tId;
    private String userId;
    int color1= Color.parseColor("#ff7f6c");
    int color2=Color.parseColor("#5cd0c2");
    int color3=Color.parseColor("#74b8ff");
    int color4=Color.parseColor("#fcae04");
    int color5=Color.parseColor("#9f8fe4");
    int color6=Color.parseColor("#50c3eb");
    int color7=Color.parseColor("#5ee5c5");
    private int [] colors=new int[]{color1,color2,color3,color4,color5,color6,color7};
    private int [] titleBgs=new int[]{R.drawable.fragment_appoint_title1_bg,R.drawable.fragment_appoint_title2_bg,R.drawable.fragment_appoint_title3_bg,R.drawable.fragment_appoint_title4_bg,R.drawable.fragment_appoint_title5_bg,R.drawable.fragment_appoint_title6_bg,R.drawable.fragment_appoint_title7_bg,};
    private int action;

    @Override
    protected void initEvent() {
        init();
        mIvUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserCenterActivity.start(AppointWithMeDetailActivity.this,mIvUserIcon,userId);
            }
        });
        btEnter.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId.equals(GlobalUtils.getUserInfo().getId())){
                    MyAppointActivity.start(AppointWithMeDetailActivity.this,MyAppointActivity.WITH_ME);
                    return;
                }
                Map<String, String> appointMap = MapUtils.Build().addKey().addUserId().end();
                XEventUtils.getUseCommonBackJson(IVariable.MY_CREATE_APPOINT,appointMap,TYPE_OTHER,new AppointWithMeDetailEvent());
            }
        });
        mBtChat.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(userId)){
                    ToastUtils.showToast("联系人信息出问题啦！");
                }else {
                    if (userId.equals(GlobalUtils.getUserInfo().getId())){
                        ToastUtils.showToast("无法与自己聊天！");
                    }else {
                        ChatActivity.start(AppointWithMeDetailActivity.this,userId, EaseConstant.CHATTYPE_SINGLE);
                    }
                }
            }
        });

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

  public static void start(Context context,String tid){
      Intent intent=new Intent(context,AppointWithMeDetailActivity.class);
      intent.putExtra(IVariable.TID,tid);
      context.startActivity(intent);
  }

    @Override
    protected void onSuccess(AppointWithMeDetailEvent appointDetailEvent) {
        switch (appointDetailEvent.getType()){
            case TYPE_UPDATE://推送约伴
                ToastUtils.showToast("推送成功！请耐心等待用户确认。");
                break;
            case TYPE_OTHER:
                if (appointDetailEvent.getCode()==2){
                    onFail(appointDetailEvent);
                }else {
                    MyCreateAppointBean myCreateAppointBean = GsonUtils.getObject(appointDetailEvent.getResult(), MyCreateAppointBean.class);
                    final List<MyCreateAppointBean.DataBean> data = myCreateAppointBean.getData();
                    EnterAppointDialog.showMyAppoint(this,data, new ParentPopClick() {
                        @Override
                        public void onClick(int position) {
                            MyCreateAppointBean.DataBean dataBean = data.get(position);
                            Map<String, String> endMap = MapUtils.Build().addKey().addUserId().addtId(tId).addTpId(dataBean.getId()).end();
                            XEventUtils.postUseCommonBackJson(IVariable.PUSH_MY_APPOINT,endMap,TYPE_UPDATE,new AppointWithMeDetailEvent());
                        }
                    });
                }
                break;
            default:
                dealData(appointDetailEvent);
                break;
        }

    }

    @Override
    protected void onFail(AppointWithMeDetailEvent appointWithMeDetailEvent) {
        switch (appointWithMeDetailEvent.getType()){
            case TYPE_UPDATE:
                ToastUtils.showToast(appointWithMeDetailEvent.getMessage());
                break;
            case TYPE_OTHER:
                EnterAppointDialog.showCommonDialog(this,"报名失败", "去创建", "您还没有创建任何约伴[一起玩]线路,请先创建自己的队伍!", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        Intent intent=new Intent(AppointWithMeDetailActivity.this,TravelsPlanActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            default:
                super.onFail(appointWithMeDetailEvent);
                break;
        }

    }

    private void dealData(AppointWithMeDetailEvent event) {
        AppointWithMeDetailBean appointWithMeDetail = GsonUtils.getObject(event.getResult(), AppointWithMeDetailBean.class);
        try {
            AppointWithMeDetailBean.DataBean data = appointWithMeDetail.getData();
           mTvDay.setText(FormatDateUtils.FormatLongTime("MM-dd",data.getAdd_time()));
           mTvTime.setText(FormatDateUtils.FormatLongTime("HH:mm",data.getAdd_time()));
            FrescoUtils.displayIcon(mIvUserIcon, data.getUser_img());
            userId = data.getUser_id();
            btEnter.setText("我的约伴");
            mTvUserNickName.setText(data.getUser_name());
            FrescoUtils.displayRoundIcon(mIvAppointBg, data.getTravel_img());
            if (mFlTitle.getChildCount()>0)mFlTitle.removeAllViews();
            dealLabel(data);
            mTvLoveNumber.setText(data.getCount_like());
            mTvWatchNumber.setText(data.getBrowse());
            mTvTitle.setText(data.getTitle());
            mTvContent.setText(data.getContent());
            action = data.getAction();
            if (action!=1){
                btEnter.setText("已报名");
            }
            mTvLove.setText(data.getIs_like().equals("1")?fullLove:emptyLove);
            mTvLove.setTextColor(data.getIs_like().equals("1") ? getResources().getColor(R.color.colorFf8076) : getResources().getColor(R.color.colorb5b5b5));
            mTvStartAndLong.setText(data.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(data.getStart_time()+"000", data.getEnd_time()+"000"));
            mTvDayAndNight.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd", data.getStart_time()) + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", data.getEnd_time()));
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
            textView.setBackgroundResource(titleBgs[i%titleBgs.length]);
            textView.setTextColor(colors[i%colors.length]);
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
