package com.example.administrator.travel.ui.appoint.lineplan;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class LinePlanDestinationAdapter extends TravelBaseAdapter<String> {
    public LinePlanDestinationAdapter(Context mContext, List<String> mDatas) {
        super(mContext, mDatas);
    }



    @Override
    protected void initListener(BaseHolder baseHolder, final String item,  int position) {
        LinePlanDestinationHolder holder = (LinePlanDestinationHolder) baseHolder;
        //移除数据
        holder.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(item);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new LinePlanDestinationHolder(mContext);
    }
}
