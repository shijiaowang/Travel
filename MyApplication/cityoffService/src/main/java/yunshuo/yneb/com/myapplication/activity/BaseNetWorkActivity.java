package yunshuo.yneb.com.myapplication.activity;


import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import butterknife.OnClick;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.other.event.HttpEvent;
import yunshuo.yneb.com.myapplication.other.utils.MapUtils;
import yunshuo.yneb.com.myapplication.other.utils.ToastUtils;
import yunshuo.yneb.com.myapplication.other.utils.XEventUtils;



/**
 * Created by wangyang on 2016/10/7 0007.
 *  需要网络加载的基类
 */

public abstract class BaseNetWorkActivity<T extends HttpEvent> extends BaseToolBarActivity {
    private boolean isFirstInflate =true;
    protected Callback.Cancelable useCommonBackJson;

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
        useCommonBackJson=null;//同于取消
        try {
            if (!t.getClass().getSimpleName().equals(getTInstance().getClass().getSimpleName())){
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            onFail(t);
        }

        if (t.isSuccess()){
            if (isFirstInflate && t.getCode()==2){
                setIsEmpty();
                onSuccess(t);
                return;
            }
            try {
                isFirstInflate =false;
                if ( t.getCode()==2){
                    ToastUtils.showToast("没有更多数据了");
                }
                onSuccess(t);
                setSuccess();
            }catch (Exception e){
                e.printStackTrace();
                setErrorPage(true);
            }

        }else {
            ToastUtils.showToast(t.getMessage());
            onFail(t);
        }
       setIsProgress(false);
    }
    /**
     * 处理公共的网络参数请求
     *
     * @param type
     */
    protected void onLoad(int type) {
        if (type==TYPE_REFRESH){
            isFirstInflate=true;
        }
        MapUtils.Builder builder = MapUtils.Build().addKey().addUserId();
        childAdd(builder,type);
        Map<String, String> baseMap = builder.end();
        useCommonBackJson = XEventUtils.getUseCommonBackJson(initUrl(), baseMap, changeType(type), getTInstance());
    }

    /**
     * 是否改变状态码
     * @param type
     * @return
     */
    protected int changeType(int type) {
        return type;
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
