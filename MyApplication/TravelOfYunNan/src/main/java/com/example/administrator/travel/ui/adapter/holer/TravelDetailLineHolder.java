package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointTogetherDetail;
import com.example.administrator.travel.ui.adapter.AppointDetailLineItemAdapter;
import com.example.administrator.travel.ui.view.DottedLineView;
import com.example.administrator.travel.ui.view.ToShowAllListView;

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
    public TravelDetailLineHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(List<AppointTogetherDetail.DataBean.RoutesBean> datas, final Context mContext, int position) {
        mTvTime.setText(datas.get(0).getTime());
        mTvNumber.setText((position + 1) + "");
        List<String> list=new ArrayList<>();
        for (AppointTogetherDetail.DataBean.RoutesBean routesBean:datas){
            list.add(routesBean.getTitle());
        }
        mLvLine.setAdapter(new AppointDetailLineItemAdapter(mContext,list));
        mLvLine.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLvLine.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = mLvLine.getHeight();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mDlvLine.getLayoutParams();
                layoutParams.height = layoutParams.height+height;
                mDlvLine.setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_appoint_detail_line);
    }
}
