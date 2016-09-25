package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.appoint.togetherdetail.AppointTogetherDetail;
import com.example.administrator.travel.ui.appoint.togetherdetail.AppointDetailLineDetailAdapter;
import com.example.administrator.travel.ui.appoint.togetherdetail.AppointDetailLineItemAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import butterknife.BindView;

/**
 * Created by android on 2016/9/4.
 */
public class TravelDetailLineHolder extends BaseHolder<List<AppointTogetherDetail.DataBean.RoutesBean>>{
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.dlv_line) View mDlvLine;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.lv_line) ToShowAllListView mLvLine;

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
                layoutParams.height = DensityUtil.dip2px(30) + height;
                mDlvLine.setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_appoint_detail_line);
    }
}
