package com.example.administrator.travel.ui.appoint.aite;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.appoint.aite.AiteFollow;

import org.xutils.view.annotation.ViewInject;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class AiteHolder extends BaseHolder<AiteFollow> {
    @BindView(R.id.tv_nick_name) TextView mTvNickName;
    @BindView(R.id.tv_aite)TextView mTvAite;

    public AiteHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AiteFollow datas, Context mContext, int position) {
        mTvNickName.setText(datas.getNikeName());
        if (datas.isChecked()){
            mTvAite.setTextColor(Color.parseColor("#ffbf75"));
        }else {
            mTvAite.setTextColor(Color.parseColor("#e4e4e4"));
        }
    }


    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_aite);
        return inflate;
    }
}
