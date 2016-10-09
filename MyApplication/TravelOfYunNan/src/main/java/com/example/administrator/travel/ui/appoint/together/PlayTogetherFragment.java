package com.example.administrator.travel.ui.appoint.together;


import android.support.v4.app.Fragment;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CommonClickLikeBean;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndPullBaseFragment;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;

import java.util.List;
import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/21 0021.
 * 一起玩
 */
public class PlayTogetherFragment extends LoadAndPullBaseFragment<AppointTogetherEvent, AppointTogetherBean, AppointTogetherBean.DataBean> implements XListView.IXListViewListener {
    @BindView(R.id.lv_appoint) XListView mLvAppoint;
    private AppointTogetherAdapter appointTogetherAdapter;

    @Override
    protected int initResLayout() {
        return R.layout.fragment_appoint_play_together;
    }

    @Override
    protected Fragment registerEvent() {
        return this;
    }
    @Override
    public Class<? extends HttpEvent> registerEventType() {
        return AppointTogetherEvent.class;
    }

    @Override
    public XListView setXListView() {
        return mLvAppoint;
    }
    @Override
    protected TravelBaseAdapter initAdapter(List<AppointTogetherBean.DataBean> httpData) {
        return  appointTogetherAdapter = new AppointTogetherAdapter(getContext(), httpData);
    }
    @Override
    protected String initUrl() {
        return IVariable.PLAY_TOGETHER;
    }

    @Override
    protected void doOtherSuccessData(AppointTogetherEvent appointTogetherEvent) {
        CommonClickLikeBean object = GsonUtils.getObject(appointTogetherEvent.getResult(), CommonClickLikeBean.class);
        int clickPosition = appointTogetherEvent.getClickPosition();
        getmDatas().get(clickPosition).setCount_like(object.getData().getCount_like());
        getmDatas().get(clickPosition).setIs_like("1");
        appointTogetherAdapter.notifyDataSetChanged();
    }
}
