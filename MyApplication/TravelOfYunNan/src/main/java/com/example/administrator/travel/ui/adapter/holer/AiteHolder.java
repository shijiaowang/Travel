package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AiteFollow;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class AiteHolder extends BaseHolder<AiteFollow> {

    private TextView mTvNikeName;
    private TextView mTvAite;

    public AiteHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AiteFollow datas, Context mContext) {
        mTvNikeName.setText(datas.getNikeName());
        if (datas.isChecked()){
            mTvAite.setTextColor(Color.parseColor("#ffbf75"));
        }else {
            mTvAite.setTextColor(Color.parseColor("#e4e4e4"));
        }
    }


    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_aite, null);
        mTvNikeName = (TextView) inflate.findViewById(R.id.tv_nike_name);
        mTvAite = FontsIconUtil.findIconFontsById(R.id.tv_aite, mContext, inflate);
        return inflate;
    }
}
