package com.yunspeak.travel.ui.appoint.withme;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.AppointWithMeDetailActivity;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.view.refreshview.XListView;

import java.util.List;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/21 0021.
 * 带我玩
 */
public class PlayWithMeFragment extends LoadAndPullBaseFragment<AppointWithMeEvent,AppointWithMeBean,AppointWithMeBean.DataBean> implements XListView.IXListViewListener {


    @Override
    protected TravelBaseAdapter initAdapter(List<AppointWithMeBean.DataBean> httpData) {
        return new AppointWithMeAdapter(getContext(), httpData);
    }



    @Override
    protected void initListener() {
       super.initListener();
        setXListViewChildSpace(5);
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), AppointWithMeDetailActivity.class);
                intent.putExtra(IVariable.TID, getmDatas().get(position - 1).getId());
                startActivity(intent);
            }
        });
    }
    @Override
    protected String initUrl() {
        return IVariable.PLAY_WITHE_ME;
    }

}
