package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment;

import android.content.Context;

import com.yunspeak.travel.bean.ChoicePropBean;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/8/31 0031.
 * 选择装备
 */
public class ChoicePropsLeftAdapter extends TravelBaseAdapter<ChoicePropBean.DataBean.ProptypeBean> {
    public ChoicePropsLeftAdapter(Context mContext, List<ChoicePropBean.DataBean.ProptypeBean> mDatas) {
        super(mContext, mDatas);
    }



    @Override
    protected void initListener(BaseHolder baseHolder, ChoicePropBean.DataBean.ProptypeBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new ChoicePropsLeftHolder(mContext);
    }
}
