package com.yunspeak.travel.ui.me.mycollection;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.CollectionBinding;
import com.yunspeak.travel.ui.baseui.BaseBarActivity;
import com.yunspeak.travel.ui.me.mycollection.model.CollectionModel;

/**
 * Created by wangyang on 2016/8/3 0003.
 * 我的收藏
 */
public class MyCollectionActivity extends BaseBarActivity<CollectionBinding>{
    @Override
    protected int initLayoutRes() {
        return  R.layout.activity_my_collection;
    }

    @Override
    protected void initOptions() {
      dataBinding.setCollectionModel(new CollectionModel());
    }

    @Override
    protected String initTitle() {
        return getString(R.string.my_collection);
    }
}
