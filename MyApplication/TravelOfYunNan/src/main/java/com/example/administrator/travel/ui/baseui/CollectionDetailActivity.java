package com.example.administrator.travel.ui.baseui;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.CollectionDetailAdapter;
import com.example.administrator.travel.ui.fragment.MyCollectionFragment;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by android on 2016/8/14.
 *收藏详情
 */
public class CollectionDetailActivity extends BarBaseActivity {
   @ViewInject(R.id.lv_collection_detail)
    private ListView mLvCollectionDetail;
    private String name;



    private void setName(int collectionType) {
        name = "收藏详情";
        switch (collectionType){
            case MyCollectionFragment.COLLECTION_ACTIVE:
                name ="收藏的活动";
                break;
            case MyCollectionFragment.COLLECTION_TRAVELS:
                name ="收藏的游记";
                break;
            case MyCollectionFragment.COLLECTION_DESTINATION:
                name ="收藏的目的地";
                break;
            case MyCollectionFragment.COLLECTION_OTHER:
                name ="其他";
                break;
            case MyCollectionFragment.COLLECTION_TEAM:
                name ="收藏的队伍";
                break;
            case MyCollectionFragment.COLLECTION_POST:
                name ="收藏的帖子";
                break;
        }
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_collection_deatil;
    }

    @Override
    protected void initEvent() {
        init();
    }

    private void init() {
        int collectionType = getIntent().getIntExtra(MyCollectionFragment.COLLECTION_TYPE, -1);
        setName(collectionType);
    }

    @Override
    protected void initViewData() {
       mLvCollectionDetail.setAdapter(new CollectionDetailAdapter(this,null));
        changeTitle(name);
    }

    @Override
    protected String setTitleName() {
        return "收藏详情";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
