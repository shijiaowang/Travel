package com.yunspeak.travel.ui.appoint.popwindow;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.find.findcommon.CityBean;
import java.util.List;

/**
 * Created by wangyang on 2016/12/9 0009.
 */

public class DeliciousTypeAdapter extends BaseRecycleViewAdapter<CityBean> {


    public DeliciousTypeAdapter(List<CityBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<CityBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeliciousTypeHolder(inflateView(R.layout.item_pop_delicious_type, parent));
    }
}
