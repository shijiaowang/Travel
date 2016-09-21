package com.example.administrator.travel.ui.appoint.togetherdetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.appoint.choicesequipment.ChoicePropSelectBean;
import com.example.administrator.travel.ui.appoint.togetherdetail.AppointTogetherDetail;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class ProviderHolder extends BaseHolder {
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_number)
    private TextView mTvDes;
    public ProviderHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {
        if (datas instanceof AppointTogetherDetail.DataBean.PropBean){
            AppointTogetherDetail.DataBean.PropBean datas1 = (AppointTogetherDetail.DataBean.PropBean) datas;
            mTvName.setText(datas1.getName()+"x"+datas1.getNumber());
            mTvDes.setText(datas1.getContent());
        }else if (datas instanceof ChoicePropSelectBean){
            ChoicePropSelectBean datas1 = (ChoicePropSelectBean) datas;
            mTvName.setText(datas1.getName()+"x"+datas1.getNumber());
            mTvDes.setText(datas1.getContent());
        }

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_appoint_detail_pro_equ);
    }
}
