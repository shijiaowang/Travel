package com.example.administrator.travel.ui.baseui;


import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.global.ParentBean;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.me.messagecenter.MessageCommonEvent;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/9/29 0029.
 * 处理上拉加载，与下拉刷新的公共父类
 * 需要设置一个 刷新view
 */

public abstract class LoadAndRefreshBaseActivity<T extends HttpEvent, E extends ParentBean, F> extends LoadingBarBaseActivity<T> {
    private XListView mXListView;
    public int count = 0;

    public List<F> getHttpData() {
        return httpData;
    }

    public void setHttpData(List<F> httpData) {
        this.httpData = httpData;
    }

    private List<F> httpData;//从网络获取的数据

    public int getmState() {
        return mState;
    }

    public void setmState(int mState) {
        this.mState = mState;
    }

    private int mState = -1;
    private TravelBaseAdapter adapter;


    public abstract XListView setXListView();




    /**
     * 获取E的类型
     *
     * @return
     */
    public Class getEType() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) pt.getActualTypeArguments()[1];
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
     * 处理公共的网络参数请求
     *
     * @param type
     */
    @Override
    protected void onLoad(int type) {
        mState = type;
        count = type == TYPE_REFRESH ? 0 : getListSize(httpData);
        MapUtils.Builder builder = MapUtils.Build().addKey(this).addUserId().addPageSize().addCount(count);
        childAdd(builder);
        Map<String, String> end = builder.end();
        XEventUtils.getUseCommonBackJson(initUrl(), end, type, getTInstance());
    }

    /**
     * 孩子需要添加的参数
     *
     * @param builder
     */
    protected void childAdd(MapUtils.Builder builder) {

    }

    protected abstract String initUrl();

    @Override
    protected void onSuccess(T t) {
        if (mXListView == null) {
           mXListView=setXListView();
        }
        loadEnd(mXListView);
        ParentBean e;
        if (isUserChild()) {//使用孩子的
            e = GsonUtils.getObject(t.getResult(), useChildedBean());
        } else {
            e = (E) GsonUtils.getObject(t.getResult(), getEType());
        }

        if (adapter == null) {
            httpData = (List<F>) e.getData();
            adapter = initAdapter(httpData);
            if (mXListView == null) {
                mXListView=setXListView();
            }
            mXListView.setAdapter(adapter);
        } else if (mState == TYPE_LOAD) {
            httpData.addAll((List<F>) e.getData());
            adapter.notifyDataSetChanged();
        } else if (mState == TYPE_REFRESH) {
            httpData = (List<F>) e.getData();
            adapter.notifyDataSetChanged();
        }

    }

    protected Class<ParentBean> useChildedBean() {
        return getEType();
    }

    protected boolean isUserChild() {
        return false;
    }


    /**
     * 子类去初始化adapter
     *
     * @param httpData
     * @return
     */
    protected abstract TravelBaseAdapter initAdapter(List<F> httpData);

    /**
     * 子类 可选继承次方法去处理其他类型的数据
     *
     * @param t
     */
    private void dealOtherSuccessData(T t) {

    }

    @Override
    protected void onFail(HttpEvent event) {
        super.onFail(event);
        if (mXListView == null) {
            mXListView=setXListView();
        }
        loadEnd(mXListView);
    }
}
