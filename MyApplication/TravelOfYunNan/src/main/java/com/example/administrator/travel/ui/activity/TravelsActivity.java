package com.example.administrator.travel.ui.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Travels;
import com.example.administrator.travel.event.TravelsEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.ActivityTravelsAdapter;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

public class TravelsActivity extends LoadingBarBaseActivity implements View.OnKeyListener, XListView.IXListViewListener {

    private static final int LOAD_MORE = 0;
    private static final int SEARCH = 1;
    @ViewInject(R.id.lv_travels)
    private XListView mLvTravels;
    @ViewInject(R.id.et_search)
    private EditText mEtSearch;
    @ViewInject(R.id.tv_search)
    private FontsIconTextView mTvSearch;
    private List<Travels.DataBean> travelsData;
    private String content="";
    private ActivityTravelsAdapter activityTravelsAdapter;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels;
    }

    @Override
    protected void initEvent() {
        mLvTravels.setPullRefreshEnable(false);
        mLvTravels.setPullLoadEnable(true);
        mLvTravels.setXListViewListener(this);
        mEtSearch.setOnKeyListener(this);
        mLvTravels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TravelsActivity.this, TravelsDetailActivity.class);
                intent.putExtra(IVariable.T_ID,travelsData.get(position-1).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onLoad() {
      requestData(LOAD_MORE);
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "游记";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
    @Subscribe
    public void onEvent(TravelsEvent event){
        setIsProgress(false);
        loadEnd(mLvTravels);
        if (event.isSuccess()){
            Travels travels = GsonUtils.getObject(event.getResult(), Travels.class);
            if (travels.getData()==null)return;
            if (activityTravelsAdapter==null) {
                travelsData = travels.getData();
                activityTravelsAdapter = new ActivityTravelsAdapter(this, travelsData);
                mLvTravels.setAdapter(activityTravelsAdapter);
            }else if (event.getType()==SEARCH){
                travelsData = travels.getData();
                activityTravelsAdapter.notifyData(travelsData);
            }else {
                travelsData.addAll(travels.getData());
                activityTravelsAdapter.notifyData(travelsData);
            }
        }else {
            ToastUtils.showToast(event.getMessage());
        }
    }


    /**
     * 搜索
     */
    private void search() {
            content=getString(mEtSearch);
            requestData(SEARCH);
    }

    /**
     * 请求数据
     * @param type
     */
    private void requestData(int type) {
        int count=travelsData==null?0:travelsData.size();
        Map<String, String> travelsMap = MapUtils.Build().addKey(this).addPageSize(10).addCount(count).addContent(content).end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_TRAVELS, travelsMap, type, new TravelsEvent());
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

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        requestData(LOAD_MORE);
    }
}
