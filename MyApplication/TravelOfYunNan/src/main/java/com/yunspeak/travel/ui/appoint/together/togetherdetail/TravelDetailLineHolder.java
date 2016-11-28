package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.view.ToShowAllListView;

import org.xutils.common.util.DensityUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/4.
 */
public class TravelDetailLineHolder extends BaseHolder<List<AppointTogetherDetailBean.DataBean.RoutesBean>> {
    private final boolean isDetail;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.dlv_line) View mDlvLine;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.lv_line) ToShowAllListView mLvLine;
    @BindView(R.id.iv_cursor)
    ImageView mIvCursor;
    private int [] cursors=new int[]{R.drawable.activity_line_add1,R.drawable.activity_line_add2,R.drawable.activity_line_add3,R.drawable.activity_line_add4,R.drawable.activity_line_add5,R.drawable.activity_line_add6,R.drawable.activity_line_add7};


    public TravelDetailLineHolder(Context context, boolean isDetail) {
        super(context);
        this.isDetail = isDetail;
    }


    @Override
    protected void initItemDatas(final List<AppointTogetherDetailBean.DataBean.RoutesBean> datas, final Context mContext, int position) {
        mTvTime.setText(datas.get(0).getTime());
        mTvNumber.setText("Day\n"+(position + 1));
        mIvCursor.setImageResource(cursors[position%cursors.length]);
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
