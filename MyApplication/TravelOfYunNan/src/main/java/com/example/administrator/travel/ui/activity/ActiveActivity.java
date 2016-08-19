package com.example.administrator.travel.ui.activity;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.ActiveAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/25 0025.
 * 活动界面
 */
public class ActiveActivity extends BarBaseActivity {
    @ViewInject(R.id.lv_active)
    private ToShowAllListView mLvActive;//活动列表



    @Override
    protected int setContentLayout() {
        return R.layout.activity_active;
    }

    @Override
    protected void initEvent() {
        mLvActive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ActiveActivity.this,ActivateDetailActivity.class));
            }
        });
    }


    @Override
    protected void initViewData() {
        mLvActive.setAdapter(new ActiveAdapter(this,null));
    }

    @Override
    protected String setTitleName() {
        return "活动";
    }

    @Override
    public float getAlpha() {
        return 0f;
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }
}
