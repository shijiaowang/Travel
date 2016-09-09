package com.example.administrator.travel.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Active;
import com.example.administrator.travel.event.ActiveEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.ActiveAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/25 0025.
 * 活动界面
 */
public class ActiveActivity extends LoadingBarBaseActivity implements XScrollView.IXScrollViewListener {
    private ToShowAllListView mLvActive;//活动列表
    private XScrollView mSsvScroll;
    private ImageView mActiveTop;
    private ActiveAdapter activeAdapter;
    private List<Active.DataBean> actives;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_active;
    }

    @Override
    protected void initEvent() {
        init();
        mLvActive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActiveActivity.this, ActivateDetailActivity.class);
                intent.putExtra(IVariable.A_ID,actives.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onLoad() {
        request(TYPE_LOAD);
    }

    private void request(int type) {
        int count= actives==null || type==TYPE_REFRESH?0:actives.size();//去掉头布局
        Map<String, String> activeMap = MapUtils.Build().addKey(this).addPageSize(10).addCount(count).end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_ACTIVITY, activeMap, type, new ActiveEvent());
    }

    private void init() {
        mSsvScroll = getxScrollView();
        LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_active_content, null);
        if (inflate != null && mSsvScroll != null) {
            mSsvScroll.setPullRefreshEnable(true);
            mSsvScroll.setPullLoadEnable(true);
            mSsvScroll.setIXScrollViewListener(this);
            mSsvScroll.setRefreshTime(getTime());
            mLvActive = (ToShowAllListView) inflate.findViewById(R.id.lv_active);
            mLvActive.setFocusable(false);
            mLvActive.setFocusableInTouchMode(false);
            mSsvScroll.setView(inflate);
        }

    }


    @Override
    protected Activity initViewData() {
        return this;
    }
    @Subscribe
    public void onEvent(ActiveEvent event){
        loadEnd(mSsvScroll);
        setIsProgress(false);
       if (event.isSuccess()){
           dealData(event);
       }else {
           ToastUtils.showToast(event.getMessage());
       }
    }

    private void dealData(ActiveEvent event) {
        Active object = GsonUtils.getObject(event.getResult(), Active.class);
        List<Active.DataBean> data = object.getData();
        if (activeAdapter==null){
            actives = new ArrayList<>();
            actives =data;
            activeAdapter = new ActiveAdapter(this, actives);
            mLvActive.setAdapter(activeAdapter);
        }else if (event.getCode()==TYPE_LOAD){
            actives.addAll(data);
            activeAdapter.notifyData(actives);
        }else {
            actives.clear();
            actives =data;
            activeAdapter.notifyData(actives);
        }
        LogUtils.e(mLvActive.getHeight()+"");

    }

    @Override
    protected String setTitleName() {
        return "活动";
    }

    @Override
    public float getAlpha() {
        return 0f;
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }

    @Override
    protected boolean isXScrollView() {
        return true;
    }

    @Override
    public void onRefresh() {
        request(TYPE_REFRESH);
    }

    @Override
    public void onLoadMore() {
     request(TYPE_LOAD);
    }

}
