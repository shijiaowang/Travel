package com.example.administrator.travel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.ChosenAdapter;
import com.example.administrator.travel.ui.adapter.HotSpotsAdapter;
import com.example.administrator.travel.ui.view.ToShowAllGridView;

/**
 * Created by Administrator on 2016/7/6 0006.
 * 主页Fragment
 */
public class HomeFragment extends Fragment {

    private View root;//跟布局
    private ToShowAllGridView mGvChosen;//精选
    private ChosenAdapter chosenAdapter;
    private RecyclerView mRvHotSpots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = View.inflate(getActivity(), R.layout.fragment_home, null);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mGvChosen = (ToShowAllGridView) root.findViewById(R.id.gv_chosen);
        mRvHotSpots = (RecyclerView) root.findViewById(R.id.rv_hot_spots);
    }

    private void initListener() {

    }

    private void initData() {
        chosenAdapter = new ChosenAdapter(getActivity(), null);
        mGvChosen.setAdapter(chosenAdapter);

        mRvHotSpots.setAdapter(new HotSpotsAdapter(getActivity(),null));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRvHotSpots.setLayoutManager(manager);
        mRvHotSpots.setItemAnimator(new DefaultItemAnimator());
    }


}
