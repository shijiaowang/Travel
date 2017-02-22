package com.yunspeak.travel.ui.find.hotel;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CityNameBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2017/2/22.
 */

public class HotelIndexAdapter extends BaseRecycleViewAdapter<CityNameBean> {


    private  LineCallBack lineCallBack;

    public HotelIndexAdapter(List<CityNameBean> mDatas, Context mContext, LineCallBack lineCallBack) {
        super(mDatas, mContext);
        this.lineCallBack = lineCallBack;
    }

    @Override
    public BaseRecycleViewHolder<CityNameBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotelIndexHolder(inflateView(R.layout.item_activity_hotel_index,parent),lineCallBack);
    }
    public interface LineCallBack{
        boolean isNextGroup(int position);
    }
}
