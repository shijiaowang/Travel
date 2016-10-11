package com.example.administrator.travel.ui.baseui;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.ParentBean;
import com.example.administrator.travel.ui.me.myappoint.withmeselect.MyWitheMeDecoration;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;

import org.xutils.common.util.DensityUtil;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by wangyang on 2016/10/8 0008.
 * 刷新以及加载更多通用baseactivity
 */

public  abstract class BaseRecycleViewActivity<T extends HttpEvent,E extends ParentBean, F> extends BaseNetWorkActivity<T> implements OnLoadMoreListener, OnRefreshListener {
    protected List<F> mDatas;//从网络获取的数据
    protected BaseRecycleViewAdapter<F> mAdapter;//通用adapter
    protected RecyclerView mRvCommon;
    protected SwipeToLoadLayout mSwipe;
    protected ViewStub mVsContent;

    public List<F> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<F> mDatas) {
        this.mDatas = mDatas;
    }


    @Override
    protected int initLayoutRes() {
        return R.layout.acitivity_common_load;
    }

    @Override
    protected void initEvent() {
        mRvCommon = (RecyclerView) findViewById(R.id.swipe_target);
        mVsContent= (ViewStub) findViewById(R.id.vs_content);
        changeMargin(10);
        mRvCommon.addItemDecoration(new MyWitheMeDecoration(childDistance()));
        mSwipe = (SwipeToLoadLayout) findViewById(R.id.swipe_container);
        View headerView = inflater.inflate(R.layout.layout_google_header, mSwipe, false);
        View footView = inflater.inflate(R.layout.layout_google_footer, mSwipe, false);
        mSwipe.setSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
        mSwipe.setRefreshHeaderView(headerView);
        mSwipe.setLoadMoreFooterView(footView);
        mSwipe.setOnRefreshListener(this);
        mSwipe.setOnLoadMoreListener(this);
    }

    private int childDistance() {
        return 6;
    }

    protected void changeMargin(int top){
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mRvCommon.getLayoutParams();
        layoutParams.topMargin= DensityUtil.dip2px(top);
        mRvCommon.setLayoutParams(layoutParams);
    }



    /**
     * 获取泛型E的具体类型
     *
     * @return
     */
    public Class getEType() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) pt.getActualTypeArguments()[1];
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        int count=type==TYPE_REFRESH?0:getListSize(mDatas);
        builder.addPageSize().addCount(count).end();
    }

    @Override
    protected void onSuccess(T t) {
        ParentBean parentBean = null;
        if (isUserChild(parentBean)) {//使用孩子的
        } else {
            parentBean = (E) GsonUtils.getObject(t.getResult(), getEType());
        }
        List<F> loadDatas = (List<F>) parentBean.getData();
        if (parentBean==null || loadDatas==null || loadDatas.size()==0)return;
        if (mAdapter == null) {
            mDatas = loadDatas;
            mAdapter = initAdapter(mDatas);
            mRvCommon.setHasFixedSize(true);
            mRvCommon.setAdapter(mAdapter);
            if (isGridLayoutManager()){
                StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
                staggeredGridLayoutManager.setAutoMeasureEnabled(true);
                mRvCommon.setLayoutManager(staggeredGridLayoutManager);
            }else {
                LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setAutoMeasureEnabled(true);
                mRvCommon.setLayoutManager(linearLayoutManager);
            }
            mRvCommon.setItemAnimator(new DefaultItemAnimator());
        } else if (t.getType() == TYPE_LOAD) {
            mDatas.addAll(loadDatas);
            LogUtils.e("开始添加了，现在一共有"+mDatas.size());
            mSwipe.setLoadingMore(false);
            mAdapter.notifiyData(mDatas);
        } else if (t.getType() == TYPE_REFRESH) {
            mDatas = loadDatas;
            mAdapter.notifiyData(mDatas);
            mSwipe.setRefreshing(false);
        } else {
            doOtherSuccessData(t);
        }

    }

    /**
     * 是否是 瀑布流
     * @return
     */
    protected boolean isGridLayoutManager() {
        return false;
    }

    @Override
    protected void onFail(T t) {
        super.onFail(t);
        if (mSwipe!=null) {
            switch (t.getType()) {
                case TYPE_REFRESH:
                    mSwipe.setRefreshing(false);
                    break;
                case TYPE_LOAD:
                    mSwipe.setLoadingMore(false);
                    break;
            }
        }
    }

    /**
     * 处理其他逻辑
     * @param t
     */
    protected  void doOtherSuccessData(T t){

    }

    /**
     * 初始化adapter
     * @param mDatas
     * @return
     */
    protected abstract BaseRecycleViewAdapter initAdapter(List<F> mDatas);

    /**
     * 是否使用孩子另外设置设置的bean对象
     * @return
     * @param e
     */
    protected boolean isUserChild(ParentBean e) {
        return false;
    }


    @Override
    public void onLoadMore() {
        onLoad(TYPE_LOAD);
    }

    @Override
    public void onRefresh() {
        onLoad(TYPE_REFRESH);
    }
}
