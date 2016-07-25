package com.example.administrator.travel.ui.activity;


import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.ActiveAdapter;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/25 0025.
 * 活动界面
 */
public class ActiveActivity extends BaseActivity {

    private ListView mLvActive;//活动列表
    private TextView mTvBack;//返回

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_active;
    }

    @Override
    protected void initView() {
        mLvActive = (ListView) findViewById(R.id.lv_active);
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
    }

    @Override
    protected void initListener() {
      mTvBack.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });
    }

    @Override
    protected void initData() {
       mLvActive.setAdapter(new ActiveAdapter(this,null));
    }
}
