package com.yunspeak.travel.ui.me.myappoint.historyorders;

import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.bean.MyAppointTogetherBean;
import com.yunspeak.travel.utils.MapUtils;

import java.util.List;

/**
 * Created by wangyang on 2016/10/26 0026.
 * 历史订单
 */

public class HistoryOrdersActivity extends BaseRecycleViewActivity<HistoryEvent,MyAppointTogetherBean,MyAppointTogetherBean.DataBean> {

    @Override
    protected String initUrl() {
        return IVariable.MY_APPOINT;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addType("3");
    }

    @Override
    protected String initTitle() {
        return "历史订单";
    }

    @Override
    protected boolean isUseChild() {
        return true;
    }

    @Override
    protected Class<? extends ParentBean> useChildedBean() {
        return MyAppointTogetherBean.class;
    }

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<MyAppointTogetherBean.DataBean> mDatas) {
        return new HistoryAdapter(mDatas,this);
    }
}
