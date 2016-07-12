package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.HotSpots;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class HotSpotsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<HotSpots> mDatas;

    public HotSpotsAdapter(Context mContext, List<HotSpots> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_home_hot_spots, parent, false);
        return new HotSpotsHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 100;
    }
    class HotSpotsHolder extends RecyclerView.ViewHolder{

        private  TextView mTvHomeAdd;

        public HotSpotsHolder(View itemView) {
            super(itemView);
            mTvHomeAdd = (TextView) itemView.findViewById(R.id.tv_home_add);
        }
    }
}
