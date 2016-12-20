package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.AppointTogetherDetailBean;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.bean.ChoicePropSelectBean;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/5 0005.
 */
public class ProviderHolder extends BaseHolder {
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_number) TextView mTvDes;
    public ProviderHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {
        if (datas instanceof AppointTogetherDetailBean.DataBean.PropBean){
            AppointTogetherDetailBean.DataBean.PropBean datas1 = (AppointTogetherDetailBean.DataBean.PropBean) datas;
            String text = datas1.getNumber();
            mTvName.setText(datas1.getName()+"x"+text);
            if (text==null || text.equals("0")){
                mTvName.setText(datas1.getName());
            }

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
