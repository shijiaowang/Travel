package com.example.administrator.travel.ui.me.mycollection;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.me.collectiondetail.CollectionDetailActivity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by wangyang on 2016/8/3 0003.
 * 我的收藏
 */
public class MyCollectionActivity extends BarBaseActivity implements View.OnClickListener {
    public static final String COLLECTION_TYPE="collection_type";
    public static final  int COLLECTION_TEAM=1;
    public static final  int COLLECTION_TRAVELS=4;
    public static final  int COLLECTION_OTHER=6;
    public static final  int COLLECTION_DESTINATION=2;
    public static final  int COLLECTION_ACTIVE=3;
    public static final  int COLLECTION_POST=5;
    public static final int COLLECTION_CANCEL=7;
    @ViewInject(R.id.ll_active)
    private LinearLayout mLlActive;
    @ViewInject(R.id.ll_travels)
    private LinearLayout mLlTravels;
    @ViewInject(R.id.ll_destination)
    private LinearLayout mLlDestination;
    @ViewInject(R.id.ll_team)
    private LinearLayout mLlTeam;
    @ViewInject(R.id.ll_post)
    private LinearLayout mLlPost;
    @ViewInject(R.id.ll_other)
    private LinearLayout mLlOther;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initEvent() {
        mLlActive.setOnClickListener(this);
        mLlDestination.setOnClickListener(this);
        mLlOther.setOnClickListener(this);
        mLlPost.setOnClickListener(this);
        mLlTravels.setOnClickListener(this);
        mLlTeam.setOnClickListener(this);
    }



    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "我的收藏";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
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
}
