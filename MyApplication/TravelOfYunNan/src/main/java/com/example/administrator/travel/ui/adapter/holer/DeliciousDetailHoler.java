package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.DeliciousDetail;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DeliciousDetailHoler extends BaseHolder<DeliciousDetail> {
    @ViewInject(R.id.rb_star)
    private RatingBar mRbStar;

    public DeliciousDetailHoler(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(DeliciousDetail datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate =inflateView(R.layout.item_activity_delicious_top_store);


        return inflate;
    }
}
