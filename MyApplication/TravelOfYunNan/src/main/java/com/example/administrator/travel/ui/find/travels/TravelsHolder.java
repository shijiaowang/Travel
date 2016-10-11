package com.example.administrator.travel.ui.find.travels;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.find.travels.TravelsBean;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.x;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/25 0025.
 */
public class TravelsHolder extends BaseHolder<TravelsBean.DataBean> {
    @BindView(R.id.iv_picture) SimpleDraweeView mIvPicture;
    @BindView(R.id.iv_user_icon) SimpleDraweeView mIvUserIcon;
    @BindView(R.id.tv_nick_name) TextView mTvNickName;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.tv_time)TextView mTvTime;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;
    public TravelsHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(TravelsBean.DataBean datas, Context mContext, int position) {
        FrescoUtils.displayNormal(mIvPicture,datas.getTitle_img());
        FrescoUtils.displayIcon(mIvUserIcon,datas.getLogo_img());
        mTvNickName.setText(datas.getAuthor());
        mTvContent.setText(datas.getTitle());
        //mTvWatchNumber.setText(datas.get);
        mTvTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_D,datas.getAdd_time()));
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_travels);
    }
}
