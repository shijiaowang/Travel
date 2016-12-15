package com.yunspeak.travel.ui.me.mycollection.collectiondetail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/8/14.
 */
public class CollectionDetailAdapter extends BaseRecycleViewAdapter<Object> {

    private  String tid;

    public CollectionDetailAdapter(Context mContext, List<Object> mDatas, String tid) {
        super( mDatas,mContext);
        this.tid = tid;
    }



    @Override
    public BaseRecycleViewHolder<Object> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflateView(R.layout.item_activity_collection_detail, parent);
        return new CollectionDetailHolder(view,tid);
    }
}
