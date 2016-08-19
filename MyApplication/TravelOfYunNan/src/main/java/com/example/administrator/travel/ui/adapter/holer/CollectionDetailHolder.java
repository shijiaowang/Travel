package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by android on 2016/8/14.
 */
public class CollectionDetailHolder extends BaseHolder {
  @ViewInject(R.id.tv_delete)
    private TextView mTvDelete;

    public CollectionDetailHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_collection_detail);
        return view;
    }
}
