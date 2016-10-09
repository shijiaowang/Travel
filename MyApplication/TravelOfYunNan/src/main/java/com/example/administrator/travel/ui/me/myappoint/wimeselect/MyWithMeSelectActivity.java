package com.example.administrator.travel.ui.me.myappoint.wimeselect;

import android.app.Activity;

import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseLoadAndRefreshActivity;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.utils.MapUtils;

import java.util.List;

/**
 * Created by wangyang on 2016/10/9 0009.
 * 我的约伴 找人带推送选择页面
 */

public class MyWithMeSelectActivity extends BaseLoadAndRefreshActivity<MyWithMeSelectEvent,MyWithMeSelectBean,MyWithMeSelectBean.DataBean> {
    @Override
    protected BaseRecycleViewAdapter initAdapter(List<MyWithMeSelectBean.DataBean> mDatas) {
        return new  MyWithMeAdapter(mDatas,this);
    }

    @Override
    protected String initUrl() {
        return IVariable.WITH_ME_SELECT;
    }

    @Override
    protected Activity initDataAndRegisterEventBus() {
        return this;
    }

    @Override
    protected String initTitle() {
        return "已报名";
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        String id = getIntent().getStringExtra(IVariable.ID);
        builder.addTpId(id);
    }
}
