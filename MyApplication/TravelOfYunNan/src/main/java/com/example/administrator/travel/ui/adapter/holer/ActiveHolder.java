package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Active;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class ActiveHolder extends BaseHolder<Active> {

    private TextView mTvAir;

    public ActiveHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Active datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_active);
        mTvAir = FontsIconUtil.findIconFontsById(R.id.tv_air, mContext, inflate);
        return inflate;
    }
}
