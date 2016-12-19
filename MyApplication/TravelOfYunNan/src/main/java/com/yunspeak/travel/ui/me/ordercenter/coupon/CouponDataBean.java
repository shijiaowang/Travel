package com.yunspeak.travel.ui.me.ordercenter.coupon;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.me.ordercenter.CouponBean;

import java.util.List;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class CouponDataBean extends TravelsObject implements ParentBean {





    private List<CouponBean> data;


    public List<CouponBean> getData() {
        return data;
    }

    public void setData(List<CouponBean> data) {
        this.data = data;
    }


}
