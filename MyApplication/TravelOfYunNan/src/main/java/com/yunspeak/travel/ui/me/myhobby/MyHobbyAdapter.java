package com.yunspeak.travel.ui.me.myhobby;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.bean.UserLabelBean;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by wangyang on 2016/9/28 0028.
 */

public class MyHobbyAdapter extends TravelBaseAdapter<UserLabelBean> {
    public MyHobbyAdapter(Context mContext, List<UserLabelBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final UserLabelBean item, int position) {
        MyHobbyHolder myHobbyHolder = (MyHobbyHolder) baseHolder;
        myHobbyHolder.mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas.contains(item)){
                    if (GlobalValue.count>=7){
                        ToastUtils.showToast("最多佩戴7个称号");
                        return;
                    }
                    EventBus.getDefault().post(item);
                    mDatas.remove(item);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new MyHobbyHolder(mContext);
    }
}
