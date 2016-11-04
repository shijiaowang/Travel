package com.yunspeak.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailBean;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/26 0026.
 */
public class DeliciousDetailHoler extends BaseHolder<DeliciousDetailBean> {
    @BindView(R.id.rb_star) RatingBar mRbStar;

    public DeliciousDetailHoler(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(DeliciousDetailBean datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_delicious_top_store);
    }
}
