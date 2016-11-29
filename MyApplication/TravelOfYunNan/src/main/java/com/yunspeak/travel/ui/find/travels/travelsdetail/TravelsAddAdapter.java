package com.yunspeak.travel.ui.find.travels.travelsdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;

import java.util.List;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 游记详情行走路线
 */
public class TravelsAddAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<TravelsDetailBean.DataBean.TravelRoutesBean.RoutesBean> mDatas;

    public TravelsAddAdapter(Context mContext, List<TravelsDetailBean.DataBean.TravelRoutesBean.RoutesBean> mDatas) {
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
        TravelAddHolder travelAddHolder = (TravelAddHolder) holder;
        FrescoUtils.displayNormal(travelAddHolder.mIvRouteBg,mDatas.get(position).getLogo_img(),R.drawable.normal_2_1);
        travelAddHolder.mTvName.setText(mDatas.get(position).getTitle());
        travelAddHolder.mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd",mDatas.get(position).getTime()));

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    class TravelAddHolder extends RecyclerView.ViewHolder{
        private  TextView mTvTime;
        private  SimpleDraweeView mIvRouteBg;
        private  TextView mTvName;

        public TravelAddHolder(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mIvRouteBg = (SimpleDraweeView) itemView.findViewById(R.id.iv_route_bg);
            mTvName = (TextView) itemView.findViewById(R.id.tv_route_name);
        }
    }
}
