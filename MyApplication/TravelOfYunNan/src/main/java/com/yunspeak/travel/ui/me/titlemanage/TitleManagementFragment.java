package com.yunspeak.travel.ui.me.titlemanage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/9/7 0007.
 * 称号管理
 */
public class TitleManagementFragment extends BaseFragment {
    private static final String TITLE = "title";
    private static final String TITLE_TYPE = "title_type";
    @BindView(R.id.lv_title)
    ListView lvTitle;
    @BindView(R.id.ll_lv)
    LinearLayout llLv;
    @BindView(R.id.rl_empty)
    LinearLayout llEmpty;
    private List<OfficialLabelBean> mTitle;
    private String mTitleType;
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

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        if (mTitle == null){
            llLv.setVisibility(View.GONE);
        }else {
            llEmpty.setVisibility(View.GONE);
            titleManagementAdapter = new TitleManagementAdapter(getContext(), mTitle);
            lvTitle.setAdapter(titleManagementAdapter);
        }
    }

    @Subscribe
    public void onEvent(TitleDeleteEvent event) {
        if (event.getType().equals(mTitleType)) {
            for (OfficialLabelBean labelBean : mTitle) {
                if (labelBean.getId().equals(event.getId())) {
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
