package com.yunspeak.travel.ui.find.find.model;

import android.databinding.BindingAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yunspeak.travel.BR;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.OfficialLabelBean;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.adapter.CommonPagerCursorAdapter;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.find.FindDecoration;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.TopDecoration;
import java.util.List;


/**
 * Created by wangyang on 2016/10/15 0015.
 * 发现页面对象
 */

public class Find extends TravelsObject {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private List<RecommendModel> hot;

        private List<RecommendModel> recommend;

        private List<RecommendModel> banner;

        public List<RecommendModel> getHot() {
            return hot;
        }

        public void setHot(List<RecommendModel> hot) {
            this.hot = hot;
        }

        public List<RecommendModel> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<RecommendModel> recommend) {
            this.recommend = recommend;
        }

        public List<RecommendModel> getBanner() {
            return banner;
        }

        public void setBanner(List<RecommendModel> banner) {
            this.banner = banner;
        }

        @BindingAdapter("bind:setRecycleAdapter")
        public static void setRecycleAdapter(RecyclerView recyclerView,List<RecommendModel> recommendModels){
            if (checkAndSet(recyclerView, recommendModels)) return;
            recyclerView.setAdapter(new CommonRecycleViewAdapter<>(recommendModels,BR.recommendModel,R.layout.item_fragment_find_recommend,false));
            GridLayoutManager gridLayoutManager=new GridLayoutManager(recyclerView.getContext(),2);
            gridLayoutManager.setSmoothScrollbarEnabled(true);
            gridLayoutManager.setAutoMeasureEnabled(true);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.addItemDecoration(new FindDecoration(5,5));
        }

        private static boolean checkAndSet(RecyclerView recyclerView, List<RecommendModel> recommendModels) {
            if (recyclerView.getLayoutManager()!=null){
                CommonRecycleViewAdapter<RecommendModel> adapter = (CommonRecycleViewAdapter<RecommendModel>) recyclerView.getAdapter();
                adapter.resetDatas(recommendModels,false);
                return true;
            }
            return false;
        }

        @BindingAdapter("bind:setLinearRecycle")
        public static void setRecycleAdapter2(RecyclerView recycleAdapter2,List<RecommendModel> recommendModels){
            if (checkAndSet(recycleAdapter2, recommendModels)) return;
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(recycleAdapter2.getContext());
            recycleAdapter2.setAdapter(new CommonRecycleViewAdapter<>(recommendModels,BR.recommendModel,R.layout.item_fragment_find_hot,false));
            recycleAdapter2.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            linearLayoutManager.setAutoMeasureEnabled(true);
            recycleAdapter2.setHasFixedSize(true);
            recycleAdapter2.addItemDecoration(new TopDecoration(10));
            recycleAdapter2.setNestedScrollingEnabled(false);
        }
        @BindingAdapter("bind:setPagerAdapter")
        public static void setViewPagerAdapter(ViewPager viewPager,List<RecommendModel> recommendModels){
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter!=null){
                CommonPagerCursorAdapter<RecommendModel> commonPagerCursorAdapter = (CommonPagerCursorAdapter<RecommendModel>) adapter;
                commonPagerCursorAdapter.reset(recommendModels);
                return;
            }
            viewPager.setAdapter(new CommonPagerCursorAdapter<>(recommendModels,R.layout.fragment_find_pager,BR.recommendModel));
        }
    }

}
