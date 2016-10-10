package com.example.administrator.travel.ui.me.othercenter.userinfo;


import android.support.v4.app.Fragment;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.view.LoadingPage;

/**
 * Created by wangyang on 2016/10/4.
 * 个人信息
 */

public class UserInfoFragment extends LoadBaseFragment<UserInfoEvent> {


    @Override
    protected int initResLayout() {
        return R.layout.activity_other_information;
    }

    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public Class<? extends HttpEvent> registerEventType() {
        return UserInfoEvent.class;
    }

    @Override
    public void onSuccess(UserInfoEvent userInfoEvent) {

    }

    @Override
    protected void initListener() {
            setState(LoadingPage.ResultState.STATE_SUCCESS);
    }

    @Override
    protected void onLoad(int type) {

    }
}
