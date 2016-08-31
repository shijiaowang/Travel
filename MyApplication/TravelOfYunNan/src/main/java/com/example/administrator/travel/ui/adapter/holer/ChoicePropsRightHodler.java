package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class ChoicePropsRightHodler extends BaseHolder {
    public ChoicePropsRightHodler(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_choice_equ_right);
    }
}
