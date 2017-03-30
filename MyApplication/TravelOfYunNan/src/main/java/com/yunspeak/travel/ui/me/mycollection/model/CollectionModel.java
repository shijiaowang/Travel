package com.yunspeak.travel.ui.me.mycollection.model;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.CollectionDetailActivity;

/**
 * Created by wangyang on 2017/3/30.
 */

public class CollectionModel {
    public static final String COLLECTION_TYPE="collection_type";
    public static final  int COLLECTION_TEAM=1;
    public static final  int COLLECTION_TRAVELS=4;
    public static final  int COLLECTION_OTHER=6;
    public static final  int COLLECTION_DESTINATION=2;
    public static final  int COLLECTION_ACTIVE=3;
    public static final  int COLLECTION_POST=5;

    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, CollectionDetailActivity.class);
        switch (v.getId()) {
            case R.id.ll_other:
                intent.putExtra(COLLECTION_TYPE, COLLECTION_OTHER);
                break;
            case R.id.ll_destination:
                intent.putExtra(COLLECTION_TYPE, COLLECTION_DESTINATION);
                break;
            case R.id.ll_active:
                intent.putExtra(COLLECTION_TYPE, COLLECTION_ACTIVE);
                break;
            case R.id.ll_travels:
                intent.putExtra(COLLECTION_TYPE, COLLECTION_TRAVELS);
                break;
            case R.id.ll_post:
                intent.putExtra(COLLECTION_TYPE, COLLECTION_POST);
                break;
            case R.id.ll_team:
                intent.putExtra(COLLECTION_TYPE, COLLECTION_TEAM);
                break;
            default:
                return;
        }
        context.startActivity(intent);
    }
}