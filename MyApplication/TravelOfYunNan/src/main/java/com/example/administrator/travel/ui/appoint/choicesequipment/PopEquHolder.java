package com.example.administrator.travel.ui.appoint.choicesequipment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class PopEquHolder extends BaseHolder<ChoicePropSelectBean> {
    @ViewInject(R.id.tv_name)
    private TextView mTvNmae;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    public PopEquHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(ChoicePropSelectBean datas, Context mContext, int position) {

            mTvNmae.setText(datas.getName());
            mTvNumber.setText("x" + datas.getNumber());

    }

    @Override
    public View initRootView(Context mContext) {

            return inflateView(R.layout.item_pop_equ);

    }
}
