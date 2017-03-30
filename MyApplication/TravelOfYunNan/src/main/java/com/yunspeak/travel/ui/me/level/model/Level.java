package com.yunspeak.travel.ui.me.level.model;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.BR;

import java.util.List;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class Level extends TravelsObject {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private UserCurrentModel user;

        private List<LevelModel> level;

        private List<LevelDesModel> level_desc;

        public UserCurrentModel getUser() {
            return user;
        }

        public void setUser(UserCurrentModel user) {
            this.user = user;
        }

        public List<LevelModel> getLevel() {
            return level;
        }

        public void setLevel(List<LevelModel> level) {
            this.level = level;
        }

        public List<LevelDesModel> getLevel_desc() {
            return level_desc;
        }

        public void setLevel_desc(List<LevelDesModel> level_desc) {
            this.level_desc = level_desc;
        }
        @BindingAdapter("setLevel")
        public static void bindLevel(RecyclerView recyclerView,List<LevelModel> level){
            CommonRecycleViewAdapter<LevelModel> levelModelCommonRecycleViewAdapter = new CommonRecycleViewAdapter<>(level, BR.levelModel, R.layout.item_activity_level,false);
            recyclerView.setAdapter(levelModelCommonRecycleViewAdapter);

        }
        @BindingAdapter("setLevelDes")
        public static void bindLevelDes(RecyclerView recyclerView,List<LevelDesModel> levelDesc){
            CommonRecycleViewAdapter<LevelDesModel> levelModelCommonRecycleViewAdapter = new CommonRecycleViewAdapter<LevelDesModel>(levelDesc, BR.levelDesModel, R.layout.item_activity_level_express,false);
            recyclerView.setAdapter(levelModelCommonRecycleViewAdapter);

        }


        @BindingAdapter("recycle_fix_scroll")
        public static void bindRecycleInScroll(RecyclerView recyclerView,int type){
            LinearLayoutManager linearLayoutManager;
            if (type==0){
                linearLayoutManager =new LinearLayoutManager(recyclerView.getContext());
            }else {
               linearLayoutManager=new GridLayoutManager(recyclerView.getContext(),type);
            }
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            linearLayoutManager.setAutoMeasureEnabled(true);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }
}
