package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.AppointTogetherDetailBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/5 0005.
 */
public class AppointDetailLineItemHolder extends BaseRecycleViewHolder<AppointTogetherDetailBean.DataBean.RoutesBean> {
    @BindView(R.id.tv_spot_name) TextView mTvSpotName;

    public AppointDetailLineItemHolder(View itemView) {
        super(itemView);
    }
    @Override
    public void childBindView(int position, final AppointTogetherDetailBean.DataBean.RoutesBean datas, final Context mContext) {
        mTvSpotName.setText(datas.getCity()+" · "+datas.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DestinationDetailActivity.start(mContext,datas.getId(),datas.getTitle());
            }
        });
    }
}
