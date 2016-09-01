package com.example.administrator.travel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointWithMe;
import com.example.administrator.travel.event.AppointEvent;
import com.example.administrator.travel.ui.adapter.AppointWithMeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2016/7/21 0021.
 * 带我玩
 */
public class PlayWithMeFragment extends LoadBaseFragment {

    private ListView mPlayWithMe;
    private View inflate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_appoint_play_with_me, null);
    }

    @Override
    protected Fragment registerEvent() {
        return null;
    }

    @Override
    protected View initView() {

        return inflate;
    }


    @Override
    protected void initContentView() {
        mPlayWithMe = (ListView) inflate.findViewById(R.id.lv_play_with_me);
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

    @Override
    protected void onLoad() {

    }
}
