package com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination.adddestination.AddCustomDestinationActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.LineBean;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.LinePlanEvent;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/9/8 0008.
 * 自定义景点列表
 */
public class CustomDestinationActivity extends BaseRecycleViewActivity<CustomDestinationEvent,CustomDestinationBean,CustomDestinationBean.DataBean>  {

    @ViewInject(R.id.bt_diy) Button mBtDiy;
    @ViewInject(R.id.et_search) EditText mEtSearch;
    @ViewInject(R.id.tv_search) TextView mTvSearch;
    private String content = "";
    private int position;


    @Override
    protected void initEvent() {
        super.initEvent();
        mVsContent.setLayoutResource( R.layout.activity_custom_destination_header);
        mVsContent.inflate();
        GlobalValue.clickPosition=-1;//初始化，避免之前选中的对这边造成影响
        position = getIntent().getIntExtra(IVariable.POSITION, -1);
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
        onLoad(TYPE_REFRESH);
    }

    @Override
    protected String initRightText() {
        return "确定";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        if (GlobalValue.clickPosition < 0 || GlobalValue.clickPosition > mDatas.size()) {
            ToastUtils.showToast("您尚未选择任何景点。");
            return;
        }
        if (GlobalValue.mSelectSpot != null) {
            GlobalValue.mSelectSpot.add(mDatas.get(GlobalValue.clickPosition).getId());
        }
        CustomDestinationBean.DataBean bodyBean = mDatas.get(GlobalValue.clickPosition);
        String add = bodyBean.getCity() + "·" + bodyBean.getTitle();
        String id = bodyBean.getId();
        LinePlanEvent linePlanEvent = new LinePlanEvent();
        linePlanEvent.setPosition(position);
        linePlanEvent.setDestination(new LineBean.Destination(id,add));
        EventBus.getDefault().post(linePlanEvent);
        setResult(RESULT_CODE);
        finish();
    }

    @Override
    protected String initUrl() {
        return IVariable.GET_CUSTOM_SPOT;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.addContent(content);
    }

    @Override
    protected void onSuccess(CustomDestinationEvent customDestinationEvent) {
       switch (customDestinationEvent.getType()){
           case TYPE_DELETE:
               mDatas .remove(customDestinationEvent.getDeletePosition());
               mAdapter.notifiyData(mDatas);
               break;
           default:
               super.onSuccess(customDestinationEvent);
               break;
       }
    }

    @Override
    protected void onFail(CustomDestinationEvent event) {
        super.onFail(event);
    }

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<CustomDestinationBean.DataBean> mDatas) {
        return new CustomDestinationAdapter(mDatas,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValue.clickPosition=-1;
    }

    @Override
    public void onItemClick(int position) {
        if (GlobalValue.mSelectSpot == null) {
            GlobalValue.mSelectSpot = new ArrayList<String>();
        }
        if (GlobalValue.mSelectSpot.contains(mDatas.get(position).getId())) {
            ToastUtils.showToast("已在行程中！");
            return;
        }
        GlobalValue.clickPosition=position;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected String initTitle() {
        return "自定义景点";
    }
}
