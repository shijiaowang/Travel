package com.yunspeak.travel.ui.find.hotel.hotellist;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.HotelListBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;

import butterknife.BindView;

/**
 * Created by wangyang on 2017/2/27.
 */

public class HotelListHolder extends BaseRecycleViewHolder<HotelListBean> {
    @BindView(R.id.sdv_hotel_image)
    SimpleDraweeView sdvHotelImage;
    @BindView(R.id.tv_hotel_name)
    TextView tvHotelName;
    @BindView(R.id.tv_hotel_address)
    TextView tvHotelAddress;
    public HotelListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, HotelListBean data, Context mContext) {

    }
}
