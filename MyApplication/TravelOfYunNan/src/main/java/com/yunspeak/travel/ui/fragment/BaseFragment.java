package com.yunspeak.travel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/7/7 0007.
 */
public abstract class BaseFragment extends Fragment {
    public View root;
    private boolean isInflate=false;
    private boolean isPrepare=false;


    public View getRoot() {
        return root;
    }

    protected abstract int initLayoutRes();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = View.inflate(getContext(), initLayoutRes(), null);
        ButterKnife.bind(this, root);
        initView();
        initListener();
        isPrepare = true;
        return root;
    }



    /**
     * Fragment数据的懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && !isInflate && isPrepare) {
            initData();
            isInflate = true;
        }
    }


    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

}
