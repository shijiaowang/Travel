package com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost;

import android.content.Context;
import android.view.ViewGroup;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite.AiteFollow;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import java.util.List;

/**
 * Created by wangyang on 2016/11/2 0002.
 */

public class AitePeopleAdapter extends BaseRecycleViewAdapter<AiteFollow> {


    public AitePeopleAdapter(List<AiteFollow> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<AiteFollow> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AitePeopleHolder(inflateView(R.layout.item_activity_aite_people, parent));
    }
}
