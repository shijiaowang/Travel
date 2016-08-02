package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class BulletinBoardHolder extends BaseHolder {
    public BulletinBoardHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_bulletin_board, null);
        return inflate;
    }
}
