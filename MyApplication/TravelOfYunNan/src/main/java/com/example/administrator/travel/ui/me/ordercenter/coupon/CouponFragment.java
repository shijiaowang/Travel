package com.example.administrator.travel.ui.me.ordercenter.coupon;

import android.support.v4.app.Fragment;
import android.widget.Button;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndPullBaseFragment;
import com.example.administrator.travel.ui.me.ordercenter.CouponBean;
import com.example.administrator.travel.ui.me.ordercenter.orders.confirmorders.CouponAdapter;
import com.example.administrator.travel.ui.view.refreshview.XListView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/14.
 */
public class CouponFragment extends LoadAndPullBaseFragment<CouponEvent, CouponDataBean, CouponBean> {
    @BindView(R.id.bt_add) Button mBtAdd;
    @BindView(R.id.lv_coupon) XListView mLvCoupon;

    @Override
    public XListView setXListView() {
        return mLvCoupon;
    }

    @Override
    protected int initResLayout() {
        return R.layout.fragment_coupon;
    }

    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public Class<? extends HttpEvent> registerEventType() {
        return CouponEvent.class;
    }


    @Override
    protected String initUrl() {
        return IVariable.MY_COUPON;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<CouponBean> httpData) {
        return new CouponAdapter(getContext(),httpData,true);
    }

}
