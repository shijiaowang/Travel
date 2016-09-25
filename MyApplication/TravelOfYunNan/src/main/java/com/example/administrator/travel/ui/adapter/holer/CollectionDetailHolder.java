package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;

import org.xutils.view.annotation.ViewInject;

import butterknife.BindView;

/**
 * Created by android on 2016/8/14.
 */
public class CollectionDetailHolder extends BaseHolder {
  @BindView(R.id.tv_delete) TextView mTvDelete;

    public CollectionDetailHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_collection_detail);
        return view;
    }
}
