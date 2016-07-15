package com.example.administrator.travel.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.MessageCenterActivity;
import com.example.administrator.travel.ui.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private FlowLayout mFlLabel;//称号
    private List<String> titles = new ArrayList<>();
    private LayoutInflater inflater;
    private TextView mTvMessageCenter;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        mFlLabel = (FlowLayout) root.findViewById(R.id.fl_label);
        inflater = LayoutInflater.from(getActivity());
        mTvMessageCenter = (TextView) root.findViewById(R.id.tv_message_center);
    }

    @Override
    protected void initData() {
        titles.clear();
        titles.add("我叫小红帽");
        titles.add("绝对老司机");
        titles.add("新手");
        titles.add("老司机引领时尚");
        titles.add("我叫小红帽");
        titles.add("绝对老司机");
        titles.add("新手");
        titles.add("老司机引领时尚");
        for (int i = 0; i < titles.size(); i++) {
            TextView mTvTitle = (TextView) inflater.inflate(R.layout.item_fragment_me_title, mFlLabel, false);
            mTvTitle.setText(titles.get(i));
            mFlLabel.addView(mTvTitle);
        }


    }

    @Override
    protected void initListener() {
        mTvMessageCenter.setOnClickListener(this);//消息中心
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_message_center:
                getContext().startActivity(new Intent(getContext(), MessageCenterActivity.class));
                break;
        }
    }
}
