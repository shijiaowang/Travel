package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.DeliciousDetail;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DeliciousDetailHoler extends BaseHolder<DeliciousDetail> {

    private RatingBar mRbStar;

    public DeliciousDetailHoler(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(DeliciousDetail datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_delicious_top_store, null);
        mRbStar = (RatingBar) inflate.findViewById(R.id.rb_star);
        mRbStar.setRating(4.5f);
        return inflate;
    }
}
