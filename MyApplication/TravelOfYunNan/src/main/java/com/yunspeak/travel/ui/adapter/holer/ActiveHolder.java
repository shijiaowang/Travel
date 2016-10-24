package com.yunspeak.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Active;

import org.xutils.x;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class ActiveHolder extends BaseHolder<Active.DataBean> {
    @BindView(R.id.tv_air) TextView mTvAir;
    @BindView(R.id.tv_type) TextView mTvType;
    @BindView(R.id.tv_people) TextView mTvPeople;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.iv_bg) ImageView mIvBg;

    public ActiveHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Active.DataBean datas, Context mContext, int position) {
         mTvName.setText(datas.getTitle());
        mTvPeople.setText(datas.getNow_people()+"人参赛");
        mTvType.setText(datas.getType().equals("1")?"线下活动":"线上活动");
        x.image().bind(mIvBg,datas.getActivity_img());
    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_active);

        return inflate;
    }
}
