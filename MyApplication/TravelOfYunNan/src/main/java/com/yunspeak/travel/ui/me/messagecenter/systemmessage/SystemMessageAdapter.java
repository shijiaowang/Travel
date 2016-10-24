package com.yunspeak.travel.ui.me.messagecenter.systemmessage;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class SystemMessageAdapter extends TravelBaseAdapter<SystemMessageBean.DataBean> {
    public SystemMessageAdapter(Context mContext, List<SystemMessageBean.DataBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, SystemMessageBean.DataBean item, int position) {
        final SystemMessageHolder systemMessageHolder = (SystemMessageHolder) baseHolder;
        systemMessageHolder.mTvCatMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (systemMessageHolder.mTvMessage.isShowAll()){
                    systemMessageHolder.mTvMessage.swithShow(false);
                    systemMessageHolder.mTvCatMore.setText(R.string.close_more);
                }else {
                    systemMessageHolder.mTvMessage.swithShow(true);
                    systemMessageHolder.mTvCatMore.setText(R.string.cat_more);
                }
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new SystemMessageHolder(mContext);
    }
}
