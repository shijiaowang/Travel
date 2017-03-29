package com.yunspeak.travel.ui.me.ordercenter.coupon;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yunspeak.travel.R;
import com.yunspeak.travel.aop.CheckNetwork;
import com.yunspeak.travel.databinding.CouponDataBinding;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.INetworkCallBack;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.global.SendTextClick;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseLoadAndRefreshFragment;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.me.ordercenter.coupon.model.Coupon;
import com.yunspeak.travel.ui.me.ordercenter.coupon.model.CouponModel;
import com.yunspeak.travel.ui.me.ordercenter.coupon.model.CouponRecycleModel;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import java.util.Map;


/**
 * Created by wangyang on 2016/8/14.
 * 优惠券页面
 */
public class CouponFragment extends BaseLoadAndRefreshFragment<Coupon,CouponModel> {

    private CouponDataBinding couponDataBinding;
    private CouponRecycleModel couponRecycleModel;


    @Override
    protected BasePullAndRefreshModel<CouponModel> initModel() {
        couponRecycleModel = new CouponRecycleModel();
        return couponRecycleModel;
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        couponDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coupon, container, false);
        couponDataBinding.getRoot().findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @CheckNetwork
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showInputTextView(InputType.TYPE_TEXT_VARIATION_PASSWORD,v, "输入优惠券验证码", "激活优惠券", "激活", new SendTextClick() {
                    @Override
                    public void onClick(String text) {
                        Map<String, String> end = MapUtils.Build().addKey().addUserId().addContent(text).end();
                        HttpClient.getInstance().postData(IRequestUrl.ACTIVATE_COUPONS, end, new INetworkCallBack<TravelsObject>() {
                            @Override
                            public void accept(@NonNull TravelsObject travelsObject) throws Exception {
                                ToastUtils.showToast(travelsObject.getMessage());
                                couponRecycleModel.onLoadMoreListener().onLoadMore();//激活成功加载更多
                            }
                            @Override
                            public void error(Throwable throwable) {
                                ToastUtils.showToast("激活失败:"+throwable.getMessage());
                            }
                        },getContext());
                    }
                });
            }
        });
        return couponDataBinding.getRoot();
    }

    @Override
    protected void onReceive(Coupon datas) {
        couponDataBinding.setBase(couponRecycleModel);
        couponDataBinding.setManagerType(0);

    }
}
