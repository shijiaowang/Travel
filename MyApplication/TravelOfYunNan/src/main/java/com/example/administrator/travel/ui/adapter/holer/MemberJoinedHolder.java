package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.view.FontsIconTextView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/4 0004.
 */
public class MemberJoinedHolder extends BaseHolder {

    @BindView(R.id.tv_sex) FontsIconTextView mTvSex;

    public MemberJoinedHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_member_joined);
    }
}
