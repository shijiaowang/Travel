package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class SystemMessageHolder extends BaseHolder {
    public SystemMessageHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_system_message);
    }
}
