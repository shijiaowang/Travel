package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.MyCollectionAdapter;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class MyPublicationFragment extends BaseFragment {

    private ListView mLvCollection;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_my_publication;
    }

    @Override
    protected void initView() {
        mLvCollection = (ListView) root.findViewById(R.id.lv_collection);
    }

    @Override
    protected void initData() {
      mLvCollection.setAdapter(new MyCollectionAdapter(getContext(),null));
    }

    @Override
    protected void initListener() {

    }
}
