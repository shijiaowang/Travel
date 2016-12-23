package com.yunspeak.travel.ui.me.myappoint;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.MyAppointTogetherBean;
import com.yunspeak.travel.bean.MyAppointWithMeBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.myappoint.historyorders.HistoryOrdersActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.PayNotifyEvent;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.backmoney.BackMoneyNotifyEvent;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/1 0001.
 * 我的约伴
 */
public class MyAppointActivity extends BaseRecycleViewActivity<MyAppointEvent,MyAppointTogetherBean,Object> implements View.OnClickListener {
    public static final String ENTERING="1";
    public static final String PASSED="2";
    public static final String WITH_ME="4";
    private String type=PASSED;
    private String preType="0";
   @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_appoint_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

  public static void start(Context context,String type){
      Intent intent=new Intent(context,MyAppointActivity.class);
     // intent.putExtra(IVariable.TYPE,type);
      context.startActivity(intent);
  }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        super.otherOptionsItemSelected(item);
        startActivity(new Intent(this, HistoryOrdersActivity.class));

    }
   @Subscribe
   public void onEvent(BackMoneyNotifyEvent event){
       onLoad(TYPE_REFRESH);
   }

    @Override
    protected void initEvent() {
        super.initEvent();

        mVsContent.setLayoutResource(R.layout.activity_my_appoint_header);
        mVsContent.inflate();
        changeMargin(5,10);
        RadioButton mRbEntering = (RadioButton) findViewById(R.id.tv_entering);
        RadioButton mRbPassed = (RadioButton) findViewById(R.id.tv_passed);
        RadioButton mRbWithMe = (RadioButton) findViewById(R.id.tv_with_me);
        mRbEntering.setOnClickListener(this);

        mRbPassed.setOnClickListener(this);
        mRbWithMe.setOnClickListener(this);
        String type = getIntent().getStringExtra(IVariable.DATA);
        if (!StringUtils.isEmpty(type)){
            if (type.equals(PASSED)){
                this.type=type;
                mRbPassed.setChecked(true);
            }else if (type.equals(WITH_ME)){
                this.type=type;
                mRbWithMe.setChecked(true);
            }else {
                mRbEntering.setChecked(true);
            }
        }else {
            mRbPassed.setChecked(true);
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
            mAdapter.notifyItemChanged(myAppointEvent.getPosition());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Subscribe
    public void onEvent(PayNotifyEvent payNotifyEvent){
        onLoad(TYPE_REFRESH);
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
    protected boolean isUseChild() {
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
        switch (myAppointEvent.getType()){
            case TYPE_DELETE:
                mDatas.remove(myAppointEvent.getPosition());
                mAdapter.notifyItemChanged(myAppointEvent.getPosition());
                break;
            case TYPE_CHANGE:
                changeState(myAppointEvent);
                break;
            case TYPE_DELETE2://取消约伴数据
                ToastUtils.showToast("取消报名成功");
                mDatas.remove(myAppointEvent.getPosition());
                mAdapter.notifyItemRemoved(myAppointEvent.getPosition());

                break;
            case TYPE_DISCUSS:
                MyAppointTogetherBean.DataBean dataBean = (MyAppointTogetherBean.DataBean)mDatas.get(myAppointEvent.getPosition());
                dataBean.setState("10");
                mAdapter.notifyItemChanged(myAppointEvent.getPosition());
                ToastUtils.showToast(myAppointEvent.getMessage());
                break;
            default:
                super.onSuccess(myAppointEvent);
                break;
        }
        preType=type;
    }


    private void toRefresh() {
        if (type.equals(preType))return;
        if (useCommonBackJson!=null && !useCommonBackJson.isCancelled())useCommonBackJson.cancel();
        setIsProgress(true);
        onLoad(TYPE_REFRESH);
    }

    @Override
    protected String initTitle() {
        return "我的行程";
    }





}
