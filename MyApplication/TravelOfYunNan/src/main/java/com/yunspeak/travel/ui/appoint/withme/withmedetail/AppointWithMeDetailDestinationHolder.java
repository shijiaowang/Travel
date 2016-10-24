package com.yunspeak.travel.ui.appoint.withme.withmedetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/8 0008.
 */
public class AppointWithMeDetailDestinationHolder extends BaseHolder<AppointWithMeDetailBean.DataBean.RoutesBean> {
    @BindView(R.id.iv_bg) SimpleDraweeView mIvBg;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_add) TextView mTvAdd;
    public AppointWithMeDetailDestinationHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointWithMeDetailBean.DataBean.RoutesBean datas, Context mContext, int position) {
        FrescoUtils.displayNormal(mIvBg,datas.getLogo_img());
        mTvAdd.setText(datas.getProvince()+datas.getAddress());
        mTvName.setText(datas.getCity()+" · "+datas.getTitle());
    }


    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_appoint_detail_with_me_destination);
    }
}
