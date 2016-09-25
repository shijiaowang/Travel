package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleNavRight;
import com.example.administrator.travel.utils.ImageOptionsUtil;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavRightHolder extends BaseHolder<CircleNavRight.RightCircle> {
    @BindView(R.id.rl_post) RelativeLayout mRlPost;
    @BindView(R.id.iv_picture) ImageView mIvCircleBg;
    @BindView(R.id.civ_circle_icon) ImageView mCivCircleIcon;
    @BindView(R.id.tv_circle_name) TextView mTvCircleName;
    @BindView(R.id.tv_follow_number) TextView mTvFollowNumber;
    @BindView(R.id.tv_post_number) TextView mTvPostNumber;
    private ImageOptions imageOptions= ImageOptionsUtil.getImageOptions();



    public CircleNavRightHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CircleNavRight.RightCircle datas, Context mContext, int position) {
        if (datas != null){
            mTvFollowNumber.setText(datas.getCount_follow());
            mTvPostNumber.setText(datas.getCount_forum());
            mTvCircleName.setText(datas.getCname());
            x.image().bind(mIvCircleBg,
                    datas.getCircle_img(),
                    imageOptions
            );
            x.image().bind(mCivCircleIcon,datas.getCircle_ico(),ImageOptionsUtil.getUserIconImageOptions());



        }
    }


    @Override
    public View initRootView(Context mContext) {

        return  inflateView(R.layout.item_fragment_circle_nav_right);
    }
}
