package com.example.administrator.travel.ui.appoint.lineplan;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.travel.bean.Line;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.selectdestination.SelectDestinationActivity;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class LinePlanAdapter extends TravelBaseAdapter<LineBean> {
    public LinePlanAdapter(Context mContext, List<LineBean> mDatas) {
        super(mContext, mDatas);
    }



    @Override
    protected void initListener(BaseHolder baseHolder,  LineBean item, final int position) {
        if (baseHolder instanceof LinePlanHolder){
            final LinePlanHolder linePlanHolder = (LinePlanHolder) baseHolder;
            linePlanHolder.mTvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SelectDestinationActivity.class);
                    intent.putExtra(IVariable.POSITION,position);
                    mContext.startActivity(intent);
                }
            });
        }
    }



    @Override
    protected BaseHolder initHolder(int position) {
        if (position==mDatas.size()-1){
            return new LinePlanBottomHolder(mContext);
        }
        return new LinePlanHolder(mContext);
    }
}
