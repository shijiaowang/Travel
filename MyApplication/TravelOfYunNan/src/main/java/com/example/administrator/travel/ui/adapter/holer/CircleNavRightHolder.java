package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleNavRight;
import com.example.administrator.travel.utils.ImageOptionsUtil;
import com.example.administrator.travel.utils.LogUtils;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavRightHolder extends BaseHolder<CircleNavRight.RightCircle> {
    @ViewInject(R.id.rl_post)
    private RelativeLayout mRlPost;
    @ViewInject(R.id.iv_picture)
    private ImageView mIvCircleBg;
    @ViewInject(R.id.civ_circle_icon)
    private ImageView mCivCircleIcon;
    @ViewInject(R.id.tv_circle_name)
    private TextView mTvCircleName;
    @ViewInject(R.id.tv_follow_number)
    private TextView mTvFollowNumber;
    @ViewInject(R.id.tv_post_number)
    private TextView mTvPostNumber;
    private ImageOptions imageOptions= ImageOptionsUtil.getImageOptions();



    public CircleNavRightHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CircleNavRight.RightCircle datas, Context mContext) {
        if (datas != null){
            mTvFollowNumber.setText(datas.getCount_follow());
            mTvPostNumber.setText(datas.getCount_forum());
            mTvCircleName.setText(datas.getCname());
            x.image().bind(mIvCircleBg,
                    datas.getCircle_img(),
                    imageOptions
            );



        }
    }


    @Override
    public View initRootView(Context mContext) {
        View root = inflateView(R.layout.item_fragment_circle_nav_right);
    /*    mRlPost = (RelativeLayout) root.findViewById(R.id.rl_post);
        mIvCircleBg = (ImageView) root.findViewById(R.id.iv_picture);
        mCivCircleIcon = (CircleImageView) root.findViewById(R.id.civ_circle_icon);
        mTvCircleName = (TextView) root.findViewById(R.id.tv_circle_name);
        mTvFollowNumber = (TextView) root.findViewById(R.id.tv_follow_number);
        mTvPostNumber = (TextView) root.findViewById(R.id.tv_post_number);*/
        return root;
    }
}
