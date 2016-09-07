package com.example.administrator.travel.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.ActiveActivity;
import com.example.administrator.travel.ui.activity.FindCommonActivity;
import com.example.administrator.travel.ui.activity.HotelActivity;
import com.example.administrator.travel.ui.activity.TravelsActivity;
import com.example.administrator.travel.ui.adapter.FindHotAdapter;
import com.example.administrator.travel.ui.adapter.FindRecommendAdapter;
import com.example.administrator.travel.ui.view.ToShowAllGridView;
import com.example.administrator.travel.ui.view.ToShowAllListView;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvIconAdd;
    private TextView mTvIconActive;
    private TextView mTvIconHotel;
    private TextView mTvIconFood;
    private TextView mTvIconTravels;
    private TextView mTvIconSearch;
    private ToShowAllGridView mGvRecommend;
    private ToShowAllListView mLvHot;
    private LinearLayout mLlActive;
    private LinearLayout mLlDeliciousFood;
    private LinearLayout mLlTravels;
    private LinearLayout mLlAdd;
    private LinearLayout mLlHotel;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        mGvRecommend = (ToShowAllGridView) root.findViewById(R.id.gv_recommend);
        mLlActive = (LinearLayout) root.findViewById(R.id.ll_active);
        mLlDeliciousFood = (LinearLayout) root.findViewById(R.id.ll_delicious_food);
        mLlHotel = (LinearLayout) root.findViewById(R.id.ll_hotel);
        mLlAdd = (LinearLayout) root.findViewById(R.id.ll_add);
        mLlTravels = (LinearLayout) root.findViewById(R.id.ll_travels);
        mLvHot = (ToShowAllListView) root.findViewById(R.id.lv_hot);//热门

    }

    @Override
    protected void initData() {
        mGvRecommend.setAdapter(new FindRecommendAdapter(getContext(), null));
        mLvHot.setAdapter(new FindHotAdapter(getContext(), null));
    }

    @Override
    protected void initListener() {
        mLlActive.setOnClickListener(this);
        mLlDeliciousFood.setOnClickListener(this);
        mLlTravels.setOnClickListener(this);
        mLlAdd.setOnClickListener(this);
        mLlHotel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_active:
                startActivity(new Intent(getContext(), ActiveActivity.class));
                break;
            case R.id.ll_delicious_food:
                Intent food = new Intent(getContext(), FindCommonActivity.class);
                food.putExtra(IVariable.TYPE,IVariable.TYPE_DELICIOUS);
                startActivity(food);
                break;
            case R.id.ll_travels:
                startActivity(new Intent(getContext(), TravelsActivity.class));
                break;
            case R.id.ll_add:
                Intent intent = new Intent(getContext(), FindCommonActivity.class);
                intent.putExtra(IVariable.TYPE,IVariable.TYPE_DESTINATION);
                startActivity(intent);
                break;
            case R.id.ll_hotel:
                startActivity(new Intent(getContext(), HotelActivity.class));
                break;
        }
    }
}
