package com.yunspeak.travel.ui.find.hotel.hotellist;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.HotelListBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2017/2/27.
 */

public class HotelListAdapter extends BaseRecycleViewAdapter<HotelListBean> {


    public HotelListAdapter(List<HotelListBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<HotelListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotelListHolder(inflateView(R.layout.item_hotel_list, parent));
    }
}
