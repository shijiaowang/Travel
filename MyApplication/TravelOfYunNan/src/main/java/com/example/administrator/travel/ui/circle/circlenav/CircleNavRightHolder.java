package com.example.administrator.travel.ui.circle.circlenav;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleNavRight;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/8 0008.
 * 圈子导航 右边圈子
 */
public class CircleNavRightHolder extends BaseHolder<CircleNavRight.RightCircle> {
    @BindView(R.id.rl_post) RelativeLayout mRlPost;
    @BindView(R.id.iv_picture) SimpleDraweeView mIvCircleBg;
    @BindView(R.id.civ_circle_icon) SimpleDraweeView mCivCircleIcon;
    @BindView(R.id.tv_circle_name) TextView mTvCircleName;
    @BindView(R.id.tv_follow_number) TextView mTvFollowNumber;
    @BindView(R.id.tv_post_number) TextView mTvPostNumber;



    public CircleNavRightHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CircleNavRight.RightCircle datas, Context mContext, int position) {

            mTvFollowNumber.setText(datas.getCount_follow());
            mTvPostNumber.setText(datas.getCount_forum());
            mTvCircleName.setText(datas.getCname());
            FrescoUtils.displayNormal(mIvCircleBg,datas.getCircle_ico());
            FrescoUtils.displayNormal(mCivCircleIcon,datas.getCircle_ico());




    }


    @Override
    public View initRootView(Context mContext) {

        return  inflateView(R.layout.item_fragment_circle_nav_right);
    }
}
