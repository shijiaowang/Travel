package com.example.administrator.travel.ui.me.myappoint;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.me.bulltetinboard.BulletinBoardActivity;
import com.example.administrator.travel.ui.me.memberdetail.MemberDetailActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;


import java.util.List;

/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class MyAppointAdapter extends TravelBaseAdapter<Object> {

    public MyAppointAdapter(Context mContext, List<Object> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(0) instanceof MyAppointTogetherBean.DataBean){
            return TYPE_POST_NORMAL;
        }else {
            return TYPE_POST_USER;
        }
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final Object item, int position) {
        if (item == null) {
            return;
        }
        //启动公告板页面
        if (baseHolder instanceof MyAppointTogetherHolder) {
            MyAppointTogetherBean.DataBean item1 = (MyAppointTogetherBean.DataBean) item;
            final String id = item1.getId();
            MyAppointTogetherHolder myAppointSuccessHolder = (MyAppointTogetherHolder) baseHolder;
            myAppointSuccessHolder.mRlBulletinBoard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BulletinBoardActivity.class);
                    intent.putExtra(IVariable.DATA,id);
                    mContext.startActivity(intent);
                }
            });
            myAppointSuccessHolder.mRlMemberDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MemberDetailActivity.class);
                    intent.putExtra(IVariable.DATA,id);
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    protected BaseHolder initHolder(int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType==TYPE_POST_NORMAL) {
            return new MyAppointTogetherHolder(mContext);
        }else {
            return new MyAppointingWithMeHolder(mContext);
        }
    }
}
