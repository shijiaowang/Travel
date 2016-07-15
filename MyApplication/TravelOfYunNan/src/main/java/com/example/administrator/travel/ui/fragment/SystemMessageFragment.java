package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.SystemMessage;
import com.example.administrator.travel.ui.adapter.SystemMessageAdapter;
import com.example.administrator.travel.utils.SystemMessageInitUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class SystemMessageFragment extends BaseFragment {



    private ListView mLvSystemMessage;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_system_message;
    }

    @Override
    protected void initView() {
        mLvSystemMessage = (ListView) root.findViewById(R.id.lv_system_message);
    }

    @Override
    protected void initData() {
        List<SystemMessage> systemMessages = SystemMessageInitUtils.initSystemData(getContext());
        mLvSystemMessage.setAdapter(new SystemMessageAdapter(getContext(),systemMessages));
    }

    /**
     * 初始化条目
     */
    private void initItem() {

    }

    @Override
    protected void initListener() {

    }
}
