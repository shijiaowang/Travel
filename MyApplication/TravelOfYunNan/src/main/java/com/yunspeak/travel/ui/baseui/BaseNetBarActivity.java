package com.yunspeak.travel.ui.baseui;

import android.databinding.ViewDataBinding;

import com.yunspeak.travel.R;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.view.StatusView;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by wangyang on 2017/3/30.
 * 第一次进入页面就需要加载的activity 基类  统一管理状态页面
 */

public abstract class BaseNetBarActivity<E extends TravelsObject,T extends ViewDataBinding> extends BaseBarActivity<T> implements StatusView.OnTryGetDataListener, StatusView.OnErrorBackListener {

    protected StatusView statusView;


    @Override
    protected void initOptions() {
        statusView = (StatusView)findViewById(R.id.status_view);
        if (statusView!=null) {
            statusView.setOnTryGetDataListener(this);
            statusView.setOnErrorBackListener(this);
            statusView.showLoadingView(true);
            request();
        }
    }

    /**
     * 请求数据
     */
    private void request() {
        HttpClient.getInstance().getDataDealErrorAuto(statusView, getEInstance(), new Consumer<E>() {
            @Override
            public void accept(@NonNull E e) throws Exception {
                acceptData(e);
            }
        },initMap(),initUrl());
    }
    //获取的网络数据
    protected abstract void acceptData(E e);



    protected  Map<String,String> initMap(){
        MapUtils.Builder builder = MapUtils.Build().addKey().addUserId();
        return initParams(builder);
    }

    /**
     * 网络请求参数
     * @param builder
     * @return
     */
    protected  Map<String,String> initParams(MapUtils.Builder builder){
        return builder.end();
    }

    protected abstract String initUrl();


    /**
     * 实例化 E
     *
     * @return E class类型
     */
    protected Class<E> getEInstance() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void onTryGet() {
        request();
    }

    @Override
    public boolean onErrorBack(Throwable throwable) {
        ToastUtils.showToast(!NetworkUtils.isNetworkConnected()?getString(R.string.network_isnot_available):throwable.getMessage());
        return false;
    }
}
