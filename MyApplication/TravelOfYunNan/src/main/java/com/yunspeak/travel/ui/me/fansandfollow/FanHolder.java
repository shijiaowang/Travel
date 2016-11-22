package com.yunspeak.travel.ui.me.fansandfollow;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite.Follow;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.StringUtils;

import java.util.List;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/18 0018.
 */
public class FanHolder extends BaseRecycleViewHolder<Follow> {
    private final List<Follow> mDatas;
    @BindView(R.id.tv_nick_name) TextView mTvNickName;
    @BindView(R.id.iv_icon)
    SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.v_line) public View mVLine;

    public FanHolder(View itemView, List<Follow> mDatas) {
        super(itemView);
        this.mDatas = mDatas;
    }





    @Override
    public void childBindView(int position, final Follow datas, final Context mContext) {
        if (position==mDatas.size()-1){
            mVLine.setVisibility(View.GONE);
        }else {
            mVLine.setVisibility(View.VISIBLE);
        }
        mTvNickName.setText(datas.getNick_name());
        String des= StringUtils.isEmpty(datas.getContent())?"这个人很懒，什么都没有留下":datas.getContent();
        mTvContent.setText(des);
        FrescoUtils.displayIcon(mIvIcon,datas.getUser_img());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserCenterActivity.start(mContext,mIvIcon,datas.getId());
            }
        });
    }
}
