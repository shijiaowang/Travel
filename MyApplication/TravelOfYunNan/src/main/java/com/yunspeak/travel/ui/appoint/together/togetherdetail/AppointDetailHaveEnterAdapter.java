package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.PeopleBean;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6 0006.
 * 游记详情行走路线
 */
public class AppointDetailHaveEnterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<PeopleBean> mDatas;

    public AppointDetailHaveEnterAdapter(Context mContext, List<PeopleBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new EnterHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EnterHolder enterHolder = (EnterHolder) holder;
        x.image().bind(enterHolder.mIvIcon,mDatas.get(position).getUser_img());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    class EnterHolder extends RecyclerView.ViewHolder{
        private  ImageView mIvIcon;

        public EnterHolder(View itemView) {
            super(itemView);
            mIvIcon = (ImageView) itemView.findViewById(R.id.iv_icon);

        }
    }
}
