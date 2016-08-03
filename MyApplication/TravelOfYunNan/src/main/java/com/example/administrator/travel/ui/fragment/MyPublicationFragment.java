package com.example.administrator.travel.ui.fragment;

import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/8/3 0003.
 * 我的收藏
 */
public class MyPublicationFragment extends BaseFragment {
    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_my_publication;
    }

    @Override
    protected void initView() {
        TextView mTvAdd = FontsIconUtil.findIconFontsById(R.id.tv_add, getContext(), root);
        TextView mTvActive = FontsIconUtil.findIconFontsById(R.id.tv_active, getContext(), root);
        TextView mTvTravels = FontsIconUtil.findIconFontsById(R.id.tv_travels, getContext(), root);
        TextView mTvOther = FontsIconUtil.findIconFontsById(R.id.tv_other, getContext(), root);
        TextView mTvPost = FontsIconUtil.findIconFontsById(R.id.tv_post, getContext(), root);
        TextView mTvTeam = FontsIconUtil.findIconFontsById(R.id.tv_team, getContext(), root);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
