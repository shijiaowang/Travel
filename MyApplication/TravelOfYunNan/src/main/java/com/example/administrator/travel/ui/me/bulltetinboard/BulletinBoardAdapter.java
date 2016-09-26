package com.example.administrator.travel.ui.me.bulltetinboard;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class BulletinBoardAdapter extends TravelBaseAdapter<BulletinBoardBean.DataBean> {
    public BulletinBoardAdapter(Context mContext, List<BulletinBoardBean.DataBean> mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, BulletinBoardBean.DataBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new BulletinBoardHolder(mContext);
    }
}
