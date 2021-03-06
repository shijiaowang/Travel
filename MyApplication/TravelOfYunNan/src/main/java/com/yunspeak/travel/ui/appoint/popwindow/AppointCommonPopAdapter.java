package com.yunspeak.travel.ui.appoint.popwindow;

import android.content.Context;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.bean.CityBean;

import java.util.List;

/**
 * Created by wangyang on 2016/9/9 0009.
 * 公用Adapter
 */
public class AppointCommonPopAdapter extends TravelBaseAdapter<CityBean> {

   public static final int LEFT=0;//左边
    public static final int RIGHT=1;//右边
    private int type=-1;

    public AppointCommonPopAdapter(Context mContext, List<CityBean> mDatas, int type) {
        super(mContext, mDatas);
        this.type = type;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, CityBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType==RIGHT){
            return new AppointCommonPopRightHolder(mContext);
        }
        return new AppointCommonPopLeftHolder(mContext);
    }
}
