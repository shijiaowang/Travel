package com.example.administrator.travel.ui.appoint.choicesequipment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/31 0031.
 * 选择道具类型
 */
public class ChoicePropsLeftHolder extends BaseHolder<ChoicePropBean.DataBean.ProptypeBean> {
    @ViewInject(R.id.tv_type)
    private TextView mTvType;
    @ViewInject(R.id.v_cursor)
    private View mVCursor;
    @ViewInject(R.id.tv_cursor)
    private TextView mTvCursor;
    public ChoicePropsLeftHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(ChoicePropBean.DataBean.ProptypeBean datas, Context mContext, int position) {
        if (GlobalValue.choicePropType==position){
            mTvCursor.setVisibility(View.VISIBLE);
            mVCursor.setVisibility(View.VISIBLE);
        }else {
            mTvCursor.setVisibility(View.GONE);
            mVCursor.setVisibility(View.GONE);
        }
        mTvType.setText(datas.getName());

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activiity_choice_props_left);
    }
}
