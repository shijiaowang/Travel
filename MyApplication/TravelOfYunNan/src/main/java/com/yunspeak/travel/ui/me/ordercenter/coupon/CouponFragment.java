package com.yunspeak.travel.ui.me.ordercenter.coupon;

import android.support.v4.app.Fragment;
import android.widget.Button;

import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.me.ordercenter.CouponBean;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.CouponAdapter;
import com.yunspeak.travel.ui.view.refreshview.XListView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/14.
 */
public class CouponFragment extends LoadAndPullBaseFragment<CouponEvent, CouponDataBean, CouponBean> {


    private Button mBtAdd;

    @Override
    protected void initListener() {
        super.initListener();
        mVsContent.setLayoutResource(R.layout.fragment_coupon);
        mVsContent.inflate();
        mBtAdd = (Button) inflate.findViewById(R.id.bt_add);
    }

    @Override
    protected String initUrl() {
        return IVariable.MY_COUPON;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<CouponBean> httpData) {
        return new CouponAdapter(getContext(),httpData,true);
    }

}
