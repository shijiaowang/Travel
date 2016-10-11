package com.example.administrator.travel.ui.me.myappoint;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.global.ParentBean;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailActivity;
import com.example.administrator.travel.ui.appoint.withme.withmedetail.AppointWithMeDetailActivity;
import com.example.administrator.travel.ui.baseui.BaseToolBarActivity;
import com.example.administrator.travel.ui.baseui.BaseXListViewActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/1 0001.
 * 我的约伴
 */
public class MyAppointActivity extends BaseXListViewActivity<MyAppointEvent,MyAppointTogetherBean,Object> implements View.OnClickListener {
    private static final String ENTERING="1";
    private static final String PASSED="2";
    private static final String HISTORY="3";
    private static final String WITH_ME="4";
    private String type=ENTERING;
    private String preType=ENTERING;


    @Override
    protected Activity initDataAndRegisterEventBus() {
        mVsHeader.setLayoutResource(R.layout.activity_my_appoint_header);
        mVsHeader.inflate();
        RadioButton mTvEntering= (RadioButton) findViewById(R.id.tv_entering);
        RadioButton mTvPassed= (RadioButton) findViewById(R.id.tv_passed);
        RadioButton mTvWithMe = (RadioButton) findViewById(R.id.tv_with_me);
        mTvEntering.setOnClickListener(this);
        mTvEntering.setChecked(true);
        mTvPassed.setOnClickListener(this);
        mTvWithMe.setOnClickListener(this);
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_appoint_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void childItemClick(int position) {
        if (type==WITH_ME) {
            MyAppointWithMeBean.DataBean dataBean = (MyAppointWithMeBean.DataBean) mDatas.get(position);
            Intent intent=new Intent(MyAppointActivity.this,AppointWithMeDetailActivity.class);
            intent.putExtra(IVariable.TID,dataBean.getId());
            startActivity(intent);
        }else {
            MyAppointTogetherBean.DataBean dataBean = (MyAppointTogetherBean.DataBean) mDatas.get(position);
            Intent intent=new Intent(MyAppointActivity.this,AppointTogetherDetailActivity.class);
            intent.putExtra(IVariable.T_ID,dataBean.getId());
            startActivity(intent);
        }
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        super.otherOptionsItemSelected(item);
        type=HISTORY;
        toRefresh();

    }

    @Override
    protected void doOtherSuccessData(MyAppointEvent myAppointEvent) {
        super.doOtherSuccessData(myAppointEvent);
        switch (myAppointEvent.getType()){
            case BaseToolBarActivity.TYPE_DELETE:
                mDatas.remove(myAppointEvent.getPosition());
                adapter.notifyDataSetChanged();
                break;
            case BaseToolBarActivity.TYPE_CHANGE:
                changeState(myAppointEvent);
                break;
            case BaseToolBarActivity.TYPE_DISCUSS:
                MyAppointTogetherBean.DataBean dataBean = (MyAppointTogetherBean.DataBean)mDatas.get(myAppointEvent.getPosition());
                dataBean.setState("10");
                adapter.notifyDataSetChanged();
                ToastUtils.showToast(myAppointEvent.getMessage());
                break;
        }
    }

    /**
     * 修改boos点击后出发按钮的状态
     * @param myAppointEvent
     */
    private void changeState(MyAppointEvent myAppointEvent) {
        try {
            MyAppointTogetherBean.DataBean dataBean = (MyAppointTogetherBean.DataBean)mDatas.get(myAppointEvent.getPosition());
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
    protected void childAdd(MapUtils.Builder builder) {
        builder.addType(type);
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

        }
    }

    private void toRefresh() {
        if (type.equals(preType))return;
        preType=type;
        onLoad(TYPE_REFRESH);
    }

    @Override
    protected String initTitle() {
        return "我的约伴";
    }
}
