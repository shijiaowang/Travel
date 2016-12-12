package com.yunspeak.travel.ui.me.myappoint.memberdetail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.StringUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 已加入
 */
public class MemberJoinedAdapter extends BaseRecycleViewAdapter<MemberDetailBean.DataBean.JoinBean> {


    public MemberJoinedAdapter(List<MemberDetailBean.DataBean.JoinBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<MemberDetailBean.DataBean.JoinBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberDetailHolder(inflateView(R.layout.item_activity_member_joined, parent));
    }


    public class MemberDetailHolder extends BaseRecycleViewHolder<MemberDetailBean.DataBean.JoinBean> {

        @BindView(R.id.iv_icon)
        SimpleDraweeView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_sex)
        FontsIconTextView tvSex;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.v_hide_line)
        View mVHideLine;
        public MemberDetailHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void childBindView(int position, final MemberDetailBean.DataBean.JoinBean joinBean, final Context mContext) {
            FrescoUtils.displayIcon(ivIcon, joinBean.getUser_img());
            String age = joinBean.getAge();
            if (StringUtils.isEmpty(age) || age.equals("0")){
                tvAge.setText("保密");
            }else {
                tvAge.setText(age);
            }
           tvSex.setText(joinBean.getSex().equals("1") ? R.string.activity_member_detail_boy : R.string.activity_member_detail_girl);
           tvName.setText(joinBean.getNick_name());
            if (position==mDatas.size()-1){
                mVHideLine.setVisibility(View.GONE);
            }else {
                mVHideLine.setVisibility(View.VISIBLE);
            }
            if (joinBean.getIs_boss().equals("1")){
                tvType.setVisibility(View.VISIBLE);
                tvType.setText("发布人");
                tvType.setBackgroundResource(R.drawable.r_green);
            }else if (joinBean.getIs_manage().equals("1")){
                tvType.setVisibility(View.VISIBLE);
                tvType.setText("专管员");
                tvType.setBackgroundResource(R.drawable.r_red);
            }else {
                tvType.setVisibility(View.GONE);
            }
            ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OtherUserCenterActivity.start(mContext,ivIcon,joinBean.getUser_id());
                }
            });
        }
    }
}
