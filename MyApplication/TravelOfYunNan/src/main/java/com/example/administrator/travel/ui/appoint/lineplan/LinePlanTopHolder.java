package com.example.administrator.travel.ui.appoint.lineplan;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class LinePlanTopHolder extends BaseHolder<LineBean> {
    @ViewInject(R.id.tv_start_add)
    public TextView mTvStartAdd;


    public LinePlanTopHolder(Context context) {
        super(context);

    }

    @Override
    protected void initItemDatas(LineBean datas, Context mContext, int position) {

    }


    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_line_plan_top);
    }
}
