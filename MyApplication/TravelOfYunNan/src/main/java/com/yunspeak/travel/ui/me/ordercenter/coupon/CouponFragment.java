package com.yunspeak.travel.ui.me.ordercenter.coupon;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.me.ordercenter.CouponBean;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.CouponAdapter;
import java.util.List;


/**
 * Created by wangyang on 2016/8/14.
 */
public class CouponFragment extends LoadAndPullBaseFragment<CouponEvent, CouponDataBean, CouponBean> {


    RelativeLayout rlEmpty;
    private Button mBtAdd;

    @Override
    protected void initListener() {
        super.initListener();
        mVsContent.setLayoutResource(R.layout.fragment_coupon);
        mVsContent.inflate();
        mBtAdd = (Button) inflate.findViewById(R.id.bt_add);
        rlEmpty = (RelativeLayout) inflate.findViewById(R.id.rl_empty);
    }

    @Override
    public void onSuccess(CouponEvent couponEvent) {
        super.onSuccess(couponEvent);
        if (couponEvent.getCode()==2){
            rlEmpty.setVisibility(View.VISIBLE);
        }else {
            rlEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onFail(CouponEvent couponEvent) {
        super.onFail(couponEvent);
        if (couponEvent.getCode()==2){
            rlEmpty.setVisibility(View.VISIBLE);
        }else {
            rlEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    protected boolean isUseChildEmpty() {
        return true;
    }

    @Override
    protected String initUrl() {
        return IVariable.MY_COUPON;
    }

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<CouponBean> httpData) {
        return new CouponAdapter(getContext(), httpData, true);
    }


}
