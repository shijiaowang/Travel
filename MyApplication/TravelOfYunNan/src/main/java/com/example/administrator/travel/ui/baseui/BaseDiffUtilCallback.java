package com.example.administrator.travel.ui.baseui;

import android.support.v7.util.DiffUtil;

import com.example.administrator.travel.global.DataParentBean;

import java.util.List;

/**
 * Created by wangyang on 2016/10/8 0008.
 */

public  abstract class BaseDiffUtilCallback<T extends DataParentBean> extends DiffUtil.Callback {
    protected   List<T> oldList;
    protected  List<T> newList;

    public BaseDiffUtilCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }
}
