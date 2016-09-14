package com.example.administrator.travel.ui.appoint.lineplan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Line;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.view.DottedLineView;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.LogUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class LinePlanHolder extends BaseHolder<LineBean> {
    private  int total;
    @ViewInject(R.id.tv_add)
    public FontsIconTextView mTvAdd;
    @ViewInject(R.id.dlv_line)
    private DottedLineView mDlvLine;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.lv_add)
    private ListView mLvAdd;
    private FontsIconTextView mTvDelete;



    public LinePlanHolder(Context context) {
        super(context);

    }


    @Override
    protected void initItemDatas(LineBean datas, Context mContext, int position) {
        mTvTime.setText(datas.getTime());
        mTvNumber.setText((position + 1) + "");
        List<String> destinations = datas.getDestinations();
        if (destinations!=null && destinations.size()>0){
            mLvAdd.setAdapter(new LinePlanDestinationAdapter(mContext,destinations));
            initHeight();
        }else {
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

    private void initHeight() {
        mLvAdd.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLvAdd.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = mLvAdd.getHeight();
                ViewGroup.LayoutParams layoutParams = mDlvLine.getLayoutParams();
                layoutParams.height= DensityUtil.dip2px(58)+height;
                mDlvLine.setLayoutParams(layoutParams);

            }
        });
    }


    @Override
    public View initRootView(Context mContext) {

        return  inflateView(R.layout.item_activity_line_plan);
    }
}