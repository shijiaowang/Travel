package com.example.administrator.travel.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.CollectionDetailActivity;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/3 0003.
 * 我的收藏
 */
public class MyCollectionFragment extends BaseFragment implements View.OnClickListener {
    public static final String COLLECTION_TYPE="collection_type";
    public static final  int COLLECTION_TEAM=0;
    public static final  int COLLECTION_TRAVELS=1;
    public static final  int COLLECTION_OTHER=2;
    public static final  int COLLECTION_DESTINATION=3;
    public static final  int COLLECTION_ACTIVE=4;
    public static final  int COLLECTION_POST=5;
    private LinearLayout mLlActive;
    private LinearLayout mLlTravels;
    private LinearLayout mLlDestination;
    private LinearLayout mLlTeam;
    private LinearLayout mLlPost;
    private LinearLayout mLlOther;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_my_collection;
    }

    @Override
    protected void initView() {
        mLlActive= ((LinearLayout) root.findViewById(R.id.ll_active));
        mLlTeam= ((LinearLayout) root.findViewById(R.id.ll_team));
        mLlTravels= ((LinearLayout) root.findViewById(R.id.ll_travels));
        mLlOther= ((LinearLayout) root.findViewById(R.id.ll_other));
        mLlDestination= ((LinearLayout) root.findViewById(R.id.ll_destination));
        mLlPost= ((LinearLayout) root.findViewById(R.id.ll_post));

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
         mLlActive.setOnClickListener(this);
         mLlDestination.setOnClickListener(this);
         mLlOther.setOnClickListener(this);
         mLlPost.setOnClickListener(this);
         mLlTravels.setOnClickListener(this);
         mLlTeam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getContext(), CollectionDetailActivity.class);
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
