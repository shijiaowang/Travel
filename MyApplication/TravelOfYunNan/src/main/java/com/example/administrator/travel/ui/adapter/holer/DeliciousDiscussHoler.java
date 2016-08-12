package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Discuss;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DeliciousDiscussHoler extends BaseHolder<Discuss> {
    public DeliciousDiscussHoler(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Discuss datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate =inflateView(R.layout.item_common_disscuss);
        TextView mTvLove = FontsIconUtil.findIconFontsById(R.id.tv_love, mContext, inflate);
        return inflate;
    }
}
