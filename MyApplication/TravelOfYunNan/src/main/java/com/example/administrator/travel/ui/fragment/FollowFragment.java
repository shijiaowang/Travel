package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.ui.adapter.FanAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class FollowFragment extends BaseFragment {

    private ListView mLvFollowFan;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_follow_and_fan;
    }

    @Override
    protected void initView() {
        mLvFollowFan = (ListView) root.findViewById(R.id.lv_follow_fan);

    }

    @Override
    protected void initData() {
        mLvFollowFan.setAdapter(new FanAdapter(getContext(), null));
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }
    @Subscribe
    public void onEvent(HttpEvent event){

    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
