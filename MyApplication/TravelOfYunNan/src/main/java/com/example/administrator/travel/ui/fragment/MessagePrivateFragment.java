package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.MessagePrivateAdapter;

/**
 * Created by Administrator on 2016/7/15 0015.
 * 消息中心私信
 */
public class MessagePrivateFragment extends BaseFragment {

    private ListView mLvPrivate;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_message_private;
    }

    @Override
    protected void initView() {
        mLvPrivate = (ListView) root.findViewById(R.id.lv_private);
    }

    @Override
    protected void initData() {
         mLvPrivate.setAdapter(new MessagePrivateAdapter(getContext(),null));
    }

    @Override
    protected void initListener() {

    }
}
