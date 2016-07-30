package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.HotSpots;
import com.example.administrator.travel.utils.FontsIconUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6 0006.
 * 游记详情行走路线
 */
public class TravelsAddAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<HotSpots> mDatas;

    public TravelsAddAdapter(Context mContext, List<HotSpots> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_travels_detail_add, parent, false);
        return new TravelAddHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }
    class TravelAddHolder extends RecyclerView.ViewHolder{


        public TravelAddHolder(View itemView) {
            super(itemView);
            TextView mTvTime = FontsIconUtil.findIconFontsById(R.id.tv_time, mContext, itemView);
        }
    }
}
