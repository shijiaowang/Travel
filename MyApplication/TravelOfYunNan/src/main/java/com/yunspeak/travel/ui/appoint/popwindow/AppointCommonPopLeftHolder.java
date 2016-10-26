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
public class AppointCommonPopLeftHolder extends BaseHolder<SelectCommonBean> {
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_cursor) TextView mTvCursor;
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
