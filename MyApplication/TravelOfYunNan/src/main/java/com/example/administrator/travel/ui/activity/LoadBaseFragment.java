package com.example.administrator.travel.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.ui.view.LoadingPage;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public  abstract class LoadBaseFragment extends Fragment {
    public LoadingPage.ResultState currentState;
    private LoadingPage loadingPage;



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        loadingPage = new LoadingPage(getContext()) {
            @Override
            public View onCreateSuccessView() {
                return initView();
            }

            @Override
            public void onLoad() {
                 LoadBaseFragment.this.onLoad();
            }

            /**
             * 子类中修改
             * @return
             */
            @Override
            public ResultState changeState() {

                return LoadBaseFragment.this.getCurrentState();
            }
        };
        return loadingPage;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initContentView();
        initListener();
        initLoad();
    }

    protected abstract void initLoad();

    public void loadData(){
        if (loadingPage!=null){
            loadingPage.loadData();
        }
    }
    protected abstract void initContentView();

    protected abstract void initListener();

    protected abstract void onLoad();

    protected abstract View initView();

    public void setState(LoadingPage.ResultState state){
        currentState=state;
    }
    public LoadingPage.ResultState getCurrentState() {
        return currentState;
    }
}
