package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleHot;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleHotHolder extends BaseHolder<CircleHot> {
    public CircleHotHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CircleHot circleHot, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View root = View.inflate(mContext, R.layout.item_fragment_circle_hot,null);
        ImageView userIcon;
        TextView userNikeName;
        TextView titleTime;
        TextView titleType;
        TextView titleName;
        TextView titleDes;
        LinearLayout pictureToggle;
        ImageView picture1;
        ImageView picture2;
        ImageView picture3;
        ImageView zhan;
        ImageView discuss;
        TextView zhanNumber;
        TextView discussNumber;
        return root;
    }
}
