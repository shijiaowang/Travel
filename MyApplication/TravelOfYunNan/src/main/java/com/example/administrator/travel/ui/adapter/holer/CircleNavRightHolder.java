package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleNavRight;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavRightHolder extends BaseHolder<CircleNavRight.RightCircle> {

    private RelativeLayout mRlPost;
    private ImageView mIvCircleBg;
    private CircleImageView mCivCircleName;
    private TextView mTvCircleName;
    private TextView mTvFollowNumber;
    private TextView mTvPostNumber;

    public CircleNavRightHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CircleNavRight.RightCircle datas, Context mContext) {
        if (datas != null){
            mTvFollowNumber.setText(datas.getCount_follow());
            mTvPostNumber.setText(datas.getCount_forum());
            mTvCircleName.setText(datas.getCname());

        }
    }


    @Override
    public View initRootView(Context mContext) {
        View root = View.inflate(mContext, R.layout.item_fragment_circle_nav_right, null);
        mRlPost = (RelativeLayout) root.findViewById(R.id.rl_post);
        mIvCircleBg = (ImageView) root.findViewById(R.id.iv_picture);
        mCivCircleName = (CircleImageView) root.findViewById(R.id.civ_circle_icon);
        mTvCircleName = (TextView) root.findViewById(R.id.tv_circle_name);
        mTvFollowNumber = (TextView) root.findViewById(R.id.tv_follow_number);
        mTvPostNumber = (TextView) root.findViewById(R.id.tv_post_number);
        return root;
    }
}
