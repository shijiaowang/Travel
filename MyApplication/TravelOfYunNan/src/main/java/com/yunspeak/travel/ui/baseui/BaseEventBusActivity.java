package com.yunspeak.travel.ui.baseui;

import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by wangyang on 2016/11/17 0017.
 */

public  abstract class BaseEventBusActivity<T extends HttpEvent> extends BaseToolBarActivity {

    @Override
    protected void initOptions() {
        registerEventBus(this);
        initEvent();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(this);
    }
    @Subscribe
    public void onEvent(T t){
        if (t.isSuccess()){
            onSuccess(t);
        }else {
            onFail(t);
        }
    }

    protected abstract void onFail(T t);

    protected abstract void onSuccess(T t);

    protected abstract void initEvent();
}
