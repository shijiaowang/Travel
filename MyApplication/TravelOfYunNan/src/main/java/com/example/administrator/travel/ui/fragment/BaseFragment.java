package com.example.administrator.travel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.utils.LogUtils;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public abstract class BaseFragment extends Fragment {
    public View root;
    private boolean isPrepared=false;
    private boolean isFirst=true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root=View.inflate(getActivity(),initLayoutRes(),null);
    }

    public View getRoot(){
        return root;
    }

    protected abstract int initLayoutRes();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared=true;
    }
    /**
     * Fragment数据的懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isPrepared&& isFirst) {
            LogUtils.e(this.getClass().getSimpleName());
            isFirst=false;
            initView();
            initListener();
            initData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected abstract void initView();
    protected abstract void initData();

    protected abstract void initListener();

}
