package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.travel.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 选择景点
 */
public class SelectDestinationHolder extends BaseHolder {
    @ViewInject(R.id.iv_spot)
    private ImageView mIvSpot;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.rb_star)
    private RatingBar mRbStart;
    @ViewInject(R.id.tv_add)
    private TextView mTvAdd;
    @ViewInject(R.id.tv_select)
    private TextView mTvSelect;
    public SelectDestinationHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_select_destination);
    }
}
