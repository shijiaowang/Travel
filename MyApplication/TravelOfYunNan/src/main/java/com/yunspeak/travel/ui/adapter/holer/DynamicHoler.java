package com.yunspeak.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Dynamic;

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
        View inflate = inflateView(R.layout.item_other_user_fragment_dynamic);
        return inflate;
    }
}
