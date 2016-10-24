package com.yunspeak.travel.ui.me.fansandfollow;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.appoint.aite.Follow;

import org.xutils.x;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/18 0018.
 */
public class FanHolder extends BaseHolder<Follow> {
    @BindView(R.id.tv_nick_name) TextView mTvNickName;
    @BindView(R.id.iv_icon) ImageView mIvIcon;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.v_line) public View mVLine;

    public FanHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Follow datas, Context mContext, int position) {
        mTvNickName.setText(datas.getNick_name());
        mTvContent.setText(datas.getContent());
        x.image().bind(mIvIcon,datas.getUser_img(),getImageOptions(43,43));
    }



    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_follow_and_fan);
    }
}
