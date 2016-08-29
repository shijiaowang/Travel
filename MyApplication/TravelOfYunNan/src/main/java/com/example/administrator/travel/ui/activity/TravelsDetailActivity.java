package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.DetailCommonEvent;
import com.example.administrator.travel.event.TravelsDetailEvent;
import com.example.administrator.travel.event.TravelsEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.HotSpotsItemDecoration;
import com.example.administrator.travel.ui.adapter.TravelMemberAdapter;
import com.example.administrator.travel.ui.adapter.TravelsAddAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

/**
 * Created by android on 2016/7/30.
 * 游记详情
 */
public class TravelsDetailActivity extends LoadingBarBaseActivity implements XScrollView.IXScrollViewListener {
    private RecyclerView mRvAddLine;
    private RecyclerView mRvMember;
    private ToShowAllListView mLvDiscuss;
    private String tId;
    private XScrollView mSsvScroll;


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
    }

    private void init() {
        tId = getIntent().getStringExtra(IVariable.T_ID);
        mSsvScroll = getxScrollView();
        LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_travels_detail_content, null);
        if (inflate != null && mSsvScroll != null) {
            mSsvScroll.setPullRefreshEnable(false);
            mSsvScroll.setPullLoadEnable(true);
            mSsvScroll.setIXScrollViewListener(this);
            mSsvScroll.setRefreshTime(getTime());
            mRvMember = ((RecyclerView) inflate.findViewById(R.id.rv_member));
            mRvAddLine = ((RecyclerView) inflate.findViewById(R.id.rv_add_line));
            mLvDiscuss = ((ToShowAllListView) inflate.findViewById(R.id.lv_discuss));
            mSsvScroll.setView(inflate);
        }
    }

    @Override
    protected void onLoad() {
        requestData(TYPE_LOAD);
    }

    private void requestData(int type) {
        Map<String, String> detailMap = MapUtils.Build().addKey(this).addPageSize(10).addCount(0).addTId(tId).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_TRAVELS_DETAIL, detailMap, type, new DetailCommonEvent());
    }

    @Override
    protected Activity initViewData() {
        mRvAddLine.setAdapter(new TravelsAddAdapter(this, null));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvAddLine.setLayoutManager(linearLayoutManager);
        mRvAddLine.addItemDecoration(new HotSpotsItemDecoration(18));
        LinearLayoutManager memberLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvMember.setAdapter(new TravelMemberAdapter(this, null));
        mRvMember.setLayoutManager(memberLayoutManager);
        mRvMember.addItemDecoration(new HotSpotsItemDecoration(24));

        return this;
    }

    @Override
    protected String setTitleName() {
        return "游记详情";
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Subscribe
    public void onEvent(DetailCommonEvent event) {
        setIsProgress(false);
        loadEnd(mSsvScroll);
        if (event.isSuccess()){
            dealData(event);
        }else {
            ToastUtils.showToast(event.getMessage());
        }

    }

    private void dealData(DetailCommonEvent event) {
        LogUtils.e(event.getType() + "这是类型");
        switch (event.getType()){
            case TYPE_LOAD:
                dealDestinationDetailData(event);
                break;
            case TYPE_LIKE_DISCUSS:
                //dealClickData(event);
                break;
            case TYPE_DISCUSS:
                //dealReplyData(event);
                break;
        }
    }

    private void dealDestinationDetailData(DetailCommonEvent event) {

    }

    @Override
    protected boolean isXScrollView() {
        return true;
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        requestData(TYPE_LOAD);
    }

}
