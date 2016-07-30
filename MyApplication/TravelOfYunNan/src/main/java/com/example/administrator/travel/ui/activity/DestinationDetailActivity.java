package com.example.administrator.travel.ui.activity;

import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.DeliciousDiscussAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by android on 2016/7/30.
 * 目的地详情
 */
public class DestinationDetailActivity extends BarBaseActivity{

    private ToShowAllListView mLvDiscuss;

    @Override
    protected void initContentView() {
        TextView mTvDisscuss = FontsIconUtil.findIconFontsById(R.id.tv_discuss, this);
        TextView mTvSearch = FontsIconUtil.findIconFontsById(R.id.tv_search, this);
        TextView mTvSpot = FontsIconUtil.findIconFontsById(R.id.tv_spot, this);
        TextView mTvSpotAdd = FontsIconUtil.findIconFontsById(R.id.tv_spot_add, this);
        mLvDiscuss = (ToShowAllListView) findViewById(R.id.lv_discuss);

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_add_detail;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {
   mLvDiscuss.setAdapter(new DeliciousDiscussAdapter(this,null));
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }

    @Override
    protected String setTitleName() {
        return "目的地";
    }
}
