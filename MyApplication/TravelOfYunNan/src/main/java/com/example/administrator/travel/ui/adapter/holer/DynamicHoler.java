package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Dynamic;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class DynamicHoler extends BaseHolder<Dynamic> {
    public DynamicHoler(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Dynamic datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_fragment_dynamic, null);
        return inflate;
    }
}
