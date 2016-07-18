package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.FollowAndFan;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class FollowAndFanHolder extends BaseHolder<FollowAndFan> {

    public FollowAndFanHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(FollowAndFan datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_follow_and_fan, null);
        return inflate;
    }
}
