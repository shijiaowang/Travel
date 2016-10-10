package com.example.administrator.travel.ui.appoint.lineplan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.view.DottedLineView;
import com.example.administrator.travel.ui.view.FontsIconTextView;

import org.xutils.common.util.DensityUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class LinePlanHolder extends BaseHolder<LineBean> {

    @BindView(R.id.tv_add) FontsIconTextView mTvAdd;
    @BindView(R.id.dlv_line) DottedLineView mDlvLine;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.lv_add)  ListView mLvAdd;




    public LinePlanHolder(Context context) {
        super(context);

    }


    @Override
    protected void initItemDatas(LineBean datas, Context mContext, int position) {
        mTvTime.setText(datas.getTime());
        mTvNumber.setText(position + "");
        List<LineBean.Destination> destinations = datas.getDestinations();
        if (destinations!=null && destinations.size()>0){
            mLvAdd.setVisibility(View.VISIBLE);
            mLvAdd.setAdapter(new LinePlanDestinationAdapter(mContext,destinations));
            initHeight(destinations.size());
        }else {
            mLvAdd.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams = mDlvLine.getLayoutParams();
            layoutParams.height= DensityUtil.dip2px(58);
            mDlvLine.setLayoutParams(layoutParams);
        }
        if (destinations!=null && destinations.size()>=3){
            mTvAdd.setVisibility(View.GONE);
        }else {
            mTvAdd.setVisibility(View.VISIBLE);
        }



    }

    private void initHeight(final int size) {
        mLvAdd.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLvAdd.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = mLvAdd.getHeight();
                ViewGroup.LayoutParams layoutParams = mDlvLine.getLayoutParams();
                int addHeight=0;
                if (size>=3)addHeight=-DensityUtil.dip2px(30);//减去添加框被隐藏的高度
                layoutParams.height= DensityUtil.dip2px(58)+height+addHeight;
                mDlvLine.setLayoutParams(layoutParams);

            }
        });
    }


    @Override
    public View initRootView(Context mContext) {

        return  inflateView(R.layout.item_activity_line_plan);
    }
}
