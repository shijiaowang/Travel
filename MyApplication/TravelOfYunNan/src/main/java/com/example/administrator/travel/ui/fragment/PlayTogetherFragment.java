package com.example.administrator.travel.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointTogether;
import com.example.administrator.travel.event.AppointTogetherEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.AppointTogetherAdapter;
import com.example.administrator.travel.ui.view.LoadingPage;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/21 0021.
 * 一起玩
 */
public class PlayTogetherFragment extends LoadBaseFragment implements XListView.IXListViewListener {

    @BindView(R.id.lv_appoint) XListView mLvAppoint;
    private List<AppointTogether.DataBean> mDatas=new ArrayList<>();
    private View root;
    private AppointTogetherAdapter appointTogetherAdapter;


    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_appoint_play_together, null);
        ButterKnife.bind(this,root);
    }



    @Override
    protected void initListener() {
        mLvAppoint.setPullLoadEnable(true);
        mLvAppoint.setAutoLoadEnable(true);
        mLvAppoint.setXListViewListener(this);
        mLvAppoint.setRefreshTime(getTime());

    }
    @Subscribe
    public void onEvent(AppointTogetherEvent event){
        loadEnd(mLvAppoint);
        if (event.isSuccess()){
            dealData(event);
            setState(LoadingPage.ResultState.STATE_SUCCESS);
        }else if (event.getCode()==IVariable.NO_MORE){
            setState(LoadingPage.ResultState.STATE_SUCCESS);
            ToastUtils.showToast(event.getMessage());
        } else {
            ToastUtils.showToast(event.getMessage());
            setState(LoadingPage.ResultState.STATE_ERROR);
        }

    }

    private void dealData(AppointTogetherEvent event) {
        AppointTogether object = GsonUtils.getObject(event.getResult(), AppointTogether.class);
        List<AppointTogether.DataBean> data = object.getData();
        if (appointTogetherAdapter==null){
            mDatas=data;
            appointTogetherAdapter = new AppointTogetherAdapter(getContext(),data);
            mLvAppoint.setAdapter(appointTogetherAdapter);
        }else if (event.getType()==LOAD_MORE){
            mDatas.addAll(data);
            appointTogetherAdapter.notifyData(mDatas);
        }else {
            mDatas=data;
            appointTogetherAdapter.notifyData(mDatas);
        }


    }

    @Override
    protected void onLoad(int type) {
        int count = type==LOAD_MORE?getListSize(mDatas):0;
        Map<String, String> appointMap = MapUtils.Build().addKey(getContext()).addUserId().addPageSize(10).addCount(count).end();
        XEventUtils.getUseCommonBackJson(IVariable.PLAY_TOGETHER, appointMap,type,new AppointTogetherEvent());
    }

    @Override
    protected View initView() {
        return root;
    }

}
