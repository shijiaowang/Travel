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
        TextView iconFontsById = FontsIconUtil.findIconFontsById(R.id.tv_icon_add, mContext, inflate);
        TextView iconFontsById1 = FontsIconUtil.findIconFontsById(R.id.tv_icon_air, mContext, inflate);
        TextView iconFontsById2 = FontsIconUtil.findIconFontsById(R.id.tv_icon_people, mContext, inflate);
        TextView iconFontsById3 = FontsIconUtil.findIconFontsById(R.id.tv_icon_eye, mContext, inflate);
        TextView iconFontsById4= FontsIconUtil.findIconFontsById(R.id.tv_icon_love, mContext, inflate);
        TextView iconFontsById5 = FontsIconUtil.findIconFontsById(R.id.tv_icon_time, mContext, inflate);
        return inflate;
    }
}
