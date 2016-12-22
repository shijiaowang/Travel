package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.AppointTogetherDetailBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import org.xutils.common.util.DensityUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/4.
 */
public class TravelDetailLineHolder extends BaseRecycleViewHolder<List<AppointTogetherDetailBean.DataBean.RoutesBean>> {
    private  boolean isDetail;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.dlv_line) View mDlvLine;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.lv_line)
    RecyclerView mLvLine;
    @BindView(R.id.iv_cursor) ImageView mIvCursor;
    private int [] cursors=new int[]{R.drawable.activity_line_add1,R.drawable.activity_line_add2,R.drawable.activity_line_add3,R.drawable.activity_line_add4,R.drawable.activity_line_add5,R.drawable.activity_line_add6,R.drawable.activity_line_add7};
    private BaseRecycleViewAdapter appointDetailLineItemAdapter;

    public TravelDetailLineHolder(View itemView,boolean isDetail) {
        super(itemView);
        this.isDetail = isDetail;
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
    public void childBindView(int position, List<AppointTogetherDetailBean.DataBean.RoutesBean> datas, Context mContext) {
        mTvTime.setText(datas.get(0).getTime());
        mTvNumber.setText("Day\n"+(position + 1));
        mIvCursor.setImageResource(cursors[position%cursors.length]);
        if (appointDetailLineItemAdapter==null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLvLine.getLayoutParams();
            if (!isDetail) {
                layoutParams.leftMargin = DensityUtil.dip2px(40);
                layoutParams.topMargin = DensityUtil.dip2px(22);
                appointDetailLineItemAdapter = new AppointDetailLineItemAdapter(datas,mContext);
            }else {
                layoutParams.leftMargin = DensityUtil.dip2px(65);
                layoutParams.topMargin = DensityUtil.dip2px(13);
                appointDetailLineItemAdapter=new AppointDetailLineDetailAdapter(datas,mContext);
            }
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
            linearLayoutManager.setAutoMeasureEnabled(true);
            mLvLine.setNestedScrollingEnabled(false);
            mLvLine.setHasFixedSize(true);
            mLvLine.setAdapter(appointDetailLineItemAdapter);
            mLvLine.setLayoutManager(linearLayoutManager);
        }else {
            appointDetailLineItemAdapter.notifiyData(datas);
        }
        setLineHeight();
    }
}
