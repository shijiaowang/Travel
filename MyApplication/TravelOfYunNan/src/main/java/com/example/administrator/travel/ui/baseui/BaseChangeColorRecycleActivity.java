package com.example.administrator.travel.ui.baseui;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IChildParent;
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

public abstract class BaseChangeColorRecycleActivity<T extends HttpEvent,E extends ParentBean,F extends IChildParent,G> extends BaseChangeBarColorActivity<T> implements OnLoadMoreListener {
   @BindView(R.id.swipe_target)protected RecyclerView mRvCommon;
   @BindView(R.id.stll_layout)protected SwipeToLoadLayout mSwipe;
    protected List<G> mDatas;//从网络获取的数据
    protected BaseRecycleViewAdapter<G> mAdapter;//通用adapter
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int initContent() {
        return R.layout.activity_common_recycleview;
    }

    @Override
    protected void initListener() {
        changeMargin(10);
        mRvCommon.addItemDecoration(new MyWitheMeDecoration(childDistance()));
        initChildListener();
        View footView = LayoutInflater.from(this).inflate(R.layout.layout_google_footer, mSwipe, false);
        mSwipe.setSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
        mSwipe.setLoadMoreFooterView(footView);
        mSwipe.setOnLoadMoreListener(this);

    }

    protected abstract void initChildListener();

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
        final F data = (F) parentBean.getData();
        List<G> boy = (List<G>) data.getBody();
        if (boy==null || boy.size()==0){
            if (t.getType()==TYPE_REFRESH){
                mSwipeContainer.setRefreshing(false);
            }
            return;}
        if (mAdapter == null) {
            mDatas = boy;
            mAdapter = initAdapter(mDatas);
            mRvCommon.setHasFixedSize(true);
            mRvCommon.setAdapter(mAdapter);
            linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setAutoMeasureEnabled(true);
            mRvCommon.setLayoutManager(linearLayoutManager);
            mRvCommon.setItemAnimator(new DefaultItemAnimator());
            mAdapter.setItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                        onChildItemClick(mDatas.get(position));
                }
            });
        } else if (t.getType() == TYPE_LOAD) {
            mDatas.addAll(boy);
             mSwipe.setLoadingMore(false);
            mAdapter.notifiyData(mDatas);
        } else if (t.getType() == TYPE_REFRESH) {
            mSwipeContainer.setRefreshing(false);
            mDatas = boy;
            mAdapter.notifiyData(mDatas);
        } else {
            doOtherSuccessData(t);
        }
    }



    /**
     * 孩子item点击
     * @param g
     */
    protected void onChildItemClick(G g) {

    }

    protected void doOtherSuccessData(T t) {

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

                    break;

        }
    }

    /**
     * 初始化adapter
     * @param mDatas
     * @return
     */
    protected abstract BaseRecycleViewAdapter initAdapter(List<G> mDatas);

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
}
