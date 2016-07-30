package com.example.administrator.travel.ui.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.DestinationAdapter;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by android on 2016/7/30.
 * 目的地
 */
public class DestinationActivity extends BarBaseActivity {

    private ListView mLvDestination;
    private TextView mTvSearch;

    @Override
    protected void initContentView() {
        mLvDestination = (ListView) findViewById(R.id.lv_destination);
        mTvSearch = FontsIconUtil.findIconFontsById(R.id.tv_search, this);

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_destination;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {
        mLvDestination.setAdapter(new DestinationAdapter(this,null));
    }

    @Override
    protected String setTitleName() {
        return "目的地";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }
}
