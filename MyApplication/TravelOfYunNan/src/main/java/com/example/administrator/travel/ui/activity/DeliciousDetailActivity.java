package com.example.administrator.travel.ui.activity;

import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.DeliciousDetailAdapter;
import com.example.administrator.travel.ui.adapter.DeliciousDiscussAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 美食详情
 */
public class DeliciousDetailActivity extends BarBaseActivity {
    @ViewInject(R.id.lv_delicious_detail)
    private ToShowAllListView mLvDeliciousDetail;
    @ViewInject(R.id.lv_delicious_discuss)
    private ToShowAllListView mLvDeliciousDiscuss;



    @Override
    protected int setContentLayout() {
        return R.layout.activity_delicious_detail;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {
        mLvDeliciousDetail.setAdapter(new DeliciousDetailAdapter(this, null));
        mLvDeliciousDiscuss.setAdapter(new DeliciousDiscussAdapter(this, null));
    }

    @Override
    protected String setTitleName() {
        return "美食详情";
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
    public float getAlpha() {
        return 0f;
    }
}
