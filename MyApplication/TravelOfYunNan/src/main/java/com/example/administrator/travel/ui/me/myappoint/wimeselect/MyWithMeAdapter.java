package com.example.administrator.travel.ui.me.myappoint.wimeselect;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.view.BadgeView;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class MyWithMeAdapter extends BaseRecycleViewAdapter<MyWithMeSelectBean.DataBean> {

    public MyWithMeAdapter(List<MyWithMeSelectBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.activity_my_appoint_selector, parent, false);
        return new MyWithMeSelectHolder(inflate);
    }



    @Override
    protected void childBindView(RecyclerView.ViewHolder holder, int position) {
        MyWithMeSelectHolder myWithMeSelectHolder = (MyWithMeSelectHolder) holder;
        MyWithMeSelectBean.DataBean dataBean = mDatas.get(position);
        FrescoUtils.displayRoundIcon(myWithMeSelectHolder.ivIcon, Uri.parse(dataBean.getTravel_img()));
    }

    class MyWithMeSelectHolder extends BaseRecycleViewHolder {
        @BindView(R.id.code)
        TextView code;
        @BindView(R.id.tv_appointing)
        TextView tvAppointing;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_delete)
        FontsIconTextView tvDelete;
        @BindView(R.id.iv_icon)
        SimpleDraweeView ivIcon;
        @BindView(R.id.tv_line)
        TextView tvLine;
        @BindView(R.id.tv_day_and_night)
        TextView tvDayAndNight;
        @BindView(R.id.tv_start_and_long)
        TextView tvStartAndLong;
        @BindView(R.id.tv_plan_number)
        TextView tvPlanNumber;
        @BindView(R.id.tv_have_number)
        TextView tvHaveNumber;
        @BindView(R.id.bv_enter_people)
        BadgeView bvEnterPeople;
        @BindView(R.id.rl_member_detail)
        RelativeLayout rlMemberDetail;
        @BindView(R.id.bv_bulletin_number)
        BadgeView bvBulletinNumber;
        @BindView(R.id.rl_bulletin_board)
        RelativeLayout rlBulletinBoard;
        @BindView(R.id.tv_icon_eye)
        FontsIconTextView tvIconEye;
        @BindView(R.id.tv_watch_number)
        TextView tvWatchNumber;
        @BindView(R.id.tv_icon_love)
        FontsIconTextView tvIconLove;
        @BindView(R.id.tv_love_number)
        TextView tvLoveNumber;
        @BindView(R.id.tv_how_long)
        TextView tvHowLong;

        public MyWithMeSelectHolder(View itemView) {
            super(itemView);
        }
    }
}
