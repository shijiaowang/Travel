package com.yunspeak.travel.ui.find.travels.travelsdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.TravelsDetailBean;
import com.yunspeak.travel.utils.FrescoUtils;

import java.util.List;


/**
 * Created by wangyang on 2016/7/25 0025.
 */
public class TravelMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<TravelsDetailBean.DataBean.TravelRoutesBean.UserBean> mDatas;

    public TravelMemberAdapter(Context mContext, List<TravelsDetailBean.DataBean.TravelRoutesBean.UserBean> mDatas) {
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
        FrescoUtils.displayIcon(memberHolder.mCivUserIcon,mDatas.get(position).getUser_img());
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

   public class MemberHolder extends RecyclerView.ViewHolder {


       private  TextView mTvUserName;
       private SimpleDraweeView mCivUserIcon;

       public MemberHolder(View itemView) {
           super(itemView);
           mCivUserIcon = (SimpleDraweeView) itemView.findViewById(R.id.civ_user_icon);
           mTvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
       }
    }
}
