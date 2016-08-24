package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Destination;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.DestinationAdapter;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

/**
 * Created by android on 2016/7/30.
 * 目的地
 */
public class DestinationActivity extends LoadingBarBaseActivity {
    @ViewInject(R.id.lv_destination)
    private ListView mLvDestination;
    @ViewInject(R.id.tv_search)
    private TextView mTvSearch;
    private int currentPage = 0;
    private List<Destination.DataBean.BodyBean> destinationData;
    private DestinationAdapter destinationAdapter;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_destination;
    }

    @Override
    protected void initEvent() {
        mLvDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(DestinationActivity.this, DestinationDetailActivity.class));
            }
        });
    }

    @Override
    protected void onLoad() {
        Map<String, String> destinationMap = MapUtils.Build().addKey(this).add(IVariable.PAGE_SIZE, "6").add(IVariable.PAGE, currentPage + "").end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_DESTINATION, destinationMap, 0);
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "目的地";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }

    @Subscribe
    public void onEvent(HttpEvent event) {
        if (event.isSuccess()) {
            dealDestinationData(event);
        } else {
            ToastUtils.showToast(event.getMessage());
        }
    }

    private void dealDestinationData(HttpEvent event) {
        Destination destination = GsonUtils.getObject(event.getResult(), Destination.class);

        if (destinationAdapter == null) {
            destinationData = destination.getData().getBody();
            destinationAdapter = new DestinationAdapter(this, destinationData);
            mLvDestination.setAdapter(destinationAdapter);
        } else {
            destinationData.addAll(destination.getData().getBody());
            destinationAdapter.notifyData(destinationData);
        }
    }
}
