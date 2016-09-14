package com.example.administrator.travel.ui.appoint.popwindow;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.SelectCommonBean;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class AppointCommonPopRightHolder extends BaseHolder<SelectCommonBean> {
    @ViewInject(R.id.tv_type)
    private TextView mTvType;
    @ViewInject(R.id.tv_ok)
    private TextView mTvOk;
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
