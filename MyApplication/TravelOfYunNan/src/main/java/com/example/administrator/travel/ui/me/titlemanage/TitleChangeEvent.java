package com.example.administrator.travel.ui.me.titlemanage;

/**
 * Created by wangyang on 2016/9/27 0027.
 * 添加标签
 */

public class TitleChangeEvent {
    private TitleManagementBean.DataBean.UserLabelBean userLabelBean;
    private int Type;

    public TitleChangeEvent(int type, TitleManagementBean.DataBean.UserLabelBean userLabelBean) {
        Type = type;
        this.userLabelBean = userLabelBean;
    }

    public int getType() {

        return Type;
    }

    public void setType(int type) {
        Type = type;
    }


    public TitleManagementBean.DataBean.UserLabelBean getUserLabelBean() {

        return userLabelBean;
    }

    public void setUserLabelBean(TitleManagementBean.DataBean.UserLabelBean userLabelBean) {
        this.userLabelBean = userLabelBean;
    }
}
