package com.yunspeak.travel.ui.find.find.model;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.find.FindDecoration;

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

    }
    @BindingAdapter("bind:setRecycleAdapter")
    public static void setRecycleAdapter(RecyclerView recyclerView,List<RecommendModel> recommendModels){
        recyclerView.setAdapter(new CommonRecycleViewAdapter(recommendModels,BR.recommendModel,R.layout.item_fragment_find_recommend));
        GridLayoutManager gridLayoutManager=new GridLayoutManager(recyclerView.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new FindDecoration(5,5));
    }
}
