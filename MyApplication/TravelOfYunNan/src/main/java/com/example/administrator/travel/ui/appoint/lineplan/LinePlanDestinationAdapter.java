package com.example.administrator.travel.ui.appoint.lineplan;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by wangyang on 2016/9/12 0012.
 */
public class LinePlanDestinationAdapter extends TravelBaseAdapter<LineBean.Destination> {
    public LinePlanDestinationAdapter(Context mContext, List<LineBean.Destination> mDatas) {
        super(mContext, mDatas);
    }
    @Override
    protected void initListener(BaseHolder baseHolder, final LineBean.Destination item,  int position) {
        LinePlanDestinationHolder holder = (LinePlanDestinationHolder) baseHolder;
        //移除数据
        holder.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(item);
                GlobalValue.mSelectSpot.remove(item.getId());
                LinePlanEvent linePlanEvent = new LinePlanEvent();
                linePlanEvent.setIsDelete(true);
                EventBus.getDefault().post(linePlanEvent);
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new LinePlanDestinationHolder(mContext);
    }
}
