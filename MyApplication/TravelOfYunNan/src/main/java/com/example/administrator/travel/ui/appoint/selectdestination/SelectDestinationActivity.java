package com.example.administrator.travel.ui.appoint.selectdestination;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Destination;
import com.example.administrator.travel.event.DestinationEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.adapter.DestinationAdapter;
import com.example.administrator.travel.ui.appoint.customdestination.CustomDestinationActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 选择目的地
 */
public class SelectDestinationActivity extends LoadingBarBaseActivity implements XListView.IXListViewListener {
    @ViewInject(R.id.tv_search)
    private TextView mTvSearch;
    @ViewInject(R.id.et_search)
    private EditText mEtSearch;
    @ViewInject(R.id.lv_destination)
    private XListView mLvDestination;
    @ViewInject(R.id.bv_diy)
    private Button mTvDiy;
    private TextView mTvRight;
    private String content = "";//搜索内容
    private String province = "";
    private String city = "";
    private String typelist = "";
    private String star = "";
    private String score = "";
    private String type;
    private List<Destination.DataBean.BodyBean> destinationData;
    private SelectDestinationAdapter destinationAdapter;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_select_destination;
    }

    @Override
    protected void initEvent() {

        init();
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        mTvDiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(SelectDestinationActivity.this,CustomDestinationActivity.class));
            }
        });
    }

    private void search() {
        content = getString(mEtSearch);
        requestData(TYPE_SEARCH);
    }

    private void init() {
        mTvRight = getmTvRightIcon();
        mTvRight.setText("确定");
        mLvDestination.setPullRefreshEnable(false);
        mLvDestination.setPullLoadEnable(true);
        mLvDestination.setXListViewListener(this);
        mLvDestination.setRefreshTime(getTime());
    }

    @Override
    protected void onLoad() {
         requestData(TYPE_LOAD);
    }
    private void requestData(int type) {
        int count = destinationData == null ? 0 : destinationData.size();
        Map<String, String> destinationMap = MapUtils.Build().addKey(this).addPageSize(6).addCount(count).
                add(IVariable.CONTENT, content).add(IVariable.PROVINCE, province).add(IVariable.CITY, city).add(IVariable.TYPELIST, typelist)
                .add(IVariable.STAR, star).add(IVariable.SCORE, score).
                        end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_DESTINATION, destinationMap, type, new DestinationEvent());
    }

    @Override
    protected Activity initViewData() {
        mLvDestination.setAdapter(new SelectDestinationAdapter(this, null));
        return this;
    }

    @Override
    protected String setTitleName() {
        return "选择目的地";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
    @Subscribe
    public void onEvent(DestinationEvent event) {
        setIsProgress(false);
        if (event.isSuccess()) {
            dealDestinationData(event);
        } else {
            ToastUtils.showToast(event.getMessage());
        }
        loadEnd(mLvDestination);
    }
    private void dealDestinationData(DestinationEvent event) {
        Destination destination = GsonUtils.getObject(event.getResult(), Destination.class);
        if (destinationAdapter == null) {
            destinationData = destination.getData().getBody();
            destinationAdapter = new SelectDestinationAdapter(this, destinationData);
            mLvDestination.setAdapter(destinationAdapter);
        } else if (event.getType() == TYPE_SEARCH) {//搜索
            destinationData = destination.getData().getBody();
            destinationAdapter.notifyData(destinationData);
        } else {//加载更多
            List<Destination.DataBean.BodyBean> body = destination.getData().getBody();
            destinationData.addAll(body);
            destinationAdapter.notifyData(destinationData);
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
       requestData(TYPE_LOAD);
    }
}
