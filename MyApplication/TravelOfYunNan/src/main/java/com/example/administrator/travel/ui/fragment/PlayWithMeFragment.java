package com.example.administrator.travel.ui.fragment;

import android.widget.AbsListView;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointWithMe;
import com.example.administrator.travel.event.AppointEvent;
import com.example.administrator.travel.ui.adapter.AppointWithMeAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/21 0021.
 * 带我玩
 */
public class PlayWithMeFragment extends BaseFragment {

    private ListView mPlayWithMe;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_appoint_play_with_me;
    }

    @Override
    protected void initView() {
        mPlayWithMe = (ListView) root.findViewById(R.id.lv_play_with_me);
    }

    @Override
    protected void initData() {
        List<AppointWithMe> lists=new ArrayList<>();
        lists.add(new AppointWithMe());
        lists.add(new AppointWithMe());
        lists.add(new AppointWithMe());
        lists.add(new AppointWithMe());
        lists.add(new AppointWithMe());
        lists.add(new AppointWithMe());
        lists.add(new AppointWithMe());
        lists.add(new AppointWithMe());
        mPlayWithMe.setAdapter(new AppointWithMeAdapter(getContext(),lists));
    }

    @Override
    protected void initListener() {
        mPlayWithMe.setOnScrollListener(new AbsListView.OnScrollListener() {
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
