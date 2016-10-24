package com.yunspeak.travel.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.find.travels.TravelsBean;
import com.yunspeak.travel.ui.view.SimpleViewPagerIndicator;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wnagyang on 2016/7/7 0007.
 */
public class TravelsAdapter extends BaseRecycleViewAdapter<HomeBean.DataBean.FindTravelBean> {


    public TravelsAdapter(List<HomeBean.DataBean.FindTravelBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<HomeBean.DataBean.FindTravelBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_home_travels, parent, false);
        return new TravelsHolder(inflate);
    }
}
