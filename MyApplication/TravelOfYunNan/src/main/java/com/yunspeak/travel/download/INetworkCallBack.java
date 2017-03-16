package com.yunspeak.travel.download;

import android.support.annotation.NonNull;

import com.yunspeak.travel.global.TravelsObject;

/**
 * 特殊页面需要处理的状态码等等
 * @param <T>
 */
public interface INetworkCallBack<T extends TravelsObject> {
    /**
     * Consume the given value.
     * @param t the value
     * @throws Exception on error
     */
    void accept(@NonNull T t) throws Exception;
    void error(Throwable throwable);
}