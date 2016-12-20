package yunshuo.yneb.com.myapplication.activity;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.LogUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import yunshuo.yneb.com.myapplication.other.event.HttpEvent;

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
    protected void onDestroy() {
        super.onDestroy();
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
                LogUtil.e("解析数据出异常了"+t.getMessage());
                onFail(t);
            }
        }catch (Exception e){
            onFail(t);
        }
    }
    protected  void onFail(T t){
        setIsProgress(false);
    }

    protected abstract void onSuccess(T t);

    protected abstract void initEvent();
}
