package com.example.administrator.travel.ui.find;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Line;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.ActiveActivity;
import com.example.administrator.travel.ui.baseui.FindCommonActivity;
import com.example.administrator.travel.ui.baseui.HotelActivity;
import com.example.administrator.travel.ui.find.travels.TravelsActivity;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.me.myappoint.withmeselect.MyWitheMeDecoration;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/19 0019.
 * 发现主页
 */
public class FindFragment extends LoadBaseFragment<FindEvent> implements View.OnClickListener {
    @BindView(R.id.vp_find) ViewPager vpFind;
    @BindView(R.id.ll_add) LinearLayout mLlAdd;
    @BindView(R.id.ll_hotel) LinearLayout mLlHotel;
    @BindView(R.id.ll_travels) LinearLayout mLlTravels;
    @BindView(R.id.ll_delicious_food) LinearLayout mLlDeliciousFood;
    @BindView(R.id.ll_active) LinearLayout mLlActive;
    @BindView(R.id.rv_recommend) RecyclerView mRvRecommend;
    @BindView(R.id.rv_hot) RecyclerView mRvHot;

    @Override
    protected int initResLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void onSuccess(FindEvent findEvent) {
        FindBean findBean = GsonUtils.getObject(findEvent.getResult(), FindBean.class);
        List<FindBean.DataBean.RecommendBean> recommend = findBean.getData().getRecommend();
        FindRecommendAdapter findRecommendAdapter=new FindRecommendAdapter(recommend,getContext());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        mRvRecommend.setAdapter(findRecommendAdapter);
        mRvRecommend.setLayoutManager(gridLayoutManager);
        mRvRecommend.addItemDecoration(new FindDecoration(5,5));
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        mRvRecommend.setHasFixedSize(true);
        mRvRecommend.setNestedScrollingEnabled(false);
        FindHotAdapter findHotAdapter=new FindHotAdapter(findBean.getData().getHot(),getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        mRvHot.setAdapter(findHotAdapter);
        mRvHot.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        mRvHot.setHasFixedSize(true);
        mRvHot.setNestedScrollingEnabled(false);
        mRvHot.addItemDecoration(new MyWitheMeDecoration(10));

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
    protected String initUrl() {
        return IVariable.FIND_HOME;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_active:
                startActivity(new Intent(getContext(), ActiveActivity.class));
                break;
            case R.id.ll_delicious_food:
                Intent food = new Intent(getContext(), FindCommonActivity.class);
                food.putExtra(IVariable.TYPE, IVariable.TYPE_DELICIOUS);
                startActivity(food);
                break;
            case R.id.ll_travels:
                startActivity(new Intent(getContext(), TravelsActivity.class));
                break;
            case R.id.ll_add:
                Intent intent = new Intent(getContext(), FindCommonActivity.class);
                intent.putExtra(IVariable.TYPE, IVariable.TYPE_DESTINATION);
                startActivity(intent);
                break;
            case R.id.ll_hotel:
                startActivity(new Intent(getContext(), HotelActivity.class));
                break;
        }
    }


}
