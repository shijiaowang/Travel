package com.yunspeak.travel.ui.me.messagecenter.appointmessage;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 */
public class AppointMessageAdapter extends BaseRecycleViewAdapter {
    public static final int TYPE_AITE = 0;
    public static final int TYPE_DISCUSS = 1;
    public static final int TYPE_ZAMBIA = 2;//赞
    public static final int TYPE_APPOINT = 3;
    private final int messageType;

    public AppointMessageAdapter(List mDatas, Context mContext,int messageType) {
        super(mDatas, mContext);
        this.messageType = messageType;
    }


    @Override
    public BaseRecycleViewHolder<Object> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppointMessageHolder(inflateView(R.layout.item_activity_appoint_message,parent),messageType);
    }
}
