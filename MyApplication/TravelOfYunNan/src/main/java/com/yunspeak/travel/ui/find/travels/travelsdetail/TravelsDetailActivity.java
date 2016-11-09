package com.yunspeak.travel.ui.find.travels.travelsdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.find.findcommon.BaseFindDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.TravelReplyBean;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.HotSpotsItemDecoration;
import com.yunspeak.travel.ui.adapter.TravelMemberAdapter;
import com.yunspeak.travel.ui.adapter.TravelsAddAdapter;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/30.
 * 游记详情
 */
public class TravelsDetailActivity extends BaseFindDetailActivity<DetailCommonEvent,TravelsDetailBean> {
    private RecyclerView mRvAddLine;
    private RecyclerView mRvMember;
    private SimpleDraweeView mIvBg;
    private SimpleDraweeView mIvIcon;
    private TextView mTvStartAndLong;
    private TextView mTvTime;
    private TextView mTvDream;
    private String haveNextPage;
    private TextView mTvHaveNumber;
    private TextView mTvMoney;
    private WebView mWvHtml;

    private boolean isFirstMove = true;//避免第一次margin为0

    @Override
    protected String detailType() {
        return IVariable.TYPE_TRAVELS;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        init();
        mRvAddLine.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isFirstMove) {
                    isFirstMove = false;
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
    }

    @Override
    public void onItemClick(int position) {
        super.onItemClick(position);
        Map<String, String> destinationMap = MapUtils.Build().addKey().addFId(mDatas.get(position).getF_id()).addUserId().
                addContent("这只是一个测试评论而已，而已").addPId(mDatas.get(position).getId()).add(IVariable.TYPE, IVariable.TYPE_TRAVELS).
                addNextPage(haveNextPage).addCount(mDatas.size()).
                end();
        XEventUtils.postUseCommonBackJson(IVariable.FIND_REPLY_DISCUSS, destinationMap, TYPE_DISCUSS, new DetailCommonEvent());
    }

    public static void start(Context context, String tid, String name){
        Intent intent=new Intent(context,TravelsDetailActivity.class);
        intent.putExtra(IVariable.T_ID,tid);
        intent.putExtra(IVariable.NAME,name);
        context.startActivity(intent);
    }


    private void init() {
            vsContent.setLayoutResource(R.layout.activity_travels_detail_content);
           vsContent.inflate();
            mRvMember = ((RecyclerView) findViewById(R.id.rv_member));
            mRvAddLine = ((RecyclerView)findViewById(R.id.rv_add_line));
            mIvBg = (SimpleDraweeView) findViewById(R.id.iv_bg);
            mIvIcon = (SimpleDraweeView) findViewById(R.id.iv_travel);
            mWvHtml = (WebView)findViewById(R.id.wv_html);
            WebSettings settings = mWvHtml.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            mTvStartAndLong = (TextView) findViewById(R.id.tv_start_and_long);
            mTvTime = (TextView) findViewById(R.id.tv_time);
            mTvDream = (TextView) findViewById(R.id.tv_dream);
            mTvHaveNumber = (TextView) findViewById(R.id.tv_have_number);
            mTvMoney = (TextView) findViewById(R.id.tv_money);

    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addTId(tId);
    }


    @Override
    protected void initHeader(DetailCommonEvent detailCommonEvent) {
        TravelsDetailBean object = GsonUtils.getObject(detailCommonEvent.getResult(), TravelsDetailBean.class);
        TravelsDetailBean.DataBean data = object.getData();
        try {
            mTvTitle.setText(data.getTravel().getTitle());
            String url = data.getTravel().getTravels_img().split(",")[0];
            FrescoUtils.displayNormal(mIvBg,url);
        }catch (Exception e){
            e.printStackTrace();
        }
        TravelsDetailBean.DataBean.TravelRoutesBean travelRoutes = data.getTravel_routes();
        FrescoUtils.displayRoundIcon(mIvIcon, travelRoutes.getTravel_img());
        mTvDream.setText(data.getTravel().getTravel_way());
        mTvStartAndLong.setText(travelRoutes.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(travelRoutes.getStar_time(), travelRoutes.getEnd_time()));
        mTvTime.setText("行程日期: " + FormatDateUtils.FormatLongTime("yyyy.MM.dd", travelRoutes.getStar_time() + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", travelRoutes.getEnd_time())));
        mTvHaveNumber.setText("已有: " + travelRoutes.getCount());
        mTvMoney.setText(travelRoutes.getTotal_price());
        List<TravelsDetailBean.DataBean.TravelRoutesBean.RoutesBean> routes = travelRoutes.getRoutes();
        mRvAddLine.setAdapter(new TravelsAddAdapter(this, routes));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvAddLine.setLayoutManager(linearLayoutManager);
        mRvAddLine.addItemDecoration(new HotSpotsItemDecoration(DensityUtil.dip2px(9)));
        LinearLayoutManager memberLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        List<TravelsDetailBean.DataBean.TravelRoutesBean.UserBean> user = travelRoutes.getUser();
        mRvMember.setAdapter(new TravelMemberAdapter(this, user));
        mRvMember.setLayoutManager(memberLayoutManager);
        mRvMember.addItemDecoration(new HotSpotsItemDecoration(DensityUtil.dip2px(12)));
        mWvHtml.loadUrl(data.getTravel().getUrl());
    }

    @Override
    protected List<TravelReplyBean> childChangeData(TravelsDetailBean parentBean, DetailCommonEvent detailCommonEvent) {
        haveNextPage=parentBean.getData().getHave_next().getNextpage();
        return parentBean.getData().getTravel_reply();
    }

    @Override
    protected String initUrl() {
        return IVariable.FIND_TRAVELS_DETAIL;
    }
}
