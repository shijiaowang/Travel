package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/12/5 0005.
 */

public class WxPaySuccessEvent extends TravelsObject {
    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
