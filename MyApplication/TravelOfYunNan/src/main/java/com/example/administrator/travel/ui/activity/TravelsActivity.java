package com.example.administrator.travel.ui.activity;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.ActivityTravelsAdapter;
import com.example.administrator.travel.utils.FontsIconUtil;

public class TravelsActivity extends BarBaseActivity {


    private ListView mLvTravels;
    private TextView mTvSearch;

    @Override
    protected void initContentView() {
        mLvTravels = (ListView) findViewById(R.id.lv_travels);
        mTvSearch = FontsIconUtil.findIconFontsById(R.id.tv_search, this);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels;
    }

    @Override
    protected void initEvent() {
        mLvTravels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(TravelsActivity.this,TravelsDetailActivity.class));
            }
        });
    }

    @Override
    protected void initViewData() {
        mLvTravels.setAdapter(new ActivityTravelsAdapter(this,null));
    }

    @Override
    protected String setTitleName() {
        return "游记";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
