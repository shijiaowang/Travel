package com.yunspeak.travel.ui.me.myhobby;

import android.os.Bundle;
import android.widget.GridView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.fragment.BaseFragment;
import com.yunspeak.travel.ui.me.titlemanage.TitleChangeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyang on 2016/9/7 0007.
 * 称号管理
 */
public class MyHobbyFragment extends BaseFragment {
    private static final String TITLE = "title";
    private static final String TITLE_TYPE = "title_type";
    private List<UserLabelBean> mTitle;
    private String mTitleType;
    private GridView mGvTitle;
    private MyHobbyAdapter myHobbyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = (List<UserLabelBean>) getArguments().getSerializable(TITLE);
            mTitleType = getArguments().getString(TITLE_TYPE);
        }
        registerEventBus();

    }

    public static MyHobbyFragment newInstance(List<UserLabelBean> title, String type) {
        MyHobbyFragment tabFragment = new MyHobbyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TITLE, (Serializable) title);
        bundle.putString(TITLE_TYPE, type);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_hobby;
    }

    @Override
    protected void initView() {
        mGvTitle = (GridView) root.findViewById(R.id.gv_title);
    }

    @Override
    protected void initData() {
        if (mTitle==null)return;
        myHobbyAdapter = new MyHobbyAdapter(getContext(), mTitle);
        mGvTitle.setAdapter(myHobbyAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Subscribe
    public void onEvent(TitleChangeEvent event) {
       if (event.getUserLabelBean().getClassX().equals(mTitleType)){
           mTitle.add(event.getUserLabelBean());
           myHobbyAdapter.notifyDataSetChanged();
       }
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
