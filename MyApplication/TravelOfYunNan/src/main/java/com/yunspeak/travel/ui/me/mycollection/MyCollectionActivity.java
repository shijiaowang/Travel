package com.yunspeak.travel.ui.me.mycollection;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.CollectionDetailActivity;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/3 0003.
 * 我的收藏
 */
public class MyCollectionActivity extends BaseToolBarActivity implements View.OnClickListener {
    public static final String COLLECTION_TYPE="collection_type";
    public static final  int COLLECTION_TEAM=1;
    public static final  int COLLECTION_TRAVELS=4;
    public static final  int COLLECTION_OTHER=6;
    public static final  int COLLECTION_DESTINATION=2;
    public static final  int COLLECTION_ACTIVE=3;
    public static final  int COLLECTION_POST=5;
    @BindView(R.id.ll_active) LinearLayout mLlActive;
    @BindView(R.id.ll_travels) LinearLayout mLlTravels;
    @BindView(R.id.ll_destination) LinearLayout mLlDestination;
    @BindView(R.id.ll_team) LinearLayout mLlTeam;
    @BindView(R.id.ll_post) LinearLayout mLlPost;
    @BindView(R.id.ll_other) LinearLayout mLlOther;
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this, CollectionDetailActivity.class);
        switch (v.getId()){
            case R.id.ll_other:
                intent.putExtra(COLLECTION_TYPE,COLLECTION_OTHER);
                startActivity(intent);
                break;
            case R.id.ll_destination:
                intent.putExtra(COLLECTION_TYPE,COLLECTION_DESTINATION);
                startActivity(intent);
                break;
            case R.id.ll_active:
                intent.putExtra(COLLECTION_TYPE,COLLECTION_ACTIVE);
                startActivity(intent);
                break;
            case R.id.ll_travels:
                intent.putExtra(COLLECTION_TYPE,COLLECTION_TRAVELS);
                startActivity(intent);
                break;
            case R.id.ll_post:
                intent.putExtra(COLLECTION_TYPE,COLLECTION_POST);
                startActivity(intent);
                break;
            case R.id.ll_team:
                intent.putExtra(COLLECTION_TYPE,COLLECTION_TEAM);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected int initLayoutRes() {
        return  R.layout.activity_my_collection;
    }

    @Override
    protected void initOptions() {
        mLlActive.setOnClickListener(this);
        mLlDestination.setOnClickListener(this);
        mLlOther.setOnClickListener(this);
        mLlPost.setOnClickListener(this);
        mLlTravels.setOnClickListener(this);
        mLlTeam.setOnClickListener(this);
    }

    @Override
    protected String initTitle() {
        return "我的收藏";
    }
}
