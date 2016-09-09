package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.appoint.customdestination.CustomDestinationActivity;
import com.example.administrator.travel.ui.adapter.SelectDestinationAdapter;
import com.example.administrator.travel.ui.view.refreshview.XListView;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 选择目的地
 */
public class SelectDestinationActivity extends LoadingBarBaseActivity {
    @ViewInject(R.id.lv_destination)
    private XListView mLvDestination;
    @ViewInject(R.id.bv_diy)
    private Button mTvDiy;
    private TextView mTvRight;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_select_destination;
    }

    @Override
    protected void initEvent() {
        init();
        mTvDiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(SelectDestinationActivity.this,CustomDestinationActivity.class));
            }
        });
    }

    private void init() {
        mTvRight = getmTvRightIcon();
        mTvRight.setText("确定");
    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        mLvDestination.setAdapter(new SelectDestinationAdapter(this, null));
        return null;
    }

    @Override
    protected String setTitleName() {
        return "选择目的地";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
