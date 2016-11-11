package com.yunspeak.travel.ui.baseui;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IChildParent;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.MyWitheMeDecoration;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.MyCollectionDecoration;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;

import org.xutils.common.util.DensityUtil;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/12 0012.
 */

public abstract class BaseChangeColorRecycleActivity<T extends HttpEvent, E extends ParentBean, F extends IChildParent, G> extends BaseChangeBarColorActivity<T> implements OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    protected RecyclerView mRvCommon;
    @BindView(R.id.stll_layout)
    protected SwipeToLoadLayout mSwipe;
    @BindView(R.id.empty)
    protected View mRlEmpty;
    protected List<G> mDatas;//从网络获取的数据
    protected BaseRecycleViewAdapter<G> mAdapter;//通用adapter
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int initContent() {
        return R.layout.activity_common_recycleview;
    }

    @Override
    protected void initListener() {
        mRlEmpty.setVisibility(View.GONE);
        initChildListener();
        View footView = LayoutInflater.from(this).inflate(R.layout.layout_google_footer, mSwipe, false);
        mSwipe.setSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
        mSwipe.setLoadMoreFooterView(footView);
        mSwipe.setOnLoadMoreListener(this);

    }

    protected abstract void initChildListener();


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        int count = type == TYPE_REFRESH ? 0 : getListSize(mDatas);
        builder.addPageSize();
        builder.addCount(count);
    }

    @Override
    protected void onSuccess(T t) {
        if (t.getCode()==2 && t.getType()==TYPE_REFRESH){
            mRlEmpty.setVisibility(View.VISIBLE);
            if (mDatas!=null && mAdapter!=null){
                mDatas.clear();
                mAdapter.notifyDataSetChanged();
            }
            return;
        }
        mRlEmpty.setVisibility(View.GONE);
        try {
            ParentBean parentBean = null;
            if (isUserChild(parentBean)) {//使用孩子的
            } else {
                parentBean = (E) GsonUtils.getObject(t.getResult(), getEType());
            }
             F data = (F) parentBean.getData();
            List<G> boy = (List<G>) data.getBody();
            if (mAdapter == null) {
                mDatas = boy;
                mAdapter = initAdapter(mDatas);
                mRvCommon.setAdapter(mAdapter);
                linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setAutoMeasureEnabled(true);
                mRvCommon.setLayoutManager(linearLayoutManager);
                mRvCommon.setItemAnimator(new DefaultItemAnimator());
            } else if (t.getType() == TYPE_LOAD) {
                mDatas.addAll(boy);
                mSwipe.setLoadingMore(false);
                mAdapter.notifiyData(mDatas);
            } else if (t.getType() == TYPE_REFRESH) {
                mSwipeContainer.setRefreshing(false);
                mDatas = boy;
                mAdapter.notifiyData(mDatas);
            }
        } catch (Exception e) {
            e.printStackTrace();
            onFail(t);
        }
    }



    protected void changeMargin(int space,int top) {
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

    /**
     * 是否是 瀑布流
     *
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
                mSwipeContainer.setRefreshing(false);
                break;

        }
    }

    /**
     * 初始化adapter
     *
     * @param mDatas
     * @return
     */
    protected abstract BaseRecycleViewAdapter initAdapter(List<G> mDatas);

    /**
     * 是否使用孩子另外设置设置的bean对象
     *
     * @param e
     * @return
     */
    protected boolean isUserChild(ParentBean e) {
        return false;
    }

    @Override
    public void onLoadMore() {
        onLoad(TYPE_LOAD);
    }
}
