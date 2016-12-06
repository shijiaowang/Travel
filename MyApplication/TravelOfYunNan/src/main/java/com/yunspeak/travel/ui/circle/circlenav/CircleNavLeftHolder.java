package com.yunspeak.travel.ui.circle.circlenav;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Circle;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.view.FontsIconTextView;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavLeftHolder extends BaseHolder<Circle.DataBean.CircleLeftBean> {
    @BindView(R.id.tv_place) TextView mTvPlace;
    @BindView(R.id.tv_cursor)FontsIconTextView mTvCursor;
    @BindView(R.id.v_line)View mVLine;

    public CircleNavLeftHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Circle.DataBean.CircleLeftBean datas, Context mContext, int position) {
        mTvPlace.setText(datas.getCname());
        if (datas.isCheck){
            mVLine.setBackgroundColor(Color.parseColor("#ffbf75"));
            mTvPlace.setTextColor(Color.parseColor("#ffbf75"));
            mTvCursor.setVisibility(View.VISIBLE);
        }else {
            mVLine.setBackgroundColor(Color.parseColor("#f1f1f1"));
            mTvPlace.setTextColor(Color.parseColor("#c1c1c1"));
            mTvCursor.setVisibility(View.INVISIBLE);
        }
    }




    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_fragment_circle_nav_left);
    }
}
