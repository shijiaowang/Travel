package com.yunspeak.travel.ui.me.myappoint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.myappoint.historyorders.HistoryOrdersActivity;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/1 0001.
 * 我的约伴
 */
public class MyAppointActivity extends BaseRecycleViewActivity<MyAppointEvent,MyAppointTogetherBean,Object> implements View.OnClickListener {
    private static final String ENTERING="1";
    private static final String PASSED="2";
    private static final String WITH_ME="4";
    private String type=ENTERING;
    private String preType="0";
   @BindView(R.id.ll_root)
    LinearLayout llRoot;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_appoint_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        super.otherOptionsItemSelected(item);
        startActivity(new Intent(this, HistoryOrdersActivity.class));

    }

    @Override
    protected void doOtherSuccessData(MyAppointEvent myAppointEvent) {
        super.doOtherSuccessData(myAppointEvent);
        switch (myAppointEvent.getType()){
            case BaseToolBarActivity.TYPE_DELETE:
                mAdapter.notifyItemChanged(myAppointEvent.getPosition());
                mDatas.remove(myAppointEvent.getPosition());
                break;
            case BaseToolBarActivity.TYPE_CHANGE:
                changeState(myAppointEvent);
                break;
            case BaseToolBarActivity.TYPE_DISCUSS:
                MyAppointTogetherBean.DataBean dataBean = (MyAppointTogetherBean.DataBean)mDatas.get(myAppointEvent.getPosition());
                dataBean.setState("10");
                mAdapter.notifyItemChanged(myAppointEvent.getPosition());
                ToastUtils.showToast(myAppointEvent.getMessage());
                break;
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        try {
            changeNeedHideView(mSwipe);
            mFlContent.removeView(mRlEmpty);
            mRlEmpty.setVisibility(View.GONE);
            llRoot.addView(mRlEmpty);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mVsContent.setLayoutResource(R.layout.activity_my_appoint_header);
        mVsContent.inflate();
        changeMargin(5,10);
        RadioButton mTvEntering= (RadioButton) findViewById(R.id.tv_entering);
        RadioButton mTvPassed= (RadioButton) findViewById(R.id.tv_passed);
        RadioButton mTvWithMe = (RadioButton) findViewById(R.id.tv_with_me);
        mTvEntering.setOnClickListener(this);
        mTvEntering.setChecked(true);
        mTvPassed.setOnClickListener(this);
        mTvWithMe.setOnClickListener(this);
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
            mAdapter.notifyItemChanged(myAppointEvent.getPosition());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    protected void childAdd(MapUtils.Builder builder, int t) {
        super.childAdd(builder, t);
        builder.addType(type);
    }

    @Override
    protected String initUrl() {
        return IVariable.MY_APPOINT;
    }

    @Override
    protected BaseRecycleViewAdapter<Object> initAdapter(List<Object> httpData) {
        return new MyAppointAdapter(httpData,this);
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

    @Override
    protected void onSuccess(MyAppointEvent myAppointEvent) {
        super.onSuccess(myAppointEvent);
        preType=type;
    }

    private void toRefresh() {
        if (type.equals(preType))return;
        setIsProgress(true);
        resetIsFirstInflate();
        onLoad(TYPE_REFRESH);
    }

    @Override
    protected String initTitle() {
        return "我的约伴";
    }
}
