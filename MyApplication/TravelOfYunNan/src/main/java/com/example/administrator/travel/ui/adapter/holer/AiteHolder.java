package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AiteFollow;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class AiteHolder extends BaseHolder<AiteFollow> {

    private TextView mTvNikeName;

    public AiteHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AiteFollow datas, Context mContext) {
        mTvNikeName.setText(datas.getNikeName());
        if (datas.isChecked()){
            root.setBackgroundColor(Color.parseColor("#efeeda"));
        }else {
            root.setBackgroundColor(Color.parseColor("#fafafa"));
        }
    }


    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_aite, null);
        mTvNikeName = (TextView) inflate.findViewById(R.id.tv_nike_name);
        return inflate;
    }
}
