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
    protected void initItemDatas(Dynamic datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_fragment_dynamic);
        return inflate;
    }
}
