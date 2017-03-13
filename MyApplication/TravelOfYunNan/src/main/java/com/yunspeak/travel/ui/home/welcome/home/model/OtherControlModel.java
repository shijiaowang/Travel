package com.yunspeak.travel.ui.home.welcome.home.model;

import android.content.Intent;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.aop.CheckNetwork;
import com.yunspeak.travel.ui.home.homesearch.HomeSearchActivity;
import com.yunspeak.travel.utils.ToastUtils;

/**
 * Created by wangyang on 2017/3/13.
 * 控制其他的一些点击效果
 */

public class OtherControlModel {
    public void onWordClick(View view){
        ToastUtils.showToast(view.getContext().getString(R.string.noly_support_yun_nan));
    }
    @CheckNetwork
    public void onSearchClick(View view){
        view.getContext().startActivity(new Intent(view.getContext(), HomeSearchActivity.class));
    }
}
