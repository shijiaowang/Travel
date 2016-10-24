package com.yunspeak.travel.ui.appoint.popwindow;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.SelectCommonBean;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/9 0009.
 */
public class AppointCommonPopRightHolder extends BaseHolder<SelectCommonBean> {
    @BindView(R.id.tv_type) TextView mTvType;
    @BindView(R.id.tv_ok) TextView mTvOk;
    public AppointCommonPopRightHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(SelectCommonBean datas, Context mContext, int position) {
           if (datas.isChecked()){
               mTvOk.setTextColor(mContext.getResources().getColor(R.color.otherTitleBg));
           }else {
               mTvOk.setTextColor(mContext.getResources().getColor(R.color.colordbdbdb));
           }
        mTvType.setText(datas.getName());
    }


    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_appoint_pop_right);
    }
}
