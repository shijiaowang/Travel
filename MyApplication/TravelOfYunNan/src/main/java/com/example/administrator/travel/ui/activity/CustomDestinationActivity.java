package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.CustomeDestinationAdapter;
import com.example.administrator.travel.ui.view.refreshview.XListView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 自定义景点
 */
public class CustomDestinationActivity  extends LoadingBarBaseActivity{
    @ViewInject(R.id.lv_destination)
    private XListView mLvDestination;
    @ViewInject(R.id.bt_diy)
    private Button mBtDiy;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_custom_destination;
    }

    @Override
    protected void initEvent() {
        mBtDiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomDestinationActivity.this,AddCustomDestinationActivity.class));
            }
        });
        List<String> list=new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
           mLvDestination.setAdapter(new CustomeDestinationAdapter(this,list));
    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        return null;
    }

    @Override
    protected String setTitleName() {
        return "自定义景点";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
