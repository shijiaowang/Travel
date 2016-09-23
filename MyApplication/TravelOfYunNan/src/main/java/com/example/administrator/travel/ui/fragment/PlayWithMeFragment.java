package com.example.administrator.travel.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointWithMe;
import com.example.administrator.travel.event.AppointWithMeEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.AppointWithMeDetailActivity;
import com.example.administrator.travel.ui.adapter.AppointWithMeAdapter;
import com.example.administrator.travel.ui.view.LoadingPage;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/7/21 0021.
 * 带我玩
 */
public class PlayWithMeFragment extends LoadBaseFragment implements XListView.IXListViewListener {

    private XListView mPlayWithMe;
    private View inflate;
    private List<AppointWithMe.DataBean> mDatas = new ArrayList<>();
    private AppointWithMeAdapter appointWithMeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_appoint_play_with_me, null);
    }

    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    protected View initView() {
        return inflate;
    }


    @Override
    protected void initContentView() {
        mPlayWithMe = (XListView) inflate.findViewById(R.id.lv_play_with_me);
        mPlayWithMe.setPullLoadEnable(true);
        mPlayWithMe.setAutoLoadEnable(true);
        mPlayWithMe.setXListViewListener(this);
        mPlayWithMe.setRefreshTime(getTime());
    }


    @Override
    protected void initListener() {
        mPlayWithMe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), AppointWithMeDetailActivity.class);
                intent.putExtra(IVariable.TID, mDatas.get(position-1).getId());
                startActivity(intent);
            }
        });
    }

    private void reqData(int type) {
        int count = type == LOAD_MORE ? mDatas.size() : 0;
        Map<String, String> appointMap = MapUtils.Build().addKey(getContext()).addUserId().addPageSize(10).addCount(count).end();
        XEventUtils.getUseCommonBackJson(IVariable.PLAY_WITHE_ME, appointMap, type, new AppointWithMeEvent());
    }

    @Override
    protected void onLoad() {
        reqData(LOAD_MORE);
    }

    @Subscribe
    public void onEvent(AppointWithMeEvent event) {
        loadEnd(mPlayWithMe);
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
        //通知自定义view去显示正确读取后界面
        afterLoadData();
    }
    private void dealData(AppointWithMeEvent event) {
        AppointWithMe appointWithMe = GsonUtils.getObject(event.getResult(), AppointWithMe.class);
        List<AppointWithMe.DataBean> data = appointWithMe.getData();
        if (appointWithMeAdapter==null){
            mDatas=data;
            appointWithMeAdapter = new AppointWithMeAdapter(getContext(),data);
            mPlayWithMe.setAdapter(appointWithMeAdapter);
        }else if (event.getType()==LOAD_MORE){
            mDatas.addAll(data);
            appointWithMeAdapter.notifyData(mDatas);
        }else {
            mDatas=data;
            appointWithMeAdapter.notifyData(mDatas);
        }


    }

    @Override
    public void onRefresh() {
        reqData(REFRESH);
    }

    @Override
    public void onLoadMore() {
        reqData(LOAD_MORE);
    }
}
