package com.example.administrator.travel.ui.appoint.togetherdetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PricebasecBean;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class AppointDetailInsuranceHolder extends BaseHolder<PricebasecBean> {
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_des)
    private TextView mTvDes;
    public AppointDetailInsuranceHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(PricebasecBean datas, Context mContext, int position) {
        mTvName.setText(datas.getKey());
        mTvDes.setText(datas.getValue()+"元");
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_appoint_detail_pro_equ);
    }
}
