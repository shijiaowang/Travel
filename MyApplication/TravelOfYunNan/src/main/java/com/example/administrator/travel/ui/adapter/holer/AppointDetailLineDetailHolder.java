package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointTogetherDetail;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class AppointDetailLineDetailHolder extends BaseHolder<AppointTogetherDetail.DataBean.RoutesBean> {
    @ViewInject(R.id.iv_bg)
    private ImageView mIvBg;
    @ViewInject(R.id.tv_des)
    private TextView mTvDes;
    @ViewInject(R.id.tv_address)
    private TextView mTvAddress;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;

    public AppointDetailLineDetailHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointTogetherDetail.DataBean.RoutesBean datas, Context mContext, int position) {
        x.image().bind(mIvBg,datas.getLogo_img(),getImageOptions(DensityUtil.dip2px(168),DensityUtil.dip2px(87)));
        mTvAddress.setText(datas.getAddress());
        mTvDes.setText(datas.getProvince()+datas.getContent());
        mTvName.setText(datas.getCity()+" · "+datas.getTitle());
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_appoint_detail_line_detail);
    }
}
