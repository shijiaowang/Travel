package com.example.administrator.travel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.SpaceItemDecoration;
import com.example.administrator.travel.ui.adapter.ChosenAdapter;
import com.example.administrator.travel.ui.adapter.HotSpotsAdapter;
import com.example.administrator.travel.ui.adapter.TravelsAdapter;
import com.example.administrator.travel.ui.view.ToShowAllGridView;

/**
 * Created by Administrator on 2016/7/6 0006.
 * 主页Fragment
 */
public class HomeFragment extends BaseFragment {
    private static final int RECYCLE_VIEW_ITEM_SPACE=24;//子VIEW之间的间距


    private ToShowAllGridView mGvChosen;//精选
    private ChosenAdapter chosenAdapter;
    private RecyclerView mRvHotSpots;
    private ListView mLvTravels;
    private EditText mEdSearch;



    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_home;
    }



    protected void initView() {
        mEdSearch = (EditText) root.findViewById(R.id.ed_search);
        root.findViewById(R.id.tv_focus).requestFocus();//抢夺Ed的焦点,搜索框的
        mGvChosen = (ToShowAllGridView) root.findViewById(R.id.gv_chosen);
        mRvHotSpots = (RecyclerView) root.findViewById(R.id.rv_hot_spots);
        mLvTravels = (ListView) root.findViewById(R.id.lv_travels);


    }
     public boolean isFirst=true;//避免进入主页已经调用onScrolled，造成未滑动边距就已经为0
    protected void initListener() {
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

    protected void initData() {
        chosenAdapter = new ChosenAdapter(getActivity(), null);
        mGvChosen.setAdapter(chosenAdapter);

        mRvHotSpots.setAdapter(new HotSpotsAdapter(getActivity(), null));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRvHotSpots.setLayoutManager(manager);
        mRvHotSpots.setItemAnimator(new DefaultItemAnimator());
        mRvHotSpots.addItemDecoration(new SpaceItemDecoration(RECYCLE_VIEW_ITEM_SPACE));//设置孩子间距为24px;

        mLvTravels.setAdapter(new TravelsAdapter(getActivity(),null));
    }


}
