package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleNavLeft;
import com.example.administrator.travel.ui.view.SingleView;
import com.example.administrator.travel.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavLeftHolder extends BaseHolder<CircleNavLeft> {
    private TextView mTvPlace;
    private ImageView mIvCursor;

    public CircleNavLeftHolder(Context context) {
        super(context);
    }


    @Override
    protected void initItemDatas(CircleNavLeft datas, Context mContext) {
       if (datas.isChecked()){
           mTvPlace.setTextColor(Color.parseColor("#ffbf75"));
           mIvCursor.setVisibility(View.VISIBLE);
       }else {
           mTvPlace.setTextColor(Color.parseColor("#b5b5b5"));
           mIvCursor.setVisibility(View.GONE);
       }
    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_fragment_circle_nav_left, null);
        mTvPlace = (TextView) inflate.findViewById(R.id.tv_place);
        mIvCursor = (ImageView) inflate.findViewById(R.id.iv_cursor);
        return inflate;
    }
}
