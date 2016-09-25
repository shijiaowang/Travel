package com.example.administrator.travel.ui.appoint.lineplan;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class LinePlanDestinationHolder extends BaseHolder<LineBean.Destination> {
    @BindView(R.id.tv_add)TextView mTvAdd;
    @BindView(R.id.tv_delete)public TextView mTvDelete;

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
