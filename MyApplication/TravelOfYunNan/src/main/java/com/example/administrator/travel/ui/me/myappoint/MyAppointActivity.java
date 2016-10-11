package com.example.administrator.travel.ui.me.myappoint;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.global.ParentBean;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailActivity;
import com.example.administrator.travel.ui.appoint.withme.withmedetail.AppointWithMeDetailActivity;
import com.example.administrator.travel.ui.baseui.BaseToolBarActivity;
import com.example.administrator.travel.ui.baseui.LoadAndRefreshBaseActivity;
import com.example.administrator.travel.ui.me.myappoint.withmeselect.MyWithMeSelectBean;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.MapUtils;

import org.xutils.view.annotation.ViewInject;
import java.util.List;

/**
 * Created by wangyang on 2016/8/1 0001.
 * 我的约伴
 */
public class MyAppointActivity extends LoadAndRefreshBaseActivity<MyAppointEvent,MyAppointTogetherBean,Object> implements View.OnClickListener {
    private static final String ENTERING="1";
    private static final String PASSED="2";
    private static final String HISTORY="3";
    private static final String WITH_ME="4";
    private String type=ENTERING;
    private String preType=ENTERING;
    @ViewInject(R.id.lv_my_appoint)
    private XListView mLvMyAppoint;
    @ViewInject(R.id.tv_entering)
    private RadioButton mTvEntering;
    @ViewInject(R.id.tv_passed)
    private RadioButton mTvPassed;
    @ViewInject(R.id.tv_with_me)
    private RadioButton mTvWithMe;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_appoint;
    }


    @Override
    protected Activity initViewData() {
        TextView mTvHistory = getmTvRightIcon();
        mTvHistory.setText("历史订单");
        mTvEntering.setOnClickListener(this);
        mTvEntering.setChecked(true);
        mTvPassed.setOnClickListener(this);
        mTvHistory.setOnClickListener(this);
        mTvWithMe.setOnClickListener(this);
        mLvMyAppoint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type==WITH_ME) {
                    MyAppointWithMeBean.DataBean dataBean = (MyAppointWithMeBean.DataBean) httpData.get(position - 1);
                    Intent intent=new Intent(MyAppointActivity.this,AppointWithMeDetailActivity.class);
                    intent.putExtra(IVariable.TID,dataBean.getId());
                    startActivity(intent);
                }else {
                    MyAppointTogetherBean.DataBean dataBean = (MyAppointTogetherBean.DataBean) httpData.get(position - 1);
                    Intent intent=new Intent(MyAppointActivity.this,AppointTogetherDetailActivity.class);
                    intent.putExtra(IVariable.T_ID,dataBean.getId());
                    startActivity(intent);
                }
            }
        });
        return this;
    }

    @Override
    protected void doOtherSuccessData(MyAppointEvent myAppointEvent) {
        super.doOtherSuccessData(myAppointEvent);
        switch (myAppointEvent.getType()){
            case BaseToolBarActivity.TYPE_DELETE:
                httpData.remove(myAppointEvent.getPosition());
                adapter.notifyDataSetChanged();
                break;
            case BaseToolBarActivity.TYPE_CHANGE:
                changeState(myAppointEvent);
                break;
        }
    }

    /**
     * 修改boos点击后出发按钮的状态
     * @param myAppointEvent
     */
    private void changeState(MyAppointEvent myAppointEvent) {
        try {
            MyAppointTogetherBean.DataBean dataBean = (MyAppointTogetherBean.DataBean)httpData.get(myAppointEvent.getPosition());
            int payStatus = myAppointEvent.getPayStatus();
            switch (payStatus){
                case 3:
                    dataBean.setState("7");
                    break;
                case 7:
                    dataBean.setState("8");
                    break;
                case 8:
                    dataBean.setState("9");
                    break;
            }
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String setTitleName() {
        return "我的约伴";
    }

    @Override
    protected void childAdd(MapUtils.Builder builder) {
        builder.addType(type);
    }

    @Override
    public float getAlpha() {
        return 1f;
    }

    @Override
    public XListView setXListView() {
        return mLvMyAppoint;
    }

    @Override
    protected String initUrl() {
        return IVariable.MY_APPOINT;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<Object> httpData) {
        return new MyAppointAdapter(this,httpData);
    }

    @Override
    protected boolean isUserChild() {
        return true;
    }

    @Override
    protected Class<? extends ParentBean> useChildedBean() {
        if (type.equals(WITH_ME)){
            return MyAppointWithMeBean.class;
        }
        return MyAppointTogetherBean.class;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_passed:
                type=PASSED;
                toRefresh();
                break;
            case R.id.tv_entering:
                type=ENTERING;
                toRefresh();
                break;
            case R.id.tv_with_me:
                type=WITH_ME;
                toRefresh();
                break;
            case R.id.tv_right_icon:
                type=HISTORY;
                toRefresh();
                break;
        }
    }

    private void toRefresh() {
        if (type.equals(preType))return;
        preType=type;
        onLoad(TYPE_REFRESH);
    }
}
