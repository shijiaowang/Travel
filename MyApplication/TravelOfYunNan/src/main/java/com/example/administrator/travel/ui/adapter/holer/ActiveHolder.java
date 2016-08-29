package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Active;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class ActiveHolder extends BaseHolder<Active.DataBean> {
    @ViewInject(R.id.tv_air)
    private TextView mTvAir;
    @ViewInject(R.id.tv_type)
    private TextView mTvType;
    @ViewInject(R.id.tv_people)
    private TextView mTvPeople;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.iv_bg)
    private ImageView mIvBg;

    public ActiveHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Active.DataBean datas, Context mContext) {
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
