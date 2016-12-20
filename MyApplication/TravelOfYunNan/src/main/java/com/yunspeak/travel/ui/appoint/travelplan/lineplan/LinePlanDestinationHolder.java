package com.yunspeak.travel.ui.appoint.travelplan.lineplan;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.LineBean;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/12 0012.
 */
public class LinePlanDestinationHolder extends BaseHolder<LineBean.Destination> {
    @BindView(R.id.tv_add)TextView mTvAdd;
    @BindView(R.id.tv_delete) TextView mTvDelete;
    public LinePlanDestinationHolder(Context context) {
        super(context);
    }
    @Override
    protected void initItemDatas(LineBean.Destination datas, Context mContext, int position) {
        mTvAdd.setText(datas.getName());
    }
    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_line_plan_add_item);
    }
}
