package com.yunspeak.travel.ui.appoint.together;
import android.content.Context;
import android.view.ViewGroup;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import java.util.List;


/**
 * Created by wangyang on 2016/7/20 0020.
 */
public class AppointTogetherAdapter extends BaseRecycleViewAdapter<AppointTogetherBean.DataBean> {


    public AppointTogetherAdapter(List<AppointTogetherBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<AppointTogetherBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppointTogetherHolder(inflateView(R.layout.item_fragment_appoint_play_together,parent));
    }
}
