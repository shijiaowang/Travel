package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.view.FontsIconTextView;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class MemberJoinedHolder extends BaseHolder {

    @ViewInject(R.id.tv_sex)
    private FontsIconTextView mTvSex;

    public MemberJoinedHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_member_joined);
        return view;
    }
}
