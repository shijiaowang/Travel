package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/5 0005.
 */
public class AppointDetailLineDetailHolder extends BaseHolder<AppointTogetherDetailBean.DataBean.RoutesBean> {
    @BindView(R.id.iv_bg) SimpleDraweeView mIvBg;
    @BindView(R.id.tv_des) TextView mTvDes;
    @BindView(R.id.tv_address) TextView mTvAddress;
    @BindView(R.id.tv_name) TextView mTvName;

    public AppointDetailLineDetailHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointTogetherDetailBean.DataBean.RoutesBean datas, Context mContext, int position) {
        FrescoUtils.displayNormal(mIvBg,datas.getLogo_img());
        mTvAddress.setText(datas.getAddress());
        mTvDes.setText(datas.getProvince()+datas.getContent());
        mTvName.setText(datas.getCity()+" · "+datas.getTitle());
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_appoint_detail_line_detail);
    }
}
