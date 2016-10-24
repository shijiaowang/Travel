package com.yunspeak.travel.ui.baseui;

import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.fragment.LoadBaseFragment;
import com.yunspeak.travel.ui.view.refreshview.XListView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;

import org.xutils.common.util.DensityUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/30 0030.
 */

public abstract class LoadAndPullBaseFragment<T extends HttpEvent, E extends ParentBean, F> extends LoadBaseFragment<T> {

    @BindView(R.id.lv_circle_hot)public XListView mXListView;
    @BindView(R.id.vs_content)public ViewStub mVsContent;
    public int count = 0;

    public List<F> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<F> mDatas) {
        this.mDatas = mDatas;
    }

    protected List<F> mDatas;//从网络获取的数据
    protected TravelBaseAdapter adapter;
    @Override
    protected int initResLayout() {
        return R.layout.fragment_common_xlist;
    }

    @Override
    protected void initListener() {
        initXListView(mXListView,canPull(),canLoad());
        changeMarginTop(0);
        setXListViewChildSpace(0);
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

    protected void setXListViewChildSpace(int i) {
        mXListView.setDividerHeight(DensityUtil.dip2px(i));
    }

    protected void changeMarginTop(int top) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mXListView.getLayoutParams();
        layoutParams.topMargin= DensityUtil.dip2px(top);
        mXListView.setLayoutParams(layoutParams);
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


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        count = type == TYPE_REFRESH ? 0 : getListSize(mDatas);
        builder.addPageSize().addCount(count);
    }

    @Override
    public void onSuccess(T t) {
        LogUtils.e("loadFragment获取数据啦");
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
        loadEnd(mXListView);
    }
}
