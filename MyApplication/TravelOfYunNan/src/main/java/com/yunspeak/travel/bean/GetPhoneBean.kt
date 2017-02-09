package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/10/6 0006.
 * 获取用户当前的手机号
 */
class GetPhoneBean : TravelsObject() {
    var data: DataBean? = null

    class DataBean {
        var id: String? = null
        var tel: String? = null
        var name: String? = null
    }
}


