package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.PricebasecBean;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class AppointDetailInsuranceHolder extends BaseHolder<PricebasecBean> {
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_number) TextView mTvDes;
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
