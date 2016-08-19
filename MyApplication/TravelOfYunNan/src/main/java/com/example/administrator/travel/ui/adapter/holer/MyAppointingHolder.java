package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.MyAppoint;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/8/2 0002.
 * 约伴中
 */
public class MyAppointingHolder extends BaseHolder<MyAppoint> {
    public MyAppointingHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(MyAppoint datas, Context mContext) {

    }



    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_my_appointting);
        return inflate;
    }
}
