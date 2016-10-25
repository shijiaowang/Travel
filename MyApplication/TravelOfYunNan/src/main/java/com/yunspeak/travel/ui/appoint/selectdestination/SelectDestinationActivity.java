package com.yunspeak.travel.ui.appoint.selectdestination;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Destination;
import com.yunspeak.travel.event.DestinationEvent;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.LoadingBarBaseActivity;
import com.yunspeak.travel.ui.appoint.customdestination.CustomDestinationActivity;
import com.yunspeak.travel.ui.appoint.lineplan.LineBean;
import com.yunspeak.travel.ui.appoint.lineplan.LinePlanEvent;
import com.yunspeak.travel.ui.view.refreshview.XListView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/9/8 0008.
 * 选择目的地
 */
public class SelectDestinationActivity extends LoadingBarBaseActivity<DestinationEvent> implements XListView.IXListViewListener {
    @ViewInject(R.id.tv_search)
    private TextView mTvSearch;
    @ViewInject(R.id.et_search)
    private EditText mEtSearch;
    @ViewInject(R.id.lv_destination)
    private XListView mLvDestination;
    @ViewInject(R.id.bv_diy)
    private Button mBtDiy;
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
    private int position;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_select_destination;
    }

    @Override
    protected void initEvent() {
        init();
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalValue.clickPosition < 0 || GlobalValue.clickPosition > destinationData.size()) {
                    ToastUtils.showToast("您尚未选择任何景点。");
                    return;
                }
                if (GlobalValue.mSelectSpot != null) {
                    GlobalValue.mSelectSpot.add(destinationData.get(GlobalValue.clickPosition).getId());
                }
                Destination.DataBean.BodyBean bodyBean = destinationData.get(GlobalValue.clickPosition);
                String add = bodyBean.getCity() + "·" + bodyBean.getTitle();
                String id = bodyBean.getId();
                LinePlanEvent linePlanEvent = new LinePlanEvent();
                linePlanEvent.setPosition(position);
                linePlanEvent.setDestination(new LineBean.Destination(id,add));
                EventBus.getDefault().post(linePlanEvent);
                finish();
            }
        });
        mLvDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (GlobalValue.mSelectSpot == null) {
                    GlobalValue.mSelectSpot = new ArrayList<String>();
                }
                if (GlobalValue.mSelectSpot.contains(destinationData.get(position-1).getId())) {
                    ToastUtils.showToast("已在行程中！");
                    return;
                }
                GlobalValue.clickPosition = position - 1;
                destinationAdapter.notifyDataSetChanged();
            }
        });
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE && resultCode==RESULT_CODE) {
            finish();
        }

    }

    private void search() {
        content = getString(mEtSearch);
        onLoad(TYPE_SEARCH);
    }

    private void init() {
        mTvRight = getmTvRightIcon();
        mTvRight.setText("确定");
        mLvDestination.setPullRefreshEnable(false);
        mLvDestination.setPullLoadEnable(true);
        mLvDestination.setXListViewListener(this);
        mLvDestination.setRefreshTime(getTime());
        position = getIntent().getIntExtra(IVariable.POSITION, -1);
        if (GlobalValue.mAppointType==IVariable.TYPE_WITH_ME){
            mBtDiy.setVisibility(View.GONE);
        }else {
            mBtDiy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SelectDestinationActivity.this, CustomDestinationActivity.class);
                    intent.putExtra(IVariable.POSITION, position);
                    startActivityForResult(intent, REQ_CODE);
                }
            });
        }
    }

    @Override
    protected void onLoad(int type) {
        int count = destinationData == null ? 0 : destinationData.size();
        Map<String, String> destinationMap = MapUtils.Build().addKey(this).addPageSize(6).addCount(count).
                add(IVariable.CONTENT, content).add(IVariable.PROVINCE, province).add(IVariable.CITY, city).add(IVariable.TYPELIST, typelist)
                .add(IVariable.STAR, star).add(IVariable.SCORE, score).
                        end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_DESTINATION, destinationMap, type, new DestinationEvent()   );
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
    protected void onSuccess(DestinationEvent destinationEvent) {
        loadEnd(mLvDestination);
        dealDestinationData(destinationEvent);
    }

    @Override
    protected void onFail(DestinationEvent event) {
        super.onFail(event);
        loadEnd(mLvDestination);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (destinationAdapter != null) {
            destinationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValue.clickPosition = -1;
    }
}