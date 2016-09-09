package com.example.administrator.travel.ui.appoint.lineplan;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Line;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.view.DottedLineView;
import com.example.administrator.travel.ui.view.FontsIconTextView;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class LinePlanHolder extends BaseHolder<Line> {
    @ViewInject(R.id.tv_add)
    public FontsIconTextView mTvAdd;
    public LinearLayout mLlLine;
    @ViewInject(R.id.dlv_line)
    private DottedLineView mDlvLine;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    private FontsIconTextView mTvDelete;


    public LinePlanHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Line datas, Context mContext, int position) {
        mTvNumber.setText(datas.getDayNumber());
        mTvTime.setText(datas.getDayTime());

    }



    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_line_plan);
        return view;
    }
}
