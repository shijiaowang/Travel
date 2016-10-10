package com.example.administrator.travel.ui.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import android.widget.TextView;
import butterknife.BindView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.ui.baseui.HomeSearchActivity;
import com.example.administrator.travel.ui.adapter.HotSpotsItemDecoration;
import com.example.administrator.travel.ui.adapter.ChosenAdapter;
import com.example.administrator.travel.ui.adapter.HotSpotsAdapter;
import com.example.administrator.travel.ui.adapter.TravelsAdapter;
import com.example.administrator.travel.ui.adapter.fragment.CommonPagerAdapter;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.fragment.homefragment.HomeActiveFragment;
import com.example.administrator.travel.ui.view.LoadingPage;
import com.example.administrator.travel.ui.view.ToShowAllGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 主页Fragment
 */
public class HomeFragment extends LoadBaseFragment<HomeEvent> implements View.OnClickListener {
    private static final int RECYCLE_VIEW_ITEM_SPACE=24;//子VIEW之间的间距
    public boolean isFirst=true;//避免进入主页已经调用onScrolled，造成未滑动边距就已经为0
    @BindView(R.id.gv_chosen) ToShowAllGridView mGvChosen;//精选
     ChosenAdapter chosenAdapter;
    @BindView(R.id.rv_hot_spots) RecyclerView mRvHotSpots;
    @BindView(R.id.lv_travels) ListView mLvTravels;
    @BindView(R.id.vp_active) ViewPager mVpActive;
    @BindView(R.id.rl_search) RelativeLayout mRlSearch;
    @BindView(R.id.tv_focus)
    TextView mTvFocus;







    @Override
    protected int initResLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public Class<? extends HttpEvent> registerEventType() {
        return HomeEvent.class;
    }

    @Override
    public void onSuccess(HomeEvent event) {

    }

    protected void initListener() {
        mTvFocus.requestFocus();
        mRlSearch.setOnClickListener(this);
        mRvHotSpots.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isFirst) {
                    isFirst = false;
                }else {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRvHotSpots.getLayoutParams();
                    layoutParams.leftMargin = 0;
                    mRvHotSpots.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    protected void onLoad(int type) {
        setState(LoadingPage.ResultState.STATE_SUCCESS);
        iniData();
    }

    protected void iniData() {
        chosenAdapter = new ChosenAdapter(getActivity(), null);
        mGvChosen.setAdapter(chosenAdapter);

        mRvHotSpots.setAdapter(new HotSpotsAdapter(getActivity(), null));
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRvHotSpots.setLayoutManager(manager);
        mRvHotSpots.setItemAnimator(new DefaultItemAnimator());
        mRvHotSpots.addItemDecoration(new HotSpotsItemDecoration(RECYCLE_VIEW_ITEM_SPACE));//设置孩子间距为24px;

        mLvTravels.setAdapter(new TravelsAdapter(getActivity(), null));
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new HomeActiveFragment());
        fragments.add(new HomeActiveFragment());
        fragments.add(new HomeActiveFragment());
        mVpActive.setAdapter(new CommonPagerAdapter(getChildFragmentManager(),fragments));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_search:
                startActivity(new Intent(getContext(), HomeSearchActivity.class));
                break;
        }
    }
}
