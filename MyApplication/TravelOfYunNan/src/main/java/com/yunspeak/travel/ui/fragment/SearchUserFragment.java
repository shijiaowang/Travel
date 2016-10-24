package com.yunspeak.travel.ui.fragment;

import android.widget.ListView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.SearchUserAdapter;

/**
 * Created by  on 2016/8/22 0022.
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
