package com.yunspeak.travel.ui.appoint.withme.withmedetail;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.view.FontsIconTextView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/11/7 0007.
 */

public class MyCreateAppointAdapter extends BaseRecycleViewAdapter<MyCreateAppointBean.DataBean> {


    public MyCreateAppointAdapter(List<MyCreateAppointBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<MyCreateAppointBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyCreateAppointHolder(inflateView(R.layout.item_dialog_appoint_list, parent));
    }
}
