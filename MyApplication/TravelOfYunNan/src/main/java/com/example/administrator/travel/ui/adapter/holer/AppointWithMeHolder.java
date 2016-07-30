package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointWithMe;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class AppointWithMeHolder extends BaseHolder<AppointWithMe> {

    private FlowLayout mFlTitle;
    private String[] titles = new String[]{"就是这么任性", "游的就是寂寞", "玩", "有责任心", "乡村一日游"};
    public AppointWithMeHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointWithMe datas, Context mContext) {
        if (mFlTitle!=null && mFlTitle.getChildCount()>0){
            mFlTitle.removeAllViews();
        }
        for (int i = 0; i < titles.length; i++) {
            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_fragment_appoint_title, mFlTitle, false);
            textView.setText(titles[i]);
            mFlTitle.addView(textView);
        }
    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_fragment_appoint_play_with_me, null);
        TextView mTvIconAdd = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_add, mContext, inflate);
        TextView mTvIconAir = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_air, mContext, inflate);
        TextView mTvIconPeople = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_people, mContext, inflate);
        TextView mTvIconTime = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_time, mContext, inflate);
        TextView mTvIconEye = FontsIconUtil.findIconFontsById(R.id.tv_icon_eye, mContext, inflate);
        TextView mTvIconLove= FontsIconUtil.findIconFontsById(R.id.tv_icon_love, mContext, inflate);

        mFlTitle = (FlowLayout) inflate.findViewById(R.id.fl_title);
        return inflate;
    }
}
