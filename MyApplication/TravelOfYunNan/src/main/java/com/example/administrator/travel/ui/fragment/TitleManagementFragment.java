package com.example.administrator.travel.ui.fragment;

import android.os.Bundle;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.TitleManagementAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2016/9/7 0007.
 * 称号管理
 */
public class TitleManagementFragment extends BaseFragment {
    private static final String TITLE = "title";
    private static final String TITLE_TYPE = "title_type";
    private String[] mTitle;
    private int mTitleType;
    private ListView mLvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getStringArray(TITLE);
            mTitleType = getArguments().getInt(TITLE_TYPE);
        }
        registerEventBus();

    }

    public static TitleManagementFragment newInstance(String[] title, int type) {
        TitleManagementFragment tabFragment = new TitleManagementFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(TITLE, title);
        bundle.putInt(TITLE_TYPE, type);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_title_management;
    }

    @Override
    protected void initView() {
        mLvTitle = (ListView) root.findViewById(R.id.lv_title);
    }

    @Override
    protected void initData() {
        mLvTitle.setAdapter(new TitleManagementAdapter(getContext(), null));
    }

    @Override
    protected void initListener() {

    }

    @Subscribe
    public void onEvent(boolean b) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterEventBus();
    }

    public void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    public void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
