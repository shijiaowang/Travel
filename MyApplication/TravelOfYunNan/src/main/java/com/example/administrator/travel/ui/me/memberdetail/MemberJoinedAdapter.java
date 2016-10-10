package com.example.administrator.travel.ui.me.memberdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;

import org.xutils.x;

import java.util.List;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 已加入
 */
public class MemberJoinedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    private List<MemberDetailBean.DataBean.JoinBean> mDatas;

    public MemberJoinedAdapter(Context mContext, List<MemberDetailBean.DataBean.JoinBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_member_joined, parent, false);
        return new MemberDetailHolder(inflate);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MemberDetailHolder memberDetailHolder = (MemberDetailHolder) holder;
        MemberDetailBean.DataBean.JoinBean joinBean = mDatas.get(position);
        x.image().bind(memberDetailHolder.mIvIcon,joinBean.getUser_img());
        memberDetailHolder.mTvAge.setText(joinBean.getAge());
        memberDetailHolder.mTvSex.setText(joinBean.getSex().equals("1")?R.string.activity_member_detail_boy:R.string.activity_member_detail_girl);
        memberDetailHolder.mTvName.setText(joinBean.getNick_name());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class MemberDetailHolder extends RecyclerView.ViewHolder {
         ImageView mIvIcon;
         TextView mTvName;
         TextView mTvSex;
         TextView mTvAge;
        public MemberDetailHolder(View itemView) {
            super(itemView);
            mIvIcon= (ImageView) itemView.findViewById(R.id.iv_icon);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvSex = (TextView) itemView.findViewById(R.id.tv_sex);
            mTvAge = (TextView) itemView.findViewById(R.id.tv_age);
        }
    }




}
