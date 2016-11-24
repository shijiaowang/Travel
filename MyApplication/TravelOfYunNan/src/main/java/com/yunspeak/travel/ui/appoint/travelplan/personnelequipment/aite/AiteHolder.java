package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.FrescoUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/1 0001.
 */
public class AiteHolder extends BaseHolder<AiteFollow> {
    @BindView(R.id.tv_nick_name) TextView mTvNickName;
    @BindView(R.id.tv_aite)TextView mTvAite;
    @BindView(R.id.iv_icon)SimpleDraweeView mIvIcon;

    public AiteHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AiteFollow datas, Context mContext, int position) {
        mTvNickName.setText(datas.getNikeName());
        if (datas.isChecked()){
            mTvAite.setTextColor(Color.parseColor("#ffbf75"));
        }else {
            mTvAite.setTextColor(Color.parseColor("#e4e4e4"));
        }
        FrescoUtils.displayIcon(mIvIcon,datas.getFollow().getUser_img());
    }


    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_aite);
        return inflate;
    }
}
