package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Travels;
import com.example.administrator.travel.utils.FormatDateUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class ActivityTravelsHolder extends BaseHolder<Travels.DataBean> {
    @BindView(R.id.iv_picture) ImageView mIvPicture;
    @BindView(R.id.iv_user_icon) ImageView mIvUserIcon;
    @BindView(R.id.tv_nick_name) TextView mTvNickName;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.tv_time)TextView mTvTime;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;
    public ActivityTravelsHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Travels.DataBean datas, Context mContext, int position) {
        x.image().bind(mIvPicture,datas.getTitle_img(),getImageOptions(340,165));
        mTvNickName.setText(datas.getAuthor());
        x.image().bind(mIvUserIcon, datas.getLogo_img(), getUserImageOptions(44, 44));
        mTvContent.setText(datas.getTitle());
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd",datas.getAdd_time()));
    }

    @Override
    public View initRootView(Context mContext) {
        View inflate =inflateView(R.layout.item_activity_travels);
        return inflate;
    }
}
