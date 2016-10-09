package com.example.administrator.travel.ui.baseui;

import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.ParentBean;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/9/30 0030.
 */

public abstract class LoadAndPullBaseFragment<T extends HttpEvent, E extends ParentBean, F> extends LoadBaseFragment<T> {

    private XListView mXListView;
    public int count = 0;

    public List<F> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<F> mDatas) {
        this.mDatas = mDatas;
    }

    protected List<F> mDatas;//从网络获取的数据
    protected TravelBaseAdapter adapter;


    public abstract XListView setXListView();

    @Override
    protected void initListener() {
        mXListView= setXListView();
        initXListView(mXListView,canPull(),canLoad());
        if (mXListView!=null){
            mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        childOnItemClick(parent,view,position-1,id);//除去头布局
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    public void childOnItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private boolean canPull() {
        return true;
    }

    private boolean canLoad() {
        return true;
    }

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
        } catch (java.lang.InstantiationException e) {
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
        count = type == TYPE_REFRESH ? 0 : getListSize(mDatas);
        MapUtils.Builder builder = MapUtils.Build().addKey(getContext()).addUserId().addPageSize().addCount(count);
        childAdd(builder);
        Map<String, String> end = builder.end();
        XEventUtils.getUseCommonBackJson(initUrl(), end, type, getTInstance());
        LogUtils.e("发起了一次请求"+count);
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
    public void onSuccess(T t) {
        LogUtils.e("loadFragment获取数据啦");
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
            mDatas = (List<F>) e.getData();
            adapter = initAdapter(mDatas);
            if (mXListView == null) {
                mXListView=setXListView();
            }
            mXListView.setAdapter(adapter);
        } else if (t.getType() == TYPE_LOAD) {
            mDatas.addAll((List<F>) e.getData());
            adapter.notifyDataSetChanged();
        } else if (t.getType() == TYPE_REFRESH) {
            mDatas.clear();
            mDatas = (List<F>) e.getData();
            adapter.notifyData(mDatas);
        }else {
            doOtherSuccessData(t);
        }

    }

    /**
     * 处理其他成功的消息
     * @param t
     */
    protected void doOtherSuccessData(T t) {

    }


    protected Class<? extends ParentBean> useChildedBean() {
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



    @Override
    protected void onFail(T event) {
        super.onFail(event);
        if (mXListView == null) {
            mXListView=setXListView();
        }
        loadEnd(mXListView);
    }
}
