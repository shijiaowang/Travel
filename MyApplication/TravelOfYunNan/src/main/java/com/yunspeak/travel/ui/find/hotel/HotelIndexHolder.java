package com.yunspeak.travel.ui.find.hotel;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CityNameBean;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2017/2/22.
 */

public class HotelIndexHolder extends BaseRecycleViewHolder<CityNameBean> {

    private final HotelIndexAdapter.LineCallBack callBack;
    @BindView(R.id.tv_hotel_index_city_name)
    TextView tvHotelIndexCityName;
    @BindView(R.id.v_show_line)
    View vShowLine;

    public HotelIndexHolder(View itemView, HotelIndexAdapter.LineCallBack callBack) {
        super(itemView);
        this.callBack = callBack;
    }


    @Override
    public void childBindView(int position, CityNameBean data, Context mContext) {
        if (callBack.isNextGroup(position)) {
            vShowLine.setVisibility(View.GONE);
        }else {
            vShowLine.setVisibility(View.VISIBLE);
        }
        tvHotelIndexCityName.setText(data.getName());
    }
}
