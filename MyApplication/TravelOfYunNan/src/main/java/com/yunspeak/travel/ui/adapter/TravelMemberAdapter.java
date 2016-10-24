package com.yunspeak.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.TravelsDetail;

import org.xutils.x;

import java.util.List;


/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class TravelMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<TravelsDetail.DataBean.TravelRoutesBean.UserBean> mDatas;

    public TravelMemberAdapter(Context mContext, List<TravelsDetail.DataBean.TravelRoutesBean.UserBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_activity_travel_detail_member, parent, false);
        return new MemberHolder(root);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MemberHolder memberHolder = (MemberHolder) holder;
        memberHolder.mTvUserName.setText(mDatas.get(position).getNick_name());
        x.image().bind(memberHolder.mCivUserIcon,mDatas.get(position).getUser_img());
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

   public class MemberHolder extends RecyclerView.ViewHolder {


       private  TextView mTvUserName;
       private ImageView mCivUserIcon;

       public MemberHolder(View itemView) {
           super(itemView);
           mCivUserIcon = (ImageView) itemView.findViewById(R.id.civ_user_icon);
           mTvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
       }
    }
}
