package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class MemberJoinedHolder extends BaseHolder {

    private TextView mTvSex;

    public MemberJoinedHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_member_joined);
        mTvSex = FontsIconUtil.findIconFontsById(R.id.tv_sex, mContext, view);
        TextView mTvAge = FontsIconUtil.findIconFontsById(R.id.tv_age, mContext, view);
        return view;
    }
}
