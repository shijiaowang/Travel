package com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CustomDestinationBean;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.bean.LineBean;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.LinePlanEvent;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination.adddestination.AddCustomDestinationActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by wangyang on 2016/9/8 0008.
 * 自定义景点列表
 */
public class CustomDestinationActivity extends BaseRecycleViewActivity<CustomDestinationEvent,CustomDestinationBean,CustomDestinationBean.DataBean>  {


    private String content = "";
    private int position;
    private EditText mEtSearch;


    @Override
    protected void initEvent() {
        super.initEvent();
        mVsContent.setLayoutResource( R.layout.activity_custom_destination_header);
        mVsContent.inflate();
        Button mBtDiy= (Button) findViewById(R.id.bt_diy);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        TextView mTvSearch= (TextView) findViewById(R.id.tv_search);
        GlobalValue.clickPosition=-1;//初始化，避免之前选中的对这边造成影响
        position = getIntent().getIntExtra(IVariable.POSITION, -1);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //一般输入法或搜狗输入法点击搜索按键
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //这里调用搜索方法
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0); //强制隐藏键盘
                    search();
                    return true;
                }
                return false;
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

            content = mEtSearch.getText().toString().trim();
            resetIsFirstInflate();
            setIsProgress(true);
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
        GlobalValue.clickPosition=position;
        mAdapter.notifyItemChanged(position);
    }

    @Override
    protected String initTitle() {
        return "自定义景点";
    }
}
