package com.example.administrator.travel.ui.fragment;

import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.FindHotAdapter;
import com.example.administrator.travel.ui.adapter.FindRecommendAdapter;
import com.example.administrator.travel.ui.view.ToShowAllGridView;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class FindFragment extends BaseFragment {

    private TextView mTvIconAdd;
    private TextView mTvIconActive;
    private TextView mTvIconHotel;
    private TextView mTvIconFood;
    private TextView mTvIconTravels;
    private TextView mTvIconSearch;
    private ToShowAllGridView mGvRecommend;
    private ToShowAllListView mLvHot;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        mTvIconAdd = FontsIconUtil.findIconFontsById(R.id.tv_icon_add, getContext(), root);
        mTvIconActive = FontsIconUtil.findIconFontsById(R.id.tv_icon_active, getContext(), root);
        mTvIconHotel = FontsIconUtil.findIconFontsById(R.id.tv_icon_hotel, getContext(), root);
        mTvIconFood = FontsIconUtil.findIconFontsById(R.id.tv_icon_food, getContext(), root);
        mTvIconTravels = FontsIconUtil.findIconFontsById(R.id.tv_icon_travels, getContext(), root);
        mTvIconSearch = FontsIconUtil.findIconFontsById(R.id.tv_search, getContext(), root);

        mGvRecommend = (ToShowAllGridView) root.findViewById(R.id.gv_recommend);
        mLvHot = (ToShowAllListView) root.findViewById(R.id.lv_hot);//热门
    }

    @Override
    protected void initData() {
        mGvRecommend.setAdapter(new FindRecommendAdapter(getContext(), null));
        mLvHot.setAdapter(new FindHotAdapter(getContext(), null));
    }

    @Override
    protected void initListener() {

    }
}
