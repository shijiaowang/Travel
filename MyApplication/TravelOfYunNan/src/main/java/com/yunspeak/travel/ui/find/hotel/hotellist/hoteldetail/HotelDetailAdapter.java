package com.yunspeak.travel.ui.find.hotel.hotellist.hoteldetail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.HotelDetailBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2017/2/28.
 */

public class HotelDetailAdapter extends BaseRecycleViewAdapter<HotelDetailBean> {

    public HotelDetailAdapter(List<HotelDetailBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<HotelDetailBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotelDetailHoler(inflateView(R.layout.item_hotel_detail_room, parent));
    }

    class HotelDetailHoler extends BaseRecycleViewHolder<HotelDetailBean> {
        @BindView(R.id.sdv_hotel_room_bg)
        SimpleDraweeView sdvHotelRoomBg;
        @BindView(R.id.tv_hotel_room_name)
        TextView tvHotelRoomName;
        @BindView(R.id.tv_hotel_room_size)
        TextView tvHotelRoomSize;
        @BindView(R.id.tv_hotel_room_price)
        TextView tvHotelRoomPrice;
        @BindView(R.id.tv_hotel_room_select)
        TextView tvHotelRoomSelect;
        public HotelDetailHoler(View itemView) {
            super(itemView);
        }

        @Override
        public void childBindView(int position, HotelDetailBean data, Context mContext) {

        }
    }
}
