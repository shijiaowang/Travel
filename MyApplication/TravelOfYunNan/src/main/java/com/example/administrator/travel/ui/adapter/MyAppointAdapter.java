package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.travel.bean.MyAppoint;
import com.example.administrator.travel.ui.activity.BulletinBoardActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.MyAppointSuccessHolder;
import com.example.administrator.travel.ui.adapter.holer.MyAppointingHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class MyAppointAdapter extends TravelBaseAdapter<MyAppoint> {
    public MyAppointAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 5;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, MyAppoint item) {
        if (item == null) {
            return;
        }
        //启动公告板页面
        if (item.isSuccess() && baseHolder instanceof MyAppointSuccessHolder) {
            MyAppointSuccessHolder myAppointSuccessHolder = (MyAppointSuccessHolder) baseHolder;
            myAppointSuccessHolder.mRlBulletinBoard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, BulletinBoardActivity.class));
                }
            });
        }
    }


    @Override
    protected BaseHolder initHolder(int position) {

        if (mDatas.get(position).isSuccess()) {
            return new MyAppointSuccessHolder(mContext);
        }
        return new MyAppointingHolder(mContext);
    }
}
