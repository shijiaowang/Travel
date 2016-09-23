package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Destination;
import com.example.administrator.travel.event.DestinationEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.DestinationAdapter;
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
 * Created by android on 2016/7/30.
 * 目的地  美食共用
 */
public class FindCommonActivity extends LoadingBarBaseActivity implements XListView.IXListViewListener, View.OnKeyListener {
    @ViewInject(R.id.lv_destination)
    private XListView mLvDestination;
    @ViewInject(R.id.tv_search)
    private TextView mTvSearch;
    @ViewInject(R.id.et_destination_search)
    private EditText mEtSearch;
    private List<Destination.DataBean.BodyBean> destinationData;
    private DestinationAdapter destinationAdapter;
    private String content = "";//搜索内容
    private String province = "";
    private String city = "";
    private String typelist = "";
    private String star = "";
    private String score = "";
    private String type;
    private String url;
    private boolean isDestination;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_destination;
    }


    @Override
    protected void initEvent() {
        type = getIntent().getStringExtra(IVariable.TYPE);
        isDestination = type.equals(IVariable.TYPE_DESTINATION);
        url = isDestination ? IVariable.FIND_DESTINATION : IVariable.FIND_FOOD;

        mLvDestination.setPullRefreshEnable(false);
        mLvDestination.setPullLoadEnable(true);
        mLvDestination.setXListViewListener(this);
        mEtSearch.setOnKeyListener(this);
        mLvDestination.setRefreshTime(getTime());
        mLvDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;

                if (isDestination) {
                    intent = new Intent(FindCommonActivity.this, DestinationDetailActivity.class);
                } else {
                    intent = new Intent(FindCommonActivity.this, DeliciousDetailActivity.class);
                }
                //xlistview有header的原因
                intent.putExtra(IVariable.T_ID, destinationData.get(position - 1).getId());
                intent.putExtra(IVariable.NAME, destinationData.get(position - 1).getTitle());
                startActivity(intent);
            }
        });
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    private void search() {
        content = getString(mEtSearch);
        requestData(TYPE_SEARCH);
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
        XEventUtils.getUseCommonBackJson(url, destinationMap, type, new DestinationEvent());
    }

    @Override
    protected Activity initViewData() {
        changeTitle(isDestination ? "目的地" : "美食");
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
            destinationAdapter = new DestinationAdapter(this, destinationData);
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
        onLoad();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0); //强制隐藏键盘
            search();
            return true;
        }
        return false;
    }
}
