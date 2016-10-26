package com.yunspeak.travel.ui.baseui;


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
import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.MyWitheMeDecoration;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.MyCollectionDecoration;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.xutils.common.util.DensityUtil;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
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
    protected ViewStub mVsFooter;

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
        mVsFooter = (ViewStub) findViewById(R.id.vs_footer);
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

    protected void changeMargin(int space,int top){
        mRvCommon.addItemDecoration(new MyCollectionDecoration(space,top));
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
        if (isUserChild()) {//使用孩子的
            parentBean=GsonUtils.getObject(t.getResult(),useChildedBean());
        } else {
            parentBean = (E) GsonUtils.getObject(t.getResult(), getEType());
        }
        List<F> loadDatas=new ArrayList<>();
        if (isChangeData()){
            childChangeData(loadDatas,(E)parentBean,t);
        }else {
            loadDatas  = (List<F>) parentBean.getData();
        }
        if (parentBean==null || loadDatas==null || loadDatas.size()==0){
            mSwipe.setLoadingMore(false);
            mSwipe.setRefreshing(false);
            return;
        }
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
            if (t.getCode()==2){
                ToastUtils.showToast("没有更多消息了");
            }
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
    protected Class<? extends ParentBean> useChildedBean() {
        return getEType();
    }
    /**
     * 是否修改获得的数据
     * @return
     */
    protected boolean isChangeData() {
        return false;
    }

    protected  void childChangeData(List<F> loadDatas, E parentBean, T t){

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

     */
    protected boolean isUserChild() {
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
