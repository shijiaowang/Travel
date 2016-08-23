package com.example.administrator.travel.ui.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointTogether;
import com.example.administrator.travel.event.AppointEvent;
import com.example.administrator.travel.ui.adapter.AppointTogetherAdapter;
import com.example.administrator.travel.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/21 0021.
 * 一起玩
 */
public class PlayTogetherFragment extends BaseFragment {

    private ListView mLvAppoint;
    private List<AppointTogether> mDatas;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_appoint_play_together;
    }

    @Override
    protected void initView() {
        mLvAppoint = (ListView) root.findViewById(R.id.lv_appoint);
    }

    @Override
    protected void initData() {
        mDatas=new ArrayList<>();
        mDatas.add(new AppointTogether());
        mDatas.add(new AppointTogether());
        mDatas.add(new AppointTogether());
        mLvAppoint.setAdapter(new AppointTogetherAdapter(getContext(),mDatas));
    }

    @Override
    protected void initListener() {
        mLvAppoint.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
               if (scrollState==1) {
                   AppointEvent appointEvent = new AppointEvent();
                   appointEvent.setIsSmooth(true);
                   EventBus.getDefault().post(appointEvent);
               }else {
                   AppointEvent appointEvent = new AppointEvent();
                   appointEvent.setIsSmooth(false);
                   EventBus.getDefault().post(appointEvent);
               }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }
}
