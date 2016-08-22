package com.example.administrator.travel.ui.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.SearchUserAdapter;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class SearchUserFragment extends BaseFragment {

    private ListView mLvSearch;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_search_common;
    }

    @Override
    protected void initView() {
        mLvSearch = (ListView) root.findViewById(R.id.lv_search);
    }

    @Override
    protected void initData() {
       mLvSearch.setAdapter(new SearchUserAdapter(getContext(),null));
    }

    @Override
    protected void initListener() {

    }
}
