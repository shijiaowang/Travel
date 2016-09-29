package com.example.administrator.travel.ui.me.messagecenter.appointmessage;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

/**
 * Created by wangyang on 2016/8/26 0026.
 */
public class AppointMessageHolder extends BaseHolder {
    public AppointMessageHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_appoint_message);
    }
}
