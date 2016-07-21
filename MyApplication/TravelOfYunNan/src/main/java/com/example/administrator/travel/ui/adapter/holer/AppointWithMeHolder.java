package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointWithMe;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class AppointWithMeHolder extends BaseHolder<AppointWithMe> {
    public AppointWithMeHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointWithMe datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_fragment_appoint_play_with_me, null);
        TextView mTvIconAdd = FontsIconUtil.findIconFontsById(R.id.tv_icon_add, mContext, inflate);
        TextView mTvIconAir = FontsIconUtil.findIconFontsById(R.id.tv_icon_air, mContext, inflate);
        TextView mTvIconPeople = FontsIconUtil.findIconFontsById(R.id.tv_icon_people, mContext, inflate);
        TextView mTvIconEye = FontsIconUtil.findIconFontsById(R.id.tv_icon_eye, mContext, inflate);
        TextView mTvIconLove= FontsIconUtil.findIconFontsById(R.id.tv_icon_love, mContext, inflate);
        TextView mTvIconTime = FontsIconUtil.findIconFontsById(R.id.tv_icon_time, mContext, inflate);
        mTvIconAir.getPaint().setFakeBoldText(true);
        mTvIconTime.getPaint().setFakeBoldText(true);
        mTvIconPeople.getPaint().setFakeBoldText(true);
        mTvIconAdd.getPaint().setFakeBoldText(true);
        return inflate;
    }
}
