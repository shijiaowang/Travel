package com.example.administrator.travel.ui.baseui;


import android.app.Activity;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.ParentBean;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;

import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by wangyang on 2016/9/29 0029.
 * 处理上拉加载，与下拉刷新的公共父类
 * 需要设置一个 刷新view
 */

public abstract class BaseXListViewActivity<T extends HttpEvent, E extends ParentBean, F> extends BaseNetWorkActivity<T> implements XListView.IXListViewListener {
    protected XListView mXListView;
    public int count = 0;
    protected ViewStub mVsHeader;

    public List<F> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<F> mDatas) {
        this.mDatas = mDatas;
    }
    protected List<F> mDatas;//从网络获取的数据
    protected TravelBaseAdapter adapter;





    /**
     * 获取E的类型
     *
     * @return
     */
    public Class getEType() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) pt.getActualTypeArguments()[1];
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_xlistview_common;
    }


    @Override
    protected void initEvent() {
        mXListView= (XListView) findViewById(R.id.lv_common);
        initXListView(mXListView,canPull(),canLoad());
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    childItemClick(position-1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mVsHeader = (ViewStub) findViewById(R.id.vs_header);

    }

    /**
     * 设置离头部的距离
     * @param top
     */
    protected void setMarginTop(int top) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mXListView.getLayoutParams();
        layoutParams.topMargin= DensityUtil.dip2px(top);
        mXListView.setLayoutParams(layoutParams);
    }

    protected void setChildSpace(int dp){
        if (dp<0)return;
        mXListView.setDividerHeight(DensityUtil.dip2px(dp));
    }

    /**
     *点击事件简易 减去刷新
     * @param i
     */
    protected void childItemClick(int i) {

    }

    private boolean canPull() {
        return true;
    }

    private boolean canLoad() {
        return true;
    }


    /**
     * 处理公共的网络参数请求
     *
     * @param type
     */
    @Override
    protected void onLoad(int type) {
        count = type == TYPE_REFRESH ? 0 : getListSize(mDatas);
        MapUtils.Builder builder = MapUtils.Build().addKey(this).addUserId().addPageSize().addCount(count);
        childAdd(builder);
        Map<String, String> end = builder.end();
        XEventUtils.getUseCommonBackJson(initUrl(), end, type, getTInstance());
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    /**
     * 孩子需要添加的参数
     *
     * @param builder
     */
    protected void childAdd(MapUtils.Builder builder) {

    }


    @Override
    protected void onSuccess(T t) {
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
            mDatas = (List<F>) e.getData();
            adapter.notifyData(mDatas);
        } else {
            doOtherSuccessData(t);
        }

    }

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
    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
    /**
     * 初始化XlistView
     * @param listView
     * @param canPull 是否能下拉刷新
     * @param canLoadMore 是否可以LoadMore
     */
    protected void initXListView(XListView listView, boolean canPull, boolean canLoadMore){
        listView.setPullLoadEnable(canLoadMore);
        listView.setPullRefreshEnable(canPull);
        listView.setXListViewListener(this);
        listView.setRefreshTime(getTime());
    }

    @Override
    public void onRefresh() {
         onLoad(TYPE_REFRESH);
    }

    @Override
    public void onLoadMore() {
        onLoad(TYPE_LOAD);
    }
    protected void loadEnd(XListView xListView) {
        if (xListView==null)return;
        xListView.stopLoadMore();
        xListView.stopRefresh();
        xListView.setRefreshTime(getTime());
    }
}
