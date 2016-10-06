package com.example.administrator.travel.ui.baseui;

/**
 * Created by wangyang on 2016/10/4.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by hcc on 16/8/7 21:18
 * 100332338@qq.com
 * <p/>
 * 自定义RecylcerView上拉加载处理
 */
public abstract class LoadMoreListener extends RecyclerView.OnScrollListener
{



    private int previousTotal = 0;

    private boolean loading = true;

    int lastCompletelyVisiableItemPosition, visibleItemCount, totalItemCount;

    private int currentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public LoadMoreListener(LinearLayoutManager linearLayoutManager)
    {

        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
    {

        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        lastCompletelyVisiableItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

        if (loading)
        {
            if (totalItemCount > previousTotal)
            {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading
                && (visibleItemCount > 0)
                && (lastCompletelyVisiableItemPosition >= totalItemCount - 1))
        {
            onLoadMore(mLinearLayoutManager.getChildCount());
            loading = true;
        }
    }

    /**
     * 传入孩子个数
     * @param childCount
     */
    public abstract void onLoadMore(int childCount);
}