package com.yunspeak.travel.ui.me.othercenter.useralbum;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class AppBarStateEvent extends TravelsObject {
    private boolean isClose;

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }
}
