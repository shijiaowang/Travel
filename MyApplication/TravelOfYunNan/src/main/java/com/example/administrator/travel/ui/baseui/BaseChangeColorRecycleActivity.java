package com.example.administrator.travel.ui.baseui;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

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
import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/12 0012.
 */

public abstract class BaseChangeColorRecycleActivity<T extends HttpEvent,E extends ParentBean,F> extends BaseChangeBarColorActivity<T> {
   @BindView(R.id.rv_recycle) RecyclerView mRvCommon;
    protected List<F> mDatas;//从网络获取的数据
    protected LoadMoreRecycleViewAdapter<F> mAdapter;//通用adapter

    @Override
    protected int initContent() {
        return R.layout.activity_common_recycleview;
    }

    @Override
    protected void initListener() {
        changeMargin(10);
        mRvCommon.addItemDecoration(new MyWitheMeDecoration(childDistance()));

    }

    /**
     * 默认子view距离
     * @return
     */
    private int childDistance() {
        return 6;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        int count=type==TYPE_REFRESH?0:getListSize(mDatas);
        builder.addPageSize();
        builder.addCount(count);
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
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setAutoMeasureEnabled(true);
            mRvCommon.setLayoutManager(linearLayoutManager);
            mRvCommon.addOnScrollListener(new LoadMoreListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int childCount) {
                    mAdapter.startLoading();
                    onLoad(TYPE_LOAD);
                }
            });
            mRvCommon.setItemAnimator(new DefaultItemAnimator());
        } else if (t.getType() == TYPE_LOAD) {
            mDatas.addAll(loadDatas);
            LogUtils.e("开始添加了，现在一共有"+mDatas.size());
            mAdapter.endLoading();
            mAdapter.setList(mDatas);
        } else if (t.getType() == TYPE_REFRESH) {
            mDatas = loadDatas;
            mAdapter.setList(mDatas);
            mSwipeContainer.setRefreshing(false);
        } else {
            doOtherSuccessData(t);
        }
    }

    private void doOtherSuccessData(T t) {

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
            switch (t.getType()) {
                case TYPE_REFRESH:
                    mSwipeContainer.setRefreshing(false);
                    break;
                case TYPE_LOAD:
                    mAdapter.endLoading();
                    break;

        }
    }

    /**
     * 初始化adapter
     * @param mDatas
     * @return
     */
    protected abstract LoadMoreRecycleViewAdapter initAdapter(List<F> mDatas);

    /**
     * 是否使用孩子另外设置设置的bean对象
     * @return
     * @param e
     */
    protected boolean isUserChild(ParentBean e) {
        return false;
    }

}
