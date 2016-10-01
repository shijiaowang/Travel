package com.example.administrator.travel.ui.me.myappoint;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.me.bulltetinboard.BulletinBoardActivity;
import com.example.administrator.travel.ui.appoint.memberdetail.MemberDetailActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;


import java.util.List;

/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class MyAppointAdapter extends TravelBaseAdapter<MyAppointBean.DataBean.TravelPlanBean> {
    public MyAppointAdapter(Context mContext, List<MyAppointBean.DataBean.TravelPlanBean> mDatas) {
        super(mContext, mDatas);
    }



    @Override
    protected void initListener(BaseHolder baseHolder, MyAppointBean.DataBean.TravelPlanBean item, int position) {
        if (item == null) {
            return;
        }
        //启动公告板页面
        if (baseHolder instanceof MyAppointSuccessHolder) {
            MyAppointSuccessHolder myAppointSuccessHolder = (MyAppointSuccessHolder) baseHolder;
            myAppointSuccessHolder.mRlBulletinBoard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, BulletinBoardActivity.class));
                }
            });
            myAppointSuccessHolder.mRlMemberDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, MemberDetailActivity.class));
                }
            });
        }
    }


    @Override
    protected BaseHolder initHolder(int position) {

        return new MyAppointingHolder(mContext);
    }
}
