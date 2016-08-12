package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class MyCollectionHolder extends BaseHolder {
    public MyCollectionHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_fragment_my_collection);
        TextView mTvDelete = FontsIconUtil.findIconFontsById(R.id.tv_delete, mContext, inflate);
        return inflate;
    }
}
