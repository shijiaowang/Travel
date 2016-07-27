package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.bean.CircleNavLeft;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavLeftHolder extends BaseHolder<Circle.DataBean.CircleLeftBean> {
    private TextView mTvPlace;
    private TextView mTvCursor;
    private View mVLine;

    public CircleNavLeftHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Circle.DataBean.CircleLeftBean datas, Context mContext) {
        mTvPlace.setText(datas.getCname());
        if (datas.isCheck){
            mVLine.setBackgroundColor(Color.parseColor("#ffbf75"));
            mTvPlace.setTextColor(Color.parseColor("#ffbf75"));
            mTvCursor.setVisibility(View.VISIBLE);
        }else {
            mVLine.setBackgroundColor(Color.parseColor("#f1f1f1"));
            mTvPlace.setTextColor(Color.parseColor("#c1c1c1"));
            mTvCursor.setVisibility(View.GONE);
        }
    }




    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_fragment_circle_nav_left, null);
        mTvPlace = (TextView) inflate.findViewById(R.id.tv_place);
        mTvCursor = FontsIconUtil.findIconFontsById(R.id.tv_cursor, mContext, inflate);
        mVLine = inflate.findViewById(R.id.v_line);
        return inflate;
    }
}
