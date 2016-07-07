package com.example.administrator.travel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public abstract class BaseFragment extends Fragment {
    public View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root=View.inflate(getActivity(),initLayoutRes(),null);
        return root;
    }
    public View getRoot(){
        return root;
    }

    protected abstract int initLayoutRes();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        initData();

    }

    protected abstract void initView();
    protected abstract void initData();

    protected abstract void initListener();

}
