package com.yunspeak.travel.ui.me.identityauth.idauth;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseCutPhotoActivity;
import com.yunspeak.travel.ui.me.identityauth.AuthCommonEvent;
import com.yunspeak.travel.utils.MapUtils;

/**
 * Created by wangyang on 2016/12/5 0005.
 * 绑定驾驶证或者机动车驾驶证
 */

public class CarAuthActivity extends BaseCutPhotoActivity<AuthCommonEvent> {
    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void childDisplay(String url, String filename) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
    }

    @Override
    protected void onSuccess(AuthCommonEvent authCommonEvent) {

    }


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_driver_card;
    }

    @Override
    protected String initTitle() {
        return "驾驶证认证";
    }
}
