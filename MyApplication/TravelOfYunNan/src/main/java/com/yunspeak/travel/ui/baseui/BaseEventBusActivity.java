package com.yunspeak.travel.ui.baseui;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.utils.ToastUtils;
import org.greenrobot.eventbus.Subscribe;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
/**
 * Created by wangyang on 2016/11/17 0017.
 */

public  abstract class BaseEventBusActivity<T extends HttpEvent> extends BaseToolBarActivity {

    @Override
    protected void initOptions() {
        initEvent();
        registerEventBus(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(this);
    }
    /**
     * 实例化 T
     *
     * @return
     */
    public T getTInstance() {

        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class c = (Class<T>) pt.getActualTypeArguments()[0];
            Constructor constructor = c.getConstructor();
            T e = (T) constructor.newInstance();
            return e;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Subscribe
    public void onEvent(T t) {
        try {
            if (!t.getClass().getSimpleName().equals(getTInstance().getClass().getSimpleName())) {
                return;
            }
            if (t.isSuccess()) {
                onSuccess(t);
            } else {
                ToastUtils.showToast(t.getMessage());
                onFail(t);
            }
        }catch (Exception e){
            onFail(t);
        }
    }
    protected abstract void onFail(T t);

    protected abstract void onSuccess(T t);

    protected abstract void initEvent();
}
