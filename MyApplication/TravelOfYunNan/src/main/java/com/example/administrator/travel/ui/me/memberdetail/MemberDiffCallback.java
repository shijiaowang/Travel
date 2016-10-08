package com.example.administrator.travel.ui.me.memberdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.example.administrator.travel.global.DataParentBean;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseDiffUtilCallback;

import java.util.List;

/**
 * Created by wangyang on 2016/10/8 0008.
 * RecycleView计算数据集变化，并刷新动画，避免之前白光
 */

class MemberDiffCallback extends BaseDiffUtilCallback<MemberDetailBean.DataBean.JoinBean> {


    public MemberDiffCallback(List<MemberDetailBean.DataBean.JoinBean> oldList, List<MemberDetailBean.DataBean.JoinBean> newList) {
        super(oldList, newList);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        MemberDetailBean.DataBean.JoinBean newItem = newList.get(newItemPosition);
        MemberDetailBean.DataBean.JoinBean oldItem = oldList.get(oldItemPosition);
        if (newItem.getState().equals(oldItem.getState())){//通过用户是否被邀请的状态判断
            return true;
        }
        return false;

    }
    //找出其中的不同
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        MemberDetailBean.DataBean.JoinBean newItem = newList.get(newItemPosition);
        MemberDetailBean.DataBean.JoinBean oldItem = oldList.get(oldItemPosition);
        Bundle diffBundle = new Bundle();
        if (!newItem.getState().equals(oldItem.getState())) {
            diffBundle.putString(IVariable.DATA, newItem.getState());
        }
        return diffBundle;
    }

}
