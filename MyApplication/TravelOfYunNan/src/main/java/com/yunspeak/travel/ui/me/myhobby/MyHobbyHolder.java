package com.yunspeak.travel.ui.me.myhobby;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserLabelBean;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/28 0028.
 */

public class MyHobbyHolder extends BaseHolder<UserLabelBean> {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    public MyHobbyHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(UserLabelBean datas, Context mContext, int position) {
        mTvTitle.setText(datas.getName());
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_flow_green);
    }
}
