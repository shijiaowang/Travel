package com.yunspeak.travel.ui.baseui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CommonClickLikeBean;
import com.yunspeak.travel.bean.FindLastReply;
import com.yunspeak.travel.bean.TravelReplyBean;
import com.yunspeak.travel.bean.TravelsDetail;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.DiscussCommonAdapter;
import com.yunspeak.travel.ui.adapter.HotSpotsItemDecoration;
import com.yunspeak.travel.ui.adapter.TravelMemberAdapter;
import com.yunspeak.travel.ui.adapter.TravelsAddAdapter;
import com.yunspeak.travel.ui.view.ToShowAllListView;
import com.yunspeak.travel.ui.view.refreshview.XScrollView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.ImageOptionsUtil;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/30.
 * 游记详情
 */
public class TravelsDetailActivity extends LoadingBarBaseActivity<DetailCommonEvent>{
    private RecyclerView mRvAddLine;
    private RecyclerView mRvMember;
    private ToShowAllListView mLvDiscuss;
    private String tId;
    private XScrollView mSsvScroll;
    private  boolean isFirstLoad=true;
    private ImageView mIvBg;
    private ImageView mIvIcon;
    private TextView mTvStartAndLong;
    private TextView mTvTime;
    private TextView mTvDream;
    private String haveNextPage;
    private TextView mTvHaveNumber;
    private TextView mTvMoney;
    private DiscussCommonAdapter discussCommonAdapter;
    private List<TravelReplyBean> travelReply=new ArrayList<>();
    private WebView mWvHtml;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_detail;
    }

    private boolean isFirst = true;//避免第一次margin为0

    @Override
    protected void initEvent() {
        init();
        mRvAddLine.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isFirst) {
                    isFirst = false;
                } else {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRvAddLine.getLayoutParams();
                    layoutParams.leftMargin = 0;
                    mRvAddLine.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        mLvDiscuss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> destinationMap = MapUtils.Build().addKey(TravelsDetailActivity.this).addFId(travelReply.get(position).getF_id()).addUserId().
                        addContent("这只是一个测试评论而已，而已").addPId(travelReply.get(position).getId()).add(IVariable.TYPE, IVariable.TYPE_TRAVELS).
                        addNextPage(haveNextPage).addCount(travelReply.size()).
                        end();
                XEventUtils.postUseCommonBackJson(IVariable.FIND_REPLY_DISCUSS, destinationMap, TYPE_DISCUSS, new DetailCommonEvent());
            }
        });
    }
    public static void start(Context context, String tid){
        Intent intent=new Intent(context,TravelsDetailActivity.class);
        intent.putExtra(IVariable.T_ID,tid);
        context.startActivity(intent);
    }
    private void init() {
        tId = getIntent().getStringExtra(IVariable.T_ID);
        mSsvScroll = getxScrollView();
        LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_travels_detail_content, null);
        if (inflate != null && mSsvScroll != null) {
            initXScrollView(false,true);
            mRvMember = ((RecyclerView) inflate.findViewById(R.id.rv_member));
            mRvAddLine = ((RecyclerView) inflate.findViewById(R.id.rv_add_line));
            mLvDiscuss = ((ToShowAllListView) inflate.findViewById(R.id.lv_discuss));
            mIvBg = (ImageView) inflate.findViewById(R.id.iv_bg);
            mIvIcon = (ImageView) inflate.findViewById(R.id.iv_icon);
            mWvHtml = (WebView) inflate.findViewById(R.id.wv_html);
            WebSettings settings = mWvHtml.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            mTvStartAndLong = (TextView) inflate.findViewById(R.id.tv_start_and_long);
            mTvTime = (TextView) inflate.findViewById(R.id.tv_time);
            mTvDream = (TextView) inflate.findViewById(R.id.tv_dream);
            mTvHaveNumber = (TextView) inflate.findViewById(R.id.tv_have_number);
            mTvMoney = (TextView) inflate.findViewById(R.id.tv_money);
            mSsvScroll.setView(inflate);
        }
    }

    @Override
    protected void onLoad(int typeRefresh) {
        int count=travelReply==null?0:travelReply.size();
        Map<String, String> detailMap = MapUtils.Build().addKey(this).addPageSize(10).addCount(count).addTId(tId).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_TRAVELS_DETAIL, detailMap, typeRefresh, new DetailCommonEvent());
    }

    @Override
    protected Activity initViewData() {

        return this;
    }

    @Override
    protected String setTitleName() {
        return "游记详情";
    }

    @Override
    protected void onSuccess(DetailCommonEvent detailCommonEvent) {
        dealData(detailCommonEvent);
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    private void dealData(DetailCommonEvent event) {
        LogUtils.e(event.getType() + "这是类型");
        switch (event.getType()){
            case TYPE_LOAD:
                dealDestinationDetailData(event);
                break;
            case TYPE_LIKE_DISCUSS:
                dealClickData(event);
                break;
            case TYPE_DISCUSS:
                dealReplyData(event);
                break;
        }
    }
    /**
     * 评论回复
     *
     * @param event
     */
    private void dealReplyData(DetailCommonEvent event) {
        if (haveNextPage.equals("0")){
            //如果没有更多数据则会返回评论的数据
            addNewData(event);
        }
    }

    /**
     * 添加新返回的评论数据
     * @param event
     */
    private void addNewData(DetailCommonEvent event) {
        try {
            FindLastReply findLastReply = GsonUtils.getObject(event.getResult(), FindLastReply.class);
            //数据强转
            travelReply.addAll(findLastReply.getData());
            discussCommonAdapter.notifyData(travelReply);
        }catch (Exception e){
            LogUtils.e("目的地详情评论返回数据时出现异常");
            e.printStackTrace();
        }
    }
    private void dealDestinationDetailData(DetailCommonEvent event) {
        TravelsDetail object = GsonUtils.getObject(event.getResult(), TravelsDetail.class);
        TravelsDetail.DataBean data = object.getData();
        haveNextPage = object.getData().getHave_next().getNextpage();
        if (isFirstLoad){
            isFirstLoad=false;
           try {
               String url = data.getTravel().getTravels_img().split(",")[0];
               x.image().bind(mIvBg, url, ImageOptionsUtil.getBySetSize(DensityUtil.getScreenWidth(), DensityUtil.dip2px(225)));
           }catch (Exception e){
               e.printStackTrace();
           }

            TravelsDetail.DataBean.TravelRoutesBean travelRoutes = data.getTravel_routes();
            x.image().bind(mIvIcon, travelRoutes.getTravel_img(), ImageOptionsUtil.getBySetSize(DensityUtil.dip2px(53), DensityUtil.dip2px(53)));
            mTvDream.setText(data.getTravel().getTravel_way());
            mTvStartAndLong.setText(travelRoutes.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(travelRoutes.getStar_time(), travelRoutes.getEnd_time()));
            mTvTime.setText("行程日期: " + FormatDateUtils.FormatLongTime("yyyy.MM.dd", travelRoutes.getStar_time() + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", travelRoutes.getEnd_time())));
            mTvHaveNumber.setText("已有: " + travelRoutes.getCount());
            mTvMoney.setText(travelRoutes.getTotal_price());
            List<TravelsDetail.DataBean.TravelRoutesBean.RoutesBean> routes = travelRoutes.getRoutes();
            mRvAddLine.setAdapter(new TravelsAddAdapter(this, routes));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mRvAddLine.setLayoutManager(linearLayoutManager);
            mRvAddLine.addItemDecoration(new HotSpotsItemDecoration(DensityUtil.dip2px(9)));
            LinearLayoutManager memberLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            List<TravelsDetail.DataBean.TravelRoutesBean.UserBean> user = travelRoutes.getUser();
            mRvMember.setAdapter(new TravelMemberAdapter(this, user));
            mRvMember.setLayoutManager(memberLayoutManager);
            mRvMember.addItemDecoration(new HotSpotsItemDecoration(DensityUtil.dip2px(12)));
            mWvHtml.loadUrl(data.getTravel().getUrl());
        }
        List<TravelReplyBean> travelData = data.getTravel_reply();
        if (travelData==null)return;
        if (discussCommonAdapter == null) {
            travelReply = travelData;
            discussCommonAdapter = new DiscussCommonAdapter(this, travelReply, IVariable.TYPE_TRAVELS);
            mLvDiscuss.setAdapter(discussCommonAdapter);
        } else {
            travelReply.addAll(travelData);
            discussCommonAdapter.notifyData(travelReply);
        }
    }

    @Override
    protected boolean isXScrollView() {
        return true;
    }



    /**
     * 点击信息
     *
     * @param event
     */
    private void dealClickData(DetailCommonEvent event) {
        CommonClickLikeBean commonClickLikeBean = GsonUtils.getObject(event.getResult(), CommonClickLikeBean.class);
        if (commonClickLikeBean == null) return;
        TravelReplyBean travelReplyBean = travelReply.get(event.getClickPosition());
        travelReplyBean.setIs_like("1");
        travelReplyBean.setLike_count(commonClickLikeBean.getData().getCount_like());
        //// TODO: 2016/8/25 0025 将来设置单独更新一条item
        discussCommonAdapter.notifyData(travelReply);
    }


}
