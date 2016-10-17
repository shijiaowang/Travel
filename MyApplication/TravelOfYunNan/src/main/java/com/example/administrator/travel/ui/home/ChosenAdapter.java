package com.example.administrator.travel.ui.home;

import android.content.Context;

import com.example.administrator.travel.bean.Chosen;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 主页精选
 */
public class ChosenAdapter extends BaseRecycleViewHolder<Chosen> {

    public ChosenAdapter(Context mContext, List<Chosen> mDatas) {
        super(mContext, mDatas);


    }
   //测试数据
    @Override
    protected int testDataSize() {
        return 4;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Chosen item, int position) {

    }


    @Override
    protected BaseHolder initHolder(int position) {

        return new ChosenHolder(super.mContext);
    }
}
