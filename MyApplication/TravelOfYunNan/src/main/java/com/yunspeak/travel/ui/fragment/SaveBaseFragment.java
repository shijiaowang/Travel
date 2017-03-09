package com.yunspeak.travel.ui.fragment;

import com.yunspeak.travel.download.DownloadClient;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.view.StatusView;
import com.yunspeak.travel.utils.ToastUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by wangyang on 2017/3/9.
 * 需要缓存数据的页面集成此 fragment
 * 目前只有主页的几个页面需要把数据存储在数据库
 */

public abstract class SaveBaseFragment<T extends TravelsObject> extends BaseLoadingFragment{

    @Override
    protected void childLoad() {
        DownloadClient.getInstance().getDataDealErrorAuto(statusView,getTInstance(), new Consumer<T>() {
            @Override
            public void accept(@NonNull T data) throws Exception {
                System.out.println(data);
            }
        },initParams(),initUrl());
    }

    protected abstract Map<String,String> initParams();

    protected abstract String initUrl();

    @Override
    protected void initOptions() {
        super.initOptions();
        statusView.setOnErrorBackListener(new StatusView.OnErrorBackListener() {
            @Override
            public void onErrorBack(Throwable throwable) {
                ToastUtils.showToast(throwable.getMessage());
                //查看数据库是否存有数据，有就显示缓存，没有就显示错误页面
            }
        });
    }
    /**
     * 实例化 T
     *
     * @return T的 class类型
     */
    public Class<T> getTInstance() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) pt.getActualTypeArguments()[0];
    }
}
