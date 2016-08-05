package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class LinePlanHolder extends BaseHolder {

    private TextView mTvAdd;

    public LinePlanHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_line_plan);
        mTvAdd = FontsIconUtil.findIconFontsById(R.id.tv_add, mContext, view);
        return view;
    }
}
