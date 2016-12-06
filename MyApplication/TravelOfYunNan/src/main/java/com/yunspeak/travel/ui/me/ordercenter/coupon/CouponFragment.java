package com.yunspeak.travel.ui.me.ordercenter.coupon;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.SendTextClick;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.me.ordercenter.CouponBean;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.CouponAdapter;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;


/**
 * Created by wangyang on 2016/8/14.
 */
public class CouponFragment extends LoadAndPullBaseFragment<CouponEvent, CouponDataBean, CouponBean> {

   boolean isFirst=true;
    RelativeLayout rlEmpty;
    private Button mBtAdd;

    @Override
    protected void initListener() {
        super.initListener();
        mVsContent.setLayoutResource(R.layout.fragment_coupon);
        mVsContent.inflate();
        mBtAdd = (Button) inflate.findViewById(R.id.bt_add);
        rlEmpty = (RelativeLayout) inflate.findViewById(R.id.rl_empty);
        mBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showInputTextView(getContext(),mBtAdd, "输入优惠券验证码", "激活优惠券", "激活", new SendTextClick() {
                    @Override
                    public void onClick(String text) {
                        Map<String, String> end = MapUtils.Build().addKey().addUserId().addContent(text).end();
                        XEventUtils.postUseCommonBackJson(IVariable.ACTIVATE_COUPONS,end,TYPE_UPDATE,new CouponEvent());
                    }
                });
            }
        });
    }

    @Override
    public void onSuccess(CouponEvent couponEvent) {
        super.onSuccess(couponEvent);
        if (couponEvent.getType()==TYPE_UPDATE){
            ToastUtils.showToast("激活成功");
            onLoad(TYPE_LOAD);
        }
        showIsEmpty(couponEvent);
        isFirst=false;
    }

    /**
     * 显示为空的情况
     * @param couponEvent
     */
    private void showIsEmpty(CouponEvent couponEvent) {
        if (!isFirst)return;
        if (couponEvent.getCode()==2){
            rlEmpty.setVisibility(View.VISIBLE);
        }else {
            rlEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onFail(CouponEvent couponEvent) {
        super.onFail(couponEvent);
        showIsEmpty(couponEvent);
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
