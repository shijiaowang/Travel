package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.view.ToShowAllListView;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by android on 2016/9/4.
 */
public class TravelDetailLineHolder extends BaseHolder {
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    @ViewInject(R.id.lv_line)
    private ToShowAllListView mLvLine;
    public TravelDetailLineHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_appoint_detail_line);
    }
}
