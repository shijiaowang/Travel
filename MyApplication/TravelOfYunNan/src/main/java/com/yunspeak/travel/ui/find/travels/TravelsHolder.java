package com.yunspeak.travel.ui.find.travels;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.FindTravelsBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.findcommon.BaseFindDetailActivity;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/25 0025.
 */
public class TravelsHolder extends BaseRecycleViewHolder<FindTravelsBean.DataBean> {
    @BindView(R.id.iv_picture) SimpleDraweeView mIvPicture;
    @BindView(R.id.iv_user_icon) SimpleDraweeView mIvUserIcon;
    @BindView(R.id.tv_nick_name) TextView mTvNickName;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.tv_time)TextView mTvTime;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;

    public TravelsHolder(View itemView) {
        super(itemView);
    }



    @Override
    public void childBindView(int position, final FindTravelsBean.DataBean datas, final Context mContext) {
        FrescoUtils.displayNormal(mIvPicture,datas.getLogo_img(),640,360,R.drawable.normal_2_1);
        FrescoUtils.displayIcon(mIvUserIcon,datas.getTitle_img());
        mTvNickName.setText(datas.getAuthor());
        mTvContent.setText(datas.getTitle());
        mTvWatchNumber.setText(datas.getBrowse());
        mTvTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_D,datas.getAdd_time()));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TravelsDetailActivity.startShareElement(mContext,datas.getId(),datas.getTitle(),mIvPicture,datas.getLogo_img(), BaseFindDetailActivity.TYPE_TRAVELS);
            }
        });
    }
}
