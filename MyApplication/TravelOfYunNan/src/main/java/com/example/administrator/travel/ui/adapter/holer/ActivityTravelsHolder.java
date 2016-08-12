package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Travels;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class ActivityTravelsHolder extends BaseHolder<Travels> {
    public ActivityTravelsHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Travels datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate =inflateView(R.layout.item_activity_travels);
        TextView mTvEye = FontsIconUtil.findIconFontsById(R.id.tv_eye, mContext, inflate);
        TextView mTvTime = FontsIconUtil.findIconFontsById(R.id.tv_time, mContext, inflate);
        return inflate;
    }
}
