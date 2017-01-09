package com.yunspeak.travel.ui.find.travels.travelsdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.TravelReplyBean;
import com.yunspeak.travel.bean.TravelsDetailBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.popwindow.AppointDetailMorePop;
import com.yunspeak.travel.ui.find.findcommon.BaseFindDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DetailCommonEvent;
import com.yunspeak.travel.ui.home.HotSpotsItemDecoration;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/30.
 * 游记详情
 */
public class TravelsDetailActivity extends BaseFindDetailActivity<DetailCommonEvent, TravelsDetailBean> {
    private RecyclerView mRvAddLine;
    private RecyclerView mRvMember;
    private SimpleDraweeView mIvBg;
    private SimpleDraweeView mIvIcon;
    private TextView mTvStartAndLong;
    private TextView mTvTime;
    private TextView mTvDream;
    private TextView mTvHaveNumber;
    private TextView mTvMoney;
    private WebView mWvHtml;

    private boolean isFirstMove = true;//避免第一次margin为0
    private String title;
    private String shareUrl;
    private String url;

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


    public static void start(Context context, String tid, String name) {
        Intent intent = new Intent(context, TravelsDetailActivity.class);
        intent.putExtra(IVariable.T_ID, tid);
        intent.putExtra(IVariable.NAME, name);
        context.startActivity(intent);
    }

    @Override
    protected String initRightText() {
        return "更多";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        if (StringUtils.isEmpty(isCollect)) return;
        String collection = isCollect.equals(isTrue) ? "已收藏" : "收藏";
        //没有举报第三个参数无所谓
        AppointDetailMorePop.showMorePopIsNotCompliant(this, tId, mToolbar, "0", collection, new ParentPopClick() {
            @Override
            public void onClick(int t) {
                String url = isCollect.equals(isTrue) ? IVariable.CANCEL_COMMON_COLLECTION : IVariable.COLLECTION;
                int type = isCollect.equals(isTrue) ? TYPE_CANCEL_COLLECTION : TYPE_COLLECTION;
                Map<String, String> collectionMap = MapUtils.Build().addKey().addUserId().addType("4").addId(tId).end();
                XEventUtils.postUseCommonBackJson(url, collectionMap, type, new DetailCommonEvent());
            }
        }, "城外旅游游记分享", title, false, shareUrl, mIvBg);
    }

    private void init() {
        vsContent.setLayoutResource(R.layout.activity_travels_detail_content);
        vsContent.inflate();
        LinearLayout  mLlRoot = ((LinearLayout) findViewById(R.id.header_root));
        mLlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftWore(v);
            }
        });
        mRvMember = ((RecyclerView) findViewById(R.id.rv_member));
        mRvAddLine = ((RecyclerView) findViewById(R.id.rv_add_line));
        mIvBg = (SimpleDraweeView) findViewById(R.id.iv_bg);
        mIvIcon = (SimpleDraweeView) findViewById(R.id.iv_travel);
        mWvHtml = (WebView) findViewById(R.id.wv_html);
       mWvHtml.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               return true;
           }
       });
        mWvHtml.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        WebSettings settings = mWvHtml.getSettings();
        settings.setLoadWithOverviewMode(true);
        mTvStartAndLong = (TextView) findViewById(R.id.tv_start_and_long);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvDream = (TextView) findViewById(R.id.tv_dream);
        mTvHaveNumber = (TextView) findViewById(R.id.tv_have_number);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        url = getIntent().getStringExtra(IVariable.URL);
        if (!StringUtils.isEmpty(url)){
            ViewCompat.setTransitionName(mIvBg,TRANSIT_IMAGE1);
            FrescoUtils.displayNormal(mIvBg, url, 640, 360, R.drawable.normal_2_1);
      }
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addT_Id(tId);
    }


    @Override
    protected void initHeader(DetailCommonEvent detailCommonEvent) {
        TravelsDetailBean object = GsonUtils.getObject(detailCommonEvent.getResult(), TravelsDetailBean.class);
        TravelsDetailBean.DataBean data = object.getData();
        try {
            title = data.getTravel().getTitle();
            mTvTitle.setText(title);
            if (StringUtils.isEmpty(url)) {
                FrescoUtils.displayNormal(mIvBg, data.getTravel().getLogo_img(), 640, 360, R.drawable.normal_2_1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TravelsDetailBean.DataBean.TravelRoutesBean travelRoutes = data.getTravel_routes();
        FrescoUtils.displayRoundIcon(mIvIcon, travelRoutes.getTravel_img());
        isCollect = data.getTravel().getIs_collect();
        mTvDream.setText(data.getTravel().getTravel_way());
        shareUrl = data.getTravel().getShare_url();
        mTvStartAndLong.setText(travelRoutes.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(travelRoutes.getStart_time(), travelRoutes.getEnd_time()));
        mTvTime.setText("行程日期: " + FormatDateUtils.FormatLongTime("yyyy.MM.dd", travelRoutes.getStart_time())+ "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", travelRoutes.getEnd_time()));
        mTvHaveNumber.setText("已有: " + travelRoutes.getCount());
        mTvMoney.setText(travelRoutes.getTotal_price());
        List<TravelsDetailBean.DataBean.TravelRoutesBean.RoutesBean> routes = travelRoutes.getRoutes();
        mRvAddLine.setAdapter(new TravelsAddAdapter(this, routes));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvAddLine.setLayoutManager(linearLayoutManager);
        mRvAddLine.addItemDecoration(new TravelsAddLineItenDecoration(9));
        LinearLayoutManager memberLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        List<TravelsDetailBean.DataBean.TravelRoutesBean.UserBean> user = travelRoutes.getUser();
        mRvMember.setAdapter(new TravelMemberAdapter(this, user));
        mRvMember.setLayoutManager(memberLayoutManager);
        mRvMember.addItemDecoration(new HotSpotsItemDecoration(DensityUtil.dip2px(12)));
        mWvHtml.loadUrl(data.getTravel().getUrl());

    }

    @Override
    protected List<TravelReplyBean> childChangeData(TravelsDetailBean parentBean, DetailCommonEvent detailCommonEvent) {
        haveNextPage = parentBean.getData().getHave_next().getNextpage();
        return parentBean.getData().getTravel_reply();
    }

    @Override
    protected String initUrl() {
        return IVariable.FIND_TRAVELS_DETAIL;
    }
}
