package com.example.administrator.travel.ui.me.titlemanage;

import android.os.Bundle;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyang on 2016/9/7 0007.
 * 称号管理
 */
public class TitleManagementFragment extends BaseFragment {
    private static final String TITLE = "title";
    private static final String TITLE_TYPE = "title_type";
    private List<OfficialLabelBean> mTitle;
    private String mTitleType;
    private ListView mLvTitle;
    private TitleManagementAdapter titleManagementAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = (List<OfficialLabelBean>) getArguments().getSerializable(TITLE);
            mTitleType = getArguments().getString(TITLE_TYPE);
        }
        registerEventBus();

    }

    public static TitleManagementFragment newInstance(List<OfficialLabelBean> title, String type) {
        TitleManagementFragment tabFragment = new TitleManagementFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TITLE, (Serializable) title);
        bundle.putString(TITLE_TYPE, type);
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
        if (mTitle==null)return;
        titleManagementAdapter = new TitleManagementAdapter(getContext(), mTitle);
        mLvTitle.setAdapter(titleManagementAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Subscribe
    public void onEvent(TitleDeleteEvent event) {
       if (event.getType().equals(mTitleType)){
           for (OfficialLabelBean labelBean:mTitle){
               if (labelBean.getId().equals(event.getId())){
                   labelBean.setStatus("1");//改成已获得，未佩戴
                   titleManagementAdapter.notifyDataSetChanged();
               }
           }
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
