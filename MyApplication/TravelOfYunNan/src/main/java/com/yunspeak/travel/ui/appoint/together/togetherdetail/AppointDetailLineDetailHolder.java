package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.AppointTogetherDetailBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.findcommon.BaseFindDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FrescoUtils;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/5 0005.
 */
public class AppointDetailLineDetailHolder extends BaseRecycleViewHolder<AppointTogetherDetailBean.DataBean.RoutesBean> {
    @BindView(R.id.iv_bg) SimpleDraweeView mIvBg;
    @BindView(R.id.tv_des) TextView mTvDes;
    @BindView(R.id.tv_address) TextView mTvAddress;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindString(R.string.item_fragment_find_add) String addressIcon;

    public AppointDetailLineDetailHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void childBindView(int position, final AppointTogetherDetailBean.DataBean.RoutesBean datas, final Context mContext) {
        FrescoUtils.displayNormal(mIvBg,datas.getLogo_img(),300,200,R.drawable.normal_2_1);
        AiteUtils.setIconText(addressIcon,datas.getAddress(),mContext,mTvAddress);
        mTvDes.setText(datas.getProvince()+datas.getContent());
        mTvName.setText(datas.getCity()+" · "+datas.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DestinationDetailActivity.startShareElement(mContext,datas.getId(),datas.getTitle(),mIvBg,datas.getLogo_img(), BaseFindDetailActivity.TYPE_DESTINATION);
            }
        });
    }
}
