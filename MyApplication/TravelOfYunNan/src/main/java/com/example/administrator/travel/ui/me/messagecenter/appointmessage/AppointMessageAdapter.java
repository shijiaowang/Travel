package com.example.administrator.travel.ui.me.messagecenter.appointmessage;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.me.messagecenter.relateme.detailmessage.CommonMessageBean;

import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 */
public class AppointMessageAdapter extends TravelBaseAdapter<CommonMessageBean.DataBean> {
    public AppointMessageAdapter(Context mContext, List<CommonMessageBean.DataBean> mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, CommonMessageBean.DataBean item, int position) {
        final AppointMessageHolder appointMessageHolder = (AppointMessageHolder) baseHolder;
        appointMessageHolder.mTvCatMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appointMessageHolder.mTvMessage.isShowAll()){
                    appointMessageHolder.mTvMessage.setShowAll(false);
                    appointMessageHolder.mTvCatMore.setText(R.string.close_more);

                }else {
                    appointMessageHolder.mTvMessage.setShowAll(true);

                    appointMessageHolder.mTvCatMore.setText(R.string.cat_more);
                }
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointMessageHolder(mContext);
    }
}
