package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointWithMeDetail;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class AppointWithMeDetailDestinationHolder extends BaseHolder<AppointWithMeDetail.DataBean.RoutesBean> {
    @ViewInject(R.id.iv_bg)
    private ImageView mIvBg;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_add)
    private TextView mTvAdd;
    public AppointWithMeDetailDestinationHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointWithMeDetail.DataBean.RoutesBean datas, Context mContext, int position) {
        x.image().bind(mIvBg,datas.getLogo_img(),getImageOptions(DensityUtil.dip2px(113),DensityUtil.dip2px(75)));
        mTvAdd.setText(datas.getProvince()+datas.getAddress());
        mTvName.setText(datas.getCity()+" · "+datas.getTitle());
    }


    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_appoint_detail_with_me_destination);
    }
}
