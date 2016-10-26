package com.yunspeak.travel.ui.baseui;

import android.app.Activity;
import android.view.Menu;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import butterknife.OnClick;

/**
 * Created by wangyang on 2016/10/7 0007.
 *  需要网络加载的基类
 */

public abstract class BaseNetWorkActivity<T extends HttpEvent> extends BaseToolBarActivity {
    private boolean isFirstInflate =true;
    @Override
    protected void initOptions() {
        initEvent();
        mIvPageError.setOnClickListener(new ErrorPageClickListener());
        registerEventBus(this);
        if (isAutoLoad()) {
            setIsProgress(true, true);//初次加载隐藏其他布局
            onLoad(TYPE_REFRESH);
        }

    }

    protected boolean isAutoLoad() {
        return true;
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



    /**
     * 初始化监听
     */
    protected abstract void initEvent();

    @Subscribe
    public void onEvent(T t){
        if (!t.getClass().getSimpleName().equals(getTInstance().getClass().getSimpleName())){
            return;
        }
        setIsProgress(false);
        if (t.isSuccess()){
            if (isFirstInflate && t.getCode()==2){
                setIsEmpty(true);
                onSuccess(t);
                return;
            }
            try {
                isFirstInflate =false;
                needHideChildView.setVisibility(View.VISIBLE);//读取完毕，展示主页
                onSuccess(t);
            }catch (Exception e){
                e.printStackTrace();
                onFail(t);
            }
        }else {
            ToastUtils.showToast(t.getMessage());
            onFail(t);
        }

    }
    /**
     * 处理公共的网络参数请求
     *
     * @param type
     */
    protected void onLoad(int type) {
        MapUtils.Builder builder = MapUtils.Build().addKey(this).addUserId();
        childAdd(builder,type);
        Map<String, String> baseMap = builder.end();
        XEventUtils.getUseCommonBackJson(initUrl(),baseMap,type,getTInstance());
    }
    /**
     * 孩子添加参数
     * @param builder
     */
    protected abstract void childAdd(MapUtils.Builder builder,int type);
    /**
     * 网路请求地址
     */
    protected abstract String initUrl();




    /**
     * 读取成功
     * @param t
     */
    protected abstract void onSuccess(T t);

    /**
     * 错误处理
     * @param t
     */
    protected void onFail(T t) {
        if (isFirstInflate){
            setErrorPage(true);//如果初次加载显示错误页
        }
    }

    /**
     * 重置为初次加载
     */
   public void resetIsFirstInflate(){
       isFirstInflate=true;
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);

    }

    /**
     * 重新读取网络
     */
    @OnClick(R.id.page_error)
    protected void onReLoad() {
        onLoad(TYPE_REFRESH);
    }

    /**
     * 错误页面点击处理
     */
    class ErrorPageClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            setIsProgress(true,true);
            onLoad(TYPE_REFRESH);
        }
    }

}
