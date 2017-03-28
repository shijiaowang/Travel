package com.yunspeak.travel.ui.me.ordercenter.coupon;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.CouponDataBinding;
import com.yunspeak.travel.ui.baseui.BaseLoadAndRefreshFragment;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;

import com.yunspeak.travel.ui.me.ordercenter.coupon.model.Coupon;
import com.yunspeak.travel.ui.me.ordercenter.coupon.model.CouponModel;
import com.yunspeak.travel.ui.me.ordercenter.coupon.model.CouponRecycleModel;


/**
 * Created by wangyang on 2016/8/14.
 */
public class CouponFragment2 extends BaseLoadAndRefreshFragment<Coupon,CouponModel> {

    private CouponDataBinding couponDataBinding;
    private CouponRecycleModel couponRecycleModel;


    @Override
    protected BasePullAndRefreshModel<CouponModel> initModel() {
        couponRecycleModel = new CouponRecycleModel();
        return couponRecycleModel;
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        couponDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coupon2, container, false);
        return couponDataBinding.getRoot();
    }

    @Override
    protected void onReceive(Coupon datas) {
        couponDataBinding.setBase(couponRecycleModel);
        couponDataBinding.setManagerType(0);
    }
}
