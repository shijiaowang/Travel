package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.SystemPrivate;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class MessagePrivateHolder extends BaseHolder<SystemPrivate> {
    public MessagePrivateHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(SystemPrivate datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_fragment_message_center_private_message);
        return inflate;
    }
}
