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
public class AppointCommonPopLeftHolder extends BaseHolder<SelectCommonBean> {
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_cursor)
    private TextView mTvCursor;
    public AppointCommonPopLeftHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(SelectCommonBean datas, Context mContext, int position) {
        mTvName.setText(datas.getName());
           if (datas.isChecked()){
               mTvName.setTextColor(mContext.getResources().getColor(R.color.otherTitleBg));
               mTvCursor.setTextColor(mContext.getResources().getColor(R.color.otherTitleBg));
           }else {
               mTvName.setTextColor(mContext.getResources().getColor(R.color.color4d4d4d));
               mTvCursor.setTextColor(mContext.getResources().getColor(R.color.color4d4d4d));
           }

    }


    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_appoint_pop_left);
    }
}
