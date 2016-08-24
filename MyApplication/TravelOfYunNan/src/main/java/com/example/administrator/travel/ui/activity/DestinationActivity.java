package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Destination;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.DestinationAdapter;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

/**
 * Created by android on 2016/7/30.
 * 目的地
 */
public class DestinationActivity extends LoadingBarBaseActivity implements XListView.IXListViewListener {
    @ViewInject(R.id.lv_destination)
    private XListView mLvDestination;
    @ViewInject(R.id.tv_search)
    private TextView mTvSearch;
    @ViewInject(R.id.et_destination_search)
    private EditText mEtSearch;
    private int currentPage = 0;
    private List<Destination.DataBean.BodyBean> destinationData;
    private DestinationAdapter destinationAdapter;
    private static final int LOAD_MORE=0;//加载
    private static final int LOAD_SEARCH=1;//搜索
    private String content="";//搜索内容
    private String province="";
    private String city="";
    private String typelist ="";
    private String star ="";
    private String score ="";

    @Override
    protected int setContentLayout() {
        return R.layout.activity_destination;
    }


    @Override
    protected void initEvent() {
        mLvDestination.setPullRefreshEnable(false);
        mLvDestination.setPullLoadEnable(true);
        mLvDestination.setXListViewListener(this);
        mLvDestination.setRefreshTime(getTime());
        mLvDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DestinationActivity.this, DestinationDetailActivity.class);
                //xlistview有header的原因
                intent.putExtra(IVariable.T_ID,destinationData.get(position-1).getId());
                intent.putExtra(IVariable.NAME,destinationData.get(position-1).getTitle());
                startActivity(intent);
            }
        });
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content=getString(mEtSearch);
                requestData(LOAD_SEARCH);
            }
        });
    }

    @Override
    protected void onLoad() {
        requestData(LOAD_MORE);
    }

    private void requestData(int type) {
        Map<String, String> destinationMap = MapUtils.Build().addKey(this).add(IVariable.PAGE_SIZE, "6").add(IVariable.PAGE, currentPage + "").
                add(IVariable.CONTENT, content).add(IVariable.PROVINCE, province).add(IVariable.CITY,city).add(IVariable.TYPELIST,typelist)
                .add(IVariable.STAR, star).add(IVariable.SCORE, score).
                end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_DESTINATION, destinationMap,type);
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
        setIsProgress(false);
        if (event.isSuccess()) {
            dealDestinationData(event);
        } else {
            if (currentPage>0)currentPage--;
            ToastUtils.showToast(event.getMessage());
        }
        loadEnd(mLvDestination);
    }
    private void dealDestinationData(HttpEvent event) {
        Destination destination = GsonUtils.getObject(event.getResult(), Destination.class);
        if (destinationAdapter == null) {
            destinationData = destination.getData().getBody();
            destinationAdapter = new DestinationAdapter(this, destinationData);
            mLvDestination.setAdapter(destinationAdapter);
        } else if (event.getType()==LOAD_SEARCH){//搜索
            destinationData=destination.getData().getBody();
            destinationAdapter.notifyData(destinationData);
        }else {//加载更多
            List<Destination.DataBean.BodyBean> body = destination.getData().getBody();
            destinationData.addAll(body);
            destinationAdapter.notifyData(destinationData);
        }
        LogUtils.e(destinationData.size()+"");
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        currentPage++;
        onLoad();
    }
}
