package com.yunspeak.travel.ui.baseui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wangyang on 2017/3/30.
 * RecycleView是滑动到了底部
 */


import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by kongqw on 2016/2/14.
 */
public abstract class OnRecyclerViewScrollListener extends RecyclerView.OnScrollListener{

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    /**
     * layoutManager的类型（枚举）
     */
    protected LAYOUT_MANAGER_TYPE layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        /*
      当前滑动的状态
     */
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if ((visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {
            onBottom();
        }
    }



    public abstract void onBottom();

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
