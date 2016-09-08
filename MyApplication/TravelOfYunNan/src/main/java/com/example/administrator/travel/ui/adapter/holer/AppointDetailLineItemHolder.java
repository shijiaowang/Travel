package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointTogetherDetail;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class AppointDetailLineItemHolder extends BaseHolder<AppointTogetherDetail.DataBean.RoutesBean> {
    @ViewInject(R.id.tv_spot_name)
    private TextView mTvSpotName;
    public AppointDetailLineItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointTogetherDetail.DataBean.RoutesBean datas, Context mContext, int position) {
        mTvSpotName.setText(datas.getCity()+" · "+datas.getTitle());
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_appoint_detail_line_item);
    }
}
