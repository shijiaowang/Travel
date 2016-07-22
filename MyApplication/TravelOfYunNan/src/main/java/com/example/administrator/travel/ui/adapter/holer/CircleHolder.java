package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class CircleHolder extends BaseHolder<Circle> {
    public CircleHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Circle datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_circle, null);
        TextView mTvIconLove = FontsIconUtil.findIconFontsById(R.id.tv_icon_love, mContext, inflate);
        TextView mTvIconDiscuss = FontsIconUtil.findIconFontsById(R.id.tv_icon_discuss, mContext, inflate);
        return inflate;
    }
}
