package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.travel.bean.AppointTogether;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.AppointTogetherDetailActivity;
import com.example.administrator.travel.ui.adapter.holer.AppointTogetherHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class AppointTogetherAdapter extends TravelBaseAdapter<AppointTogether.DataBean> {
    public AppointTogetherAdapter(Context mContext, List<AppointTogether.DataBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final AppointTogether.DataBean item, final int position) {
        AppointTogetherHolder appointTogetherHolder = (AppointTogetherHolder) baseHolder;
        appointTogetherHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppointTogetherDetailActivity.class);
                intent.putExtra(IVariable.T_ID,item.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointTogetherHolder(mContext);
    }
}
