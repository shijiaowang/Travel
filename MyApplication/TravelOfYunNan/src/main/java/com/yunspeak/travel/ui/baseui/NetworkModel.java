package com.yunspeak.travel.ui.baseui;

import com.yunspeak.travel.utils.MapUtils;

import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by wangyang on 2017/3/30.
 */

public abstract class NetworkModel{

    public Map<String, String> rootParams() {
        MapUtils.Builder builder = MapUtils.Build().addKey().addUserId();
        return initParams(builder);
    }

    protected abstract Map<String,String> initParams(MapUtils.Builder builder);

    protected abstract String url();

}
