package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Fan;

import org.xutils.view.annotation.ViewInject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class FanHolder extends BaseHolder<Fan.FanPeople> {
    @BindView(R.id.tv_nick_name) TextView mTvNickName;
    @BindView(R.id.iv_icon) CircleImageView mIvIcon;

    public FanHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Fan.FanPeople datas, Context mContext, int position) {
        mTvNickName.setText(datas.getNick_name());
    }



    @Override
    public View initRootView(Context mContext) {
        View inflate =inflateView(R.layout.item_activity_follow_and_fan);

        return inflate;
    }
}
