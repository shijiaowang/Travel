package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;

import org.xutils.view.annotation.ViewInject;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class TitleManagementHolder extends BaseHolder {
    @BindView(R.id.tv_toggle)public TextView mTvToggle;
    @BindView(R.id.ll_toggle)public LinearLayout mLlToggle;
    public TitleManagementHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_fragment_title_management);
    }
}
