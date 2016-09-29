package com.example.administrator.travel.ui.appoint.customdestination;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.adddestination.AddCustomDestinationActivity;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.appoint.lineplan.LineBean;
import com.example.administrator.travel.ui.appoint.lineplan.LinePlanEvent;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 自定义景点列表
 */
public class CustomDestinationActivity extends LoadingBarBaseActivity<CustomDestinationEvent> implements XListView.IXListViewListener {
    @ViewInject(R.id.lv_destination)
    private XListView mLvDestination;
    @ViewInject(R.id.bt_diy)
    private Button mBtDiy;
    @ViewInject(R.id.et_search)
    private EditText mEtSearch;
    @ViewInject(R.id.tv_search)
    private TextView mTvSearch;
    private List<CustomDestinationBean.DataBean> mCustomData=new ArrayList<>(0);
    private String content = "";
    private CustomDestinationAdapter customDestinationAdapter;
    private TextView mTvRight;
    private int position;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_custom_destination;
    }

    @Override
    protected void initEvent() {
        GlobalValue.clickPosition=-1;//初始化，避免之前选中的对这边造成影响
        init();
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalValue.clickPosition < 0 || GlobalValue.clickPosition > mCustomData.size()) {
                    ToastUtils.showToast("您尚未选择任何景点。");
                    return;
                }
                if (GlobalValue.mSelectSpot != null) {
                    GlobalValue.mSelectSpot.add(mCustomData.get(GlobalValue.clickPosition).getId());
                }
                CustomDestinationBean.DataBean bodyBean = mCustomData.get(GlobalValue.clickPosition);
                String add = bodyBean.getCity() + "·" + bodyBean.getTitle();
                String id = bodyBean.getId();
                LinePlanEvent linePlanEvent = new LinePlanEvent();
                linePlanEvent.setPosition(position);
                linePlanEvent.setDestination(new LineBean.Destination(id,add));
                EventBus.getDefault().post(linePlanEvent);
                setResult(RESULT_CODE);
                finish();
            }
        });
        mLvDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (GlobalValue.mSelectSpot == null) {
                    GlobalValue.mSelectSpot = new ArrayList<String>();
                }
                if (GlobalValue.mSelectSpot.contains(mCustomData.get(position-1).getId())) {
                    ToastUtils.showToast("已在行程中！");
                    return;
                }
                GlobalValue.clickPosition=position-1;
                customDestinationAdapter.notifyDataSetChanged();
            }
        });
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        mBtDiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomDestinationActivity.this, AddCustomDestinationActivity.class));
            }
        });
    }

    private void search() {
        content = getString(mEtSearch);
        if (StringUtils.isEmpty(content)){
            return;
        }
        requestData(TYPE_REFRESH);
    }

    private void init() {
        mTvRight = getmTvRightIcon();
        mTvRight.setText("确定");
        mLvDestination.setPullRefreshEnable(true);
        mLvDestination.setPullLoadEnable(true);
        mLvDestination.setXListViewListener(this);
        mLvDestination.setRefreshTime(getTime());
        position = getIntent().getIntExtra(IVariable.POSITION, -1);
    }


    @Override
    protected void onLoad(int typeRefresh) {
        requestData(TYPE_LOAD);
    }
    private void requestData(int type) {
        int count = mCustomData == null || type == TYPE_REFRESH ? 0 : mCustomData.size();
        Map<String, String> destinationMap = MapUtils.Build().addKey(this).addPageSize(10).addCount(count).addContent(content).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_CUSTOM_SPOT, destinationMap, type, new CustomDestinationEvent());
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "自定义景点";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }



    private void dealData(CustomDestinationEvent event) {
        if (event.getType()==TYPE_DELETE){//删除逻辑
            mCustomData.remove(event.getDeletePosition());
            customDestinationAdapter.notifyData(mCustomData);
        }else {
            CustomDestinationBean data = GsonUtils.getObject(event.getResult(), CustomDestinationBean.class);
            List<CustomDestinationBean.DataBean> customData = data.getData();
            if (event.getType() == TYPE_REFRESH) {
                mCustomData = customData;
                customDestinationAdapter.notifyData(mCustomData);
            } else {
                mCustomData.addAll(customData);
                customDestinationAdapter = new CustomDestinationAdapter(this, mCustomData);
                mLvDestination.setAdapter(customDestinationAdapter);
            }
        }
    }

    @Override
    public void onRefresh() {
        requestData(TYPE_REFRESH);
    }

    @Override
    public void onLoadMore() {
        requestData(TYPE_LOAD);
    }

    @Override
    protected void onSuccess(CustomDestinationEvent customDestinationEvent) {
        loadEnd(mLvDestination);
        dealData(customDestinationEvent);
    }

    @Override
    protected void onFail(HttpEvent event) {
        super.onFail(event);
        loadEnd(mLvDestination);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValue.clickPosition=-1;
    }
}
