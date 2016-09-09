package com.example.administrator.travel.ui.appoint.customdestination;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.AddCustomDestinationActivity;
import com.example.administrator.travel.ui.activity.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 自定义景点列表
 */
public class CustomDestinationActivity extends LoadingBarBaseActivity implements XListView.IXListViewListener {
    @ViewInject(R.id.lv_destination)
    private XListView mLvDestination;
    @ViewInject(R.id.bt_diy)
    private Button mBtDiy;
    @ViewInject(R.id.et_search)
    private EditText mEtSearch;
    @ViewInject(R.id.tv_search)
    private TextView mTvSearch;
    private List<CustomDestinationBean.DataBean> mCustomData;
    private String content = "";
    private CustomDestinationAdapter customDestinationAdapter;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_custom_destination;
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
        mLvDestination.setPullRefreshEnable(true);
        mLvDestination.setPullLoadEnable(true);
        mLvDestination.setXListViewListener(this);
        mLvDestination.setRefreshTime(getTime());
    }


    @Override
    protected void onLoad() {
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

    @Subscribe
    public void onEvent(CustomDestinationEvent event) {
        setIsProgress(false);
        loadEnd(mLvDestination);
        if (event.isSuccess()) {
            try {
                dealData(event);
            }catch (Exception e){
               e.printStackTrace();
            }
        } else {
            ToastUtils.showToast(event.getMessage());
        }
    }

    private void dealData(CustomDestinationEvent event) {
        if (event.getType()==TYPE_DELETE){//删除逻辑
            mCustomData.remove(event.getDeletePosition());
            customDestinationAdapter.notifyData(mCustomData);
        }else {
            CustomDestinationBean data = GsonUtils.getObject(event.getResult(), CustomDestinationBean.class);
            List<CustomDestinationBean.DataBean> customData = data.getData();
            if (event.getType() == TYPE_LOAD) {
                mCustomData.addAll(customData);
                customDestinationAdapter.notifyData(mCustomData);
            } else {
                mCustomData = customData;
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
}
