package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.MyAppoint;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/8/2 0002.
 * 成功约伴
 */
public class MyAppointSuccessHolder extends BaseHolder<MyAppoint> {

    public RelativeLayout mRlBulletinBoard;
    public RelativeLayout mRlMemberDetail;

    public MyAppointSuccessHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(MyAppoint datas, Context mContext) {

    }


    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_my_appoint_success, null);
        TextView mTvIconAdd = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_add, mContext, inflate);
        TextView mTvIconAir = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_air, mContext, inflate);
        TextView mTvIconPeople = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_people, mContext, inflate);
        TextView mTvIconTime = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_time, mContext, inflate);
        TextView mTvIconEye = FontsIconUtil.findIconFontsById(R.id.tv_icon_eye, mContext, inflate);
        TextView mTvIconLove= FontsIconUtil.findIconFontsById(R.id.tv_icon_love, mContext, inflate);
        TextView mTvDelete= FontsIconUtil.findIconFontsById(R.id.tv_delete, mContext, inflate);
        mRlBulletinBoard = (RelativeLayout) inflate.findViewById(R.id.rl_bulletin_board);
        mRlMemberDetail = (RelativeLayout) inflate.findViewById(R.id.rl_member_detail);
        return inflate;
    }
}
