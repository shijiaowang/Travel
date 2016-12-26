package com.yunspeak.travel.ui.find;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.FindBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.find.active.ActiveActivity;
import com.yunspeak.travel.ui.find.findcommon.FindCommonActivity;
import com.yunspeak.travel.ui.find.hotel.HotelActivity;
import com.yunspeak.travel.ui.find.travels.TravelsActivity;
import com.yunspeak.travel.ui.fragment.LoadBaseFragment;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.TopDecoration;
import com.yunspeak.travel.ui.view.PagerCursorView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;

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
    @BindView(R.id.pager_cursor) PagerCursorView pagerCursorView;
    private List<FindBean.DataBean.RecommendBean> banner;

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
        mRvHot.addItemDecoration(new TopDecoration(10));
        if (banner==null) {
            banner = findBean.getData().getBanner();
            pagerCursorView.setViewPager(vpFind, banner.size(), true,this);
            vpFind.setAdapter(new HomePagerAdapter(banner));
            vpFind.setOffscreenPageLimit(banner.size());
        }

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
                FindCommonActivity.start(getContext(),FindCommonActivity.DELICIOUS_NORMAL, 0);
                break;
            case R.id.ll_travels:
                startActivity(new Intent(getContext(), TravelsActivity.class));
                break;
            case R.id.ll_add:
                FindCommonActivity.start(getContext(),FindCommonActivity.DESTINATION_NORMAL, 0);
                break;
            case R.id.ll_hotel:
                startActivity(new Intent(getContext(), HotelActivity.class));
                break;
        }
    }
    class HomePagerAdapter extends PagerCursorView.CursorPagerAdapter {
        private List<FindBean.DataBean.RecommendBean> data;

        public HomePagerAdapter(List<FindBean.DataBean.RecommendBean> data) {
            super(data);
            this.data = data;
        }
        @Override
        public Object inflateView(ViewGroup container, int position) {
                SimpleDraweeView imageView = new SimpleDraweeView(getContext());
                imageView.setTag(data.get(position));
                imageView.setOnClickListener(new MyOnClickListener(getContext(), (FindBean.DataBean.RecommendBean) imageView.getTag()));
                FrescoUtils.displayNormal(imageView, data.get(position).getLogo_img(), 640, 360, R.drawable.normal_2_1);
                container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {

            container.removeView(((SimpleDraweeView) object));
        }

    }
}
