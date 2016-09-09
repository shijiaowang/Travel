package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointTogetherDetail;
import com.example.administrator.travel.ui.adapter.AppointDetailLineDetailAdapter;
import com.example.administrator.travel.ui.adapter.AppointDetailLineItemAdapter;
import com.example.administrator.travel.ui.view.DottedLineView;
import com.example.administrator.travel.ui.view.ToShowAllListView;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/9/4.
 */
public class TravelDetailLineHolder extends BaseHolder<List<AppointTogetherDetail.DataBean.RoutesBean>>{
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.dlv_line)
    private DottedLineView mDlvLine;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    @ViewInject(R.id.lv_line)
    private ToShowAllListView mLvLine;

    private boolean isDetail;

    public TravelDetailLineHolder(Context context, boolean isDetail) {
        super(context);
        this.isDetail = isDetail;
    }


    @Override
    protected void initItemDatas(List<AppointTogetherDetail.DataBean.RoutesBean> datas, final Context mContext, int position) {
        mTvTime.setText(datas.get(0).getTime());
        mTvNumber.setText((position + 1) + "");
        if (!isDetail) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)mLvLine.getLayoutParams();
            layoutParams.leftMargin= DensityUtil.dip2px(40);
            layoutParams.topMargin= DensityUtil.dip2px(22);
            mLvLine.setAdapter(new AppointDetailLineItemAdapter(mContext, datas));

        }else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)mLvLine.getLayoutParams();
            layoutParams.leftMargin= DensityUtil.dip2px(65);
            layoutParams.topMargin= DensityUtil.dip2px(13);
            mLvLine.setAdapter(new AppointDetailLineDetailAdapter(mContext, datas));
        }
        measureHeight(mLvLine);
        setLineHeight();
    }

    private void setLineHeight() {
        mLvLine.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLvLine.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = mLvLine.getHeight();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mDlvLine.getLayoutParams();
                layoutParams.height = DensityUtil.dip2px(52) + height;
                mDlvLine.setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_appoint_detail_line);
    }
}
