package com.yunspeak.travel.ui.me.titlemanage;

import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.bean.UserLabelBean;

/**
 * Created by wangyang on 2016/9/27 0027.
 * 添加标签
 */

public class TitleChangeEvent extends TravelsObject {
    private UserLabelBean userLabelBean;
    private int Type;

    public TitleChangeEvent(int type, UserLabelBean userLabelBean) {
        Type = type;
        this.userLabelBean = userLabelBean;
    }

    public int getType() {

        return Type;
    }

    public void setType(int type) {
        Type = type;
    }


    public UserLabelBean getUserLabelBean() {

        return userLabelBean;
    }

    public void setUserLabelBean(UserLabelBean userLabelBean) {
        this.userLabelBean = userLabelBean;
    }
}
