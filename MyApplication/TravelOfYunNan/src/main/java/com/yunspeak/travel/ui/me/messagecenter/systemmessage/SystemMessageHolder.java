package com.yunspeak.travel.ui.me.messagecenter.systemmessage;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;

import com.yunspeak.travel.bean.SystemMessageBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.view.ShowAllTextView;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/26 0026.
 */
public class SystemMessageHolder extends BaseRecycleViewHolder<SystemMessageBean.DataBean> {
    @BindView(R.id.tv_cat_more)
    TextView mTvCatMore;
    @BindView(R.id.tv_message)
    ShowAllTextView mTvMessage;
    @BindView(R.id.iv_icon)
    SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_time)
    TextView mTvTime;

    public SystemMessageHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, SystemMessageBean.DataBean datas, Context mContext) {
        FrescoUtils.displayNormal(mIvIcon,datas.getImg());
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm",datas.getAdd_time()));
        mTvType.setText(datas.getTitle());
        mTvMessage.setLimitContent(datas.getContent());
        if (mTvMessage.isShowAll()) {
            mTvCatMore.setVisibility(View.GONE);
        } else {
            mTvCatMore.setVisibility(View.VISIBLE);
            mTvCatMore.setText(R.string.cat_more);
            mTvCatMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTvMessage.isShowAll()) {
                        mTvMessage.setShowAll(false);
                        mTvCatMore.setText(R.string.cat_more);
                    } else {
                        mTvMessage.setShowAll(true);
                        mTvCatMore.setText(R.string.close_more);
                    }
                }
            });
        }
    }
}
