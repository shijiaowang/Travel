package com.yunspeak.travel.ui.baseui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by wangyang on 2016/10/8 0008.
 * 智能处理错误数据
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder<T>> {
    public List<T> mDatas;
    public Context mContext;
    Set<Integer> errorList = new HashSet<>();
    protected RecyclerView recyclerView;
    protected Set<Integer> currentSets;
    protected boolean scrolled;

    public BaseRecycleViewAdapter(List<T> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    public void notifiyData(List list) {
        if (list == null) return;
        mDatas = list;
        this.notifyDataSetChanged();
    }

    public void setDatas(List list) {
        if (list == null || list == mDatas) return;
        mDatas = list;
    }

    public View inflateView(int res, ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(res, parent, false);
    }


    @Override
    public void onBindViewHolder(final BaseRecycleViewHolder holder, int position) {
        try {
            holder.itemView.setTag(mDatas.get(position));
            if (itemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = mDatas.indexOf(holder.itemView.getTag());
                        itemClickListener.onItemClick(tag);
                    }
                });
            }
            holder.childBindView(position, mDatas.get(position), mContext);
        } catch (Exception e) {
            e.printStackTrace();
            errorList.add(position);
            if (!scrolled) {
                clearErrorDateWhileShowFirstTime();
            }
            LogUtils.e("抛异常啦");
        }
    }

    /**
     * 如果一进来就报错
     */
    protected synchronized void clearErrorDateWhileShowFirstTime() {
        if (recyclerView != null) {
            scrolled = true;
            recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    onErrorDeal();
                    recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
    }


    /**
     * 处理错误数据
     */
    protected synchronized void onErrorDeal() {
        try {
            if (recyclerView == null || mDatas.size() == 0) return;
            remove();
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast("智能清理错误数据");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("处理错误数据时出错了");
        }
    }

    /**
     * 移除
     */
    protected synchronized void remove() {
        Iterator<Integer> iterator = errorList.iterator();
        List<T> tempList = new ArrayList<>();
        int itemCount = getItemCount() - 1;
        int min = itemCount;
        while (iterator.hasNext()) {
            int next = iterator.next();
            min = min > next ? next : min;
            tempList.add(mDatas.get(next));
        }
        if (mDatas.size() == tempList.size()) {//清除全部数据需要调用此方法，否者会抛出
            mDatas.clear();
            this.notifyDataSetChanged();//清理所有数据
        } else {
            this.notifyItemRangeRemoved(min, itemCount);
            mDatas.removeAll(tempList);
        }

        tempList.clear();
        errorList.clear();
    }

    /**
     * 处理错误数据
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && errorList.size() != 0) {
                    onErrorDeal();
                } else {
                    scrolled = true;
                }
            }
        });
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = null;
    }

    private OnItemClickListener itemClickListener;

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
