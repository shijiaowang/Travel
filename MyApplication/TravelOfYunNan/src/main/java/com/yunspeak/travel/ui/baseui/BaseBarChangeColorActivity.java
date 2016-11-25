package com.yunspeak.travel.ui.baseui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CommonClickLikeBean;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.TravelReplyBean;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.MyCollectionDecoration;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/10/31 0031.
 */

public abstract class BaseBarChangeColorActivity<T extends HttpEvent,E extends ParentBean, F> extends BaseNetWorkActivity<T> implements OnLoadMoreListener, BaseRecycleViewAdapter.OnItemClickListener {
    @BindView(R.id.vs_content) protected ViewStub vsContent;
    @BindView(R.id.vs_footer) protected ViewStub vsFooter;
    @BindView(R.id.rc_common) protected RecyclerView mRvCommon;
    @BindView(R.id.swipe_target) protected NestedScrollView swipeTarget;
    @BindView(R.id.swipe_container) protected SwipeToLoadLayout mSwipe;
    protected List<F> mDatas;//从网络获取的数据
    protected BaseRecycleViewAdapter<F> mAdapter;//通用adapter

    public List<F> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<F> mDatas) {
        this.mDatas = mDatas;
    }
    @Override
    protected void initEvent() {
        mToolbar.setBackgroundColor(Color.argb(0, 92 , 208 , 194));
        View footView = inflater.inflate(R.layout.layout_google_footer, mSwipe, false);
        mSwipe.setSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
        mSwipe.setLoadMoreFooterView(footView);
        mSwipe.setOnLoadMoreListener(this);
        swipeTarget.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float absOffset=scrollY/300f;
                absOffset=absOffset>1?1:absOffset;
                mToolbar.setBackgroundColor(Color.argb((int) (absOffset * 255), 92 , 208, 194));

            }
        });
    }
    protected void changeMargin(int space,int top){
        mRvCommon.addItemDecoration(new MyCollectionDecoration(space,top));
    }


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_base_change_color;
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
     * 处理公共的网络参数请求
     *
     * @param type
     */
    protected void onLoad(int type) {
        MapUtils.Builder builder = MapUtils.Build().addKey().addUserId();
        childAdd(builder,type);
        Map<String, String> baseMap = builder.end();
        XEventUtils.getUseCommonBackJson(initUrl(),baseMap,type,new DetailCommonEvent());
    }
    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        int count=type==TYPE_REFRESH?0:getListSize(mDatas);
        builder.addPageSize().addCount(count);
    }

    @Override
    protected boolean isChangeBarColor() {
        return true;
    }

    @Override
    protected void onSuccess(T t) {
        ParentBean parentBean = null;
        try {
            if (isUserChild()) {//使用孩子的
                parentBean= GsonUtils.getObject(t.getResult(),useChildedBean());
            } else {
                parentBean = (E) GsonUtils.getObject(t.getResult(), getEType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<F> loadDatas;
        if (isChangeData()){
            loadDatas=childChangeData((E)parentBean,t);
        }else {
            loadDatas  = (List<F>) parentBean.getData();
        }

        dealRecycle(t, parentBean, loadDatas);




    }

    private void dealRecycle(T t, ParentBean parentBean, List<F> loadDatas) {
        if (parentBean==null || loadDatas==null || loadDatas.size()==0){
            mSwipe.setLoadingMore(false);
            mSwipe.setRefreshing(false);
            if (t.getType()==TYPE_REFRESH && mDatas!=null){
                mDatas.clear();
                mAdapter.notifiyData(mDatas);
            }
            return;
        }
        if (mAdapter == null) {
            mDatas = loadDatas;
            mAdapter = initAdapter(mDatas);
            mRvCommon.setHasFixedSize(true);
            mRvCommon.setAdapter(mAdapter);
            mAdapter.setItemClickListener(this);
            if (isGridLayoutManager()){
                StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                staggeredGridLayoutManager.setAutoMeasureEnabled(true);
                mRvCommon.setLayoutManager(staggeredGridLayoutManager);
            }else {
                LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setAutoMeasureEnabled(true);
                // linearLayoutManager.setStackFromEnd(true);//软键盘弹出上移
                mRvCommon.setLayoutManager(linearLayoutManager);
            }
            mRvCommon.setNestedScrollingEnabled(false);
        } else if (t.getType() == TYPE_LOAD) {
            mDatas.addAll(loadDatas);
            if (t.getCode()==2){
                ToastUtils.showToast("没有更多数据了");
            }

           // mAdapter.notifyItemRangeInserted(size,loadDatas.size());
            mAdapter.notifyDataSetChanged();
            mSwipe.setLoadingMore(false);
        } else if (t.getType() == TYPE_REFRESH) {
            mDatas = loadDatas;
            mAdapter.notifiyData(mDatas);
            mSwipe.setRefreshing(false);
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

    protected List<F> childChangeData(E parentBean, T t){
        return null;
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
    public void onItemClick(int position) {

    }
}
