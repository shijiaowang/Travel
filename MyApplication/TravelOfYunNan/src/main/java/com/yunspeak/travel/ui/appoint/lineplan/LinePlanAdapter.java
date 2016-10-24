package com.yunspeak.travel.ui.appoint.lineplan;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.appoint.selectdestination.SelectDestinationActivity;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/8/5 0005.
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
        if (baseHolder instanceof LinePlanBottomHolder){
            final LinePlanBottomHolder linePlanBottomHolder = (LinePlanBottomHolder) baseHolder;
            linePlanBottomHolder.mTvEndAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EnterAppointDialog.showDialogAddDestination(mContext, linePlanBottomHolder.mTvEndAdd,false);
                }
            });
        }
        if (baseHolder instanceof LinePlanTopHolder){
            final LinePlanTopHolder linePlanTopHolder = (LinePlanTopHolder) baseHolder;
            linePlanTopHolder.mTvStartAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EnterAppointDialog.showDialogAddDestination(mContext,linePlanTopHolder.mTvStartAdd, true);
                }
            });
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_POST_OP;//集合地
        }else if (position==mDatas.size()-1){
            return TYPE_POST_USER;//解散地
        }
        return TYPE_POST_NORMAL;//路线
    }

    @Override
    protected BaseHolder initHolder(int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType==TYPE_POST_USER){
            return new LinePlanBottomHolder(mContext);
        }else if (itemViewType==TYPE_POST_OP){
            return new LinePlanTopHolder(mContext);
        }
        return new LinePlanHolder(mContext);
    }
}
