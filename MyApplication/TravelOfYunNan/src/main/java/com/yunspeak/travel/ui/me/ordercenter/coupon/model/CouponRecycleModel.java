package com.yunspeak.travel.ui.me.ordercenter.coupon.model;

import android.support.v7.widget.RecyclerView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.BR;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.adapter.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.MyCollectionDecoration;

import java.util.List;

/**
 * Created by wangyang on 2017/3/29.
 */

public class CouponRecycleModel extends BasePullAndRefreshModel<CouponModel> {
    @Override
    protected RecyclerView.ItemDecoration initChildSpace() {
        return new MyCollectionDecoration(0,0);
    }

    @Override
    protected BaseRecycleViewAdapter<CouponModel> initAdapter(List<CouponModel> datas) {
        return new CommonRecycleViewAdapter<CouponModel>(datas,BR.couponModel, R.layout.item_fragment_coupon);
    }

    @Override
    public String url() {
        return IRequestUrl.MY_COUPON;
    }
}
