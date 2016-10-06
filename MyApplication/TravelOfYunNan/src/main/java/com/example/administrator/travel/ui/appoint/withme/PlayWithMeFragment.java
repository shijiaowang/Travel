package com.example.administrator.travel.ui.appoint.withme;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.appoint.together.AppointTogetherBean;
import com.example.administrator.travel.ui.baseui.AppointWithMeDetailActivity;
import com.example.administrator.travel.ui.baseui.LoadAndPullBaseFragment;
import com.example.administrator.travel.ui.me.messagecenter.appointmessage.AppointMessageAdapter;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/21 0021.
 * 带我玩
 */
public class PlayWithMeFragment extends LoadAndPullBaseFragment<AppointWithMeEvent,AppointWithMeBean,AppointWithMeBean.DataBean> implements XListView.IXListViewListener {

    @BindView(R.id.lv_play_with_me)
    XListView mPlayWithMe;

    @Override
    protected int initResLayout() {
        return R.layout.fragment_appoint_play_with_me;
    }

    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public Class<? extends HttpEvent> registerEventType() {
        return AppointWithMeEvent.class;
    }
    @Override
    protected TravelBaseAdapter initAdapter(List<AppointWithMeBean.DataBean> httpData) {
        return new AppointWithMeAdapter(getContext(), httpData);
    }

    @Override
    protected void onFail(AppointWithMeEvent event) {
        super.onFail(event);
        loadEnd(mPlayWithMe);
    }

    @Override
    public XListView setXListView() {
        return mPlayWithMe;
    }

    @Override
    protected void initListener() {
        initXListView(mPlayWithMe, true, true);
        mPlayWithMe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), AppointWithMeDetailActivity.class);
                intent.putExtra(IVariable.TID, getHttpData().get(position - 1).getId());
                startActivity(intent);
            }
        });
    }
    @Override
    protected String initUrl() {
        return IVariable.PLAY_WITHE_ME;
    }

}
