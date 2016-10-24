package com.yunspeak.travel.ui.me.ordercenter.coupon;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.me.ordercenter.CouponBean;

import java.util.List;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class CouponDataBean implements ParentBean {



    private int code;
    private String message;

    private List<CouponBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CouponBean> getData() {
        return data;
    }

    public void setData(List<CouponBean> data) {
        this.data = data;
    }


}
