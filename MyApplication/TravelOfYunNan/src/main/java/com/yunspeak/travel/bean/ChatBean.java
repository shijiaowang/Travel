package com.yunspeak.travel.bean;

import com.hyphenate.easeui.domain.UserInfo;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/10/20 0020.
 */

public class ChatBean extends TravelsObject {



    private List<UserInfo> data;


    public List<UserInfo> getData() {
        return data;
    }

    public void setData(List<UserInfo> data) {
        this.data = data;
    }


}