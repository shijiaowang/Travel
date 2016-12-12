package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.createsuccess.CreateAppointSuccessActivity;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.home.welcome.splash.register.CityoffSpeak;
import com.yunspeak.travel.ui.me.myalbum.createalbum.CreateAlbumBean;
import com.yunspeak.travel.ui.me.ordercenter.BasecPriceBean;
import com.yunspeak.travel.ui.me.ordercenter.CouponBean;
import com.yunspeak.travel.ui.me.ordercenter.OrdersCenterActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.PayNotifyEvent;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.payresult.PayResultActivity;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/12 0012.
 * 确认订单
 */
public class ConfirmOrdersActivity extends BaseNetWorkActivity<ConfirmOrdersEvent> implements View.OnClickListener {
    public static final int PAY_WAY_ZFB = 1;
    public static final int PAY_WAY_WX = 2;
    public static final int SUBMIT_NEW = 45;
    private int currentPayWay = -1;
    @BindView(R.id.tv_pay_zfb)
    TextView mTvPayZfb;
    @BindView(R.id.tv_pay_wx)
    TextView mTvPayWx;
    @BindView(R.id.rv_coupon)
    RecyclerView mRvConpun;
    @BindView(R.id.rl_zfb)
    RelativeLayout mRlZfb;
    @BindView(R.id.rl_wx)
    RelativeLayout mRlWx;
    @BindView(R.id.cb_agree)
    CheckBox mCbAgree;
    @BindView(R.id.lv_price)
    ListView mLvPrice;
    @BindView(R.id.tv_route_price)
    TextView mTvPriceRoute;
    @BindView(R.id.tv_order_id)
    TextView mTvOrderId;
    @BindView(R.id.tv_reduce_price)
    TextView mTvReducePrice;
    @BindView(R.id.tv_pay)
    TextView mTvPay;
    @BindView(R.id.bt_submit)
    Button mAcpSubmit;
    @BindView(R.id.tv_order_name)
    TextView mTvOrderName;
    @BindView(R.id.tv_appoint) TextView mTvAppoint;
    private List<String> mConpous = new ArrayList<>();


    private List<TextView> selectPayWay = new ArrayList<>();
    private String id;
    private CouponAdapter ordersCouponAdapter;
    private List<CouponBean> orderConpou;
    private float totalReduces = 0f;
    private float totalPay = 0f;
    private static final int SDK_PAY_FLAG = 1;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        startShowDetail(true,false);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        startShowDetail(false,false);
                    }
                    break;
                }
            }
        }
    };

    /**
     * 显示详情
     */
    private void startShowDetail(boolean isSuccess,boolean isWx) {
        EventBus.getDefault().post(new PayNotifyEvent());
        if (payType.equals("1") && isSuccess){
            Intent intent=new Intent(this,CreateAppointSuccessActivity.class);
            intent.putExtra(IVariable.ID,id);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, PayResultActivity.class);
            intent.putExtra("result", isSuccess ? "支付成功" : "支付失败");
            intent.putExtra("des", isSuccess ? "订单等待处理" : "订单支付失败");
            intent.putExtra("isWX", isWx);
            startActivityForResult(intent, REQ_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE && resultCode==RESULT_CODE){
            if (OrdersCenterActivity.instance==null){
                startActivity(new Intent(this, OrdersCenterActivity.class));
            }
            finish();
        }
    }

    private int prePosition = -1;
    private float currentPrice;
    private String payType;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_confirm_orders;
    }

    private void init() {
        id = getIntent().getStringExtra(IVariable.ID);
        payType = getIntent().getStringExtra("pay_type");
        selectPayWay.add(mTvPayZfb);
        selectPayWay.add(mTvPayWx);
    }


    @Override
    protected void initEvent() {
        init();
        mAcpSubmit.setOnClickListener(this);
        mRlZfb.setOnClickListener(this);
        mRlWx.setOnClickListener(this);
        String text="同意《城外旅游软件许可及服务协议》";
        SpannableString spannableString=new SpannableString(text);
        int startIndex=text.indexOf("《");
        spannableString.setSpan(new CityoffSpeak(this),startIndex,text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvAppoint.setText(spannableString);
        mTvAppoint.setMovementMethod(LinkMovementMethod.getInstance());

    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.addId(id).add("pay_type",payType);

    }

    @Override
    protected String initUrl() {
        return IVariable.PAY_ORDERS;
    }

    @Override
    protected void onSuccess(ConfirmOrdersEvent confirmOrdersEvent) {
        switch (confirmOrdersEvent.getType()) {
            case TYPE_REFRESH:
                dealRefresh(confirmOrdersEvent);
                break;
            case SUBMIT_NEW:
                switch (currentPayWay) {
                    case PAY_WAY_ZFB:
                        CreateAlbumBean order = GsonUtils.getObject(confirmOrdersEvent.getResult(), CreateAlbumBean.class);
                        payZFB(order);
                        break;
                    case PAY_WAY_WX:
                        WXPayBean wxPayBean = GsonUtils.getObject(confirmOrdersEvent.getResult(), WXPayBean.class);
                        WXPayBean.DataBean dataBean = wxPayBean.getData();
                        IWXAPI wxapi = WXAPIFactory.createWXAPI(this,null);
                        try {
                            PayReq req = new PayReq();
                            req.appId = dataBean.getAppid();
                            req.partnerId = dataBean.getPartnerid();
                            req.prepayId = dataBean.getPrepayid();
                            req.nonceStr = dataBean.getNoncestr();
                            req.timeStamp = String.valueOf(dataBean.getTimestamp());
                            req.packageValue = dataBean.getPackageX();
                            req.sign = dataBean.getSign();
                            req.extData = "城外旅游订单";
                            wxapi.sendReq(req);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showToast("支付出错了");
                        }
                        break;
                }


        }
    }

    private void payZFB(CreateAlbumBean order) {
       final String info = order.getData();
        if (StringUtils.isEmpty(info)) {
            ToastUtils.showToast("订单信息错误！");
            return;
        }
        //final String   info="app_id=2016111402791670&timestamp=2016-07-29+16%3A55%3A53&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%221202124847-1682%22%7D&method=alipay.trade.app.pay&charset=utf-8&version=1.0&sign_type=RSA&sign=E0LhmU%2BvgXQcOX2s6VgXPtHihX%2B7Gdbf62%2BmrMlKpX5nqlt8IM4QRx58YyuDANF0D588zjBI%2FKTOYB4SSIkuHdJWPJOhNwvWzhPVucy5pVErK3vzabCcHNGUE2vp1eVgvecAVWDnaRCT6WLLaixqZMOWpzHACAZtruhQPeC0BxY%3D";
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                Map<String, String> result = null;
                try {
                    PayTask alipay = new PayTask(ConfirmOrdersActivity.this);
                    result = alipay.payV2(info, true);
                } catch (Exception e) {
                    e.printStackTrace();
                    //部分手机支付宝支付可能出现异常
                    payZFBAgain(info);
                }
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void payZFBAgain(final String info) {
        //final String   info="app_id=2016111402791670&timestamp=2016-07-29+16%3A55%3A53&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%221202124847-1682%22%7D&method=alipay.trade.app.pay&charset=utf-8&version=1.0&sign_type=RSA&sign=E0LhmU%2BvgXQcOX2s6VgXPtHihX%2B7Gdbf62%2BmrMlKpX5nqlt8IM4QRx58YyuDANF0D588zjBI%2FKTOYB4SSIkuHdJWPJOhNwvWzhPVucy5pVErK3vzabCcHNGUE2vp1eVgvecAVWDnaRCT6WLLaixqZMOWpzHACAZtruhQPeC0BxY%3D";
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                Map<String, String> result = null;
                try {
                    PayTask alipay = new PayTask(ConfirmOrdersActivity.this);
                    result = alipay.payV2(info, true);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast("支付宝支付出现异常，建议再次尝试或使用微信支付。");
                    return;
                }
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void dealRefresh(ConfirmOrdersEvent confirmOrdersEvent) {
        ConfirmOrdersBean confirmOrdersBean = GsonUtils.getObject(confirmOrdersEvent.getResult(), ConfirmOrdersBean.class);
        ConfirmOrdersBean.DataBean.OrderBean order = confirmOrdersBean.getData().getOrder();
        //订单头
        mTvPriceRoute.setText("¥" + order.getPrice());
        totalPay += Float.parseFloat(order.getPrice());
        List<BasecPriceBean> basecPrice = confirmOrdersBean.getData().getBasec_price();
        if (basecPrice != null && basecPrice.size() != 0) {
            for (BasecPriceBean basecPriceBean : basecPrice) {
                totalPay += Float.parseFloat(basecPriceBean.getValue());
            }
        }
        if (payType.equals("2")) {
            mTvOrderName.setText("活动订单");
            mTvOrderName.setTextColor(getResources().getColor(R.color.otherFf7f6c));
        }
        mLvPrice.setAdapter(new PriceDeatilAdapter(this, basecPrice));
        mTvOrderId.setText("订单号:" + order.getOrder_sn());
        //优惠券
        orderConpou = confirmOrdersBean.getData().getConpou();
        mTvPay.setText("总计:" + totalPay + "元");
        mTvReducePrice.setText("¥-" + 0.00);
        ordersCouponAdapter = new CouponAdapter(this, orderConpou, false);
        mRvConpun.setAdapter(ordersCouponAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvConpun.setHasFixedSize(true);
        mRvConpun.setNestedScrollingEnabled(false);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        mRvConpun.setLayoutManager(linearLayoutManager);
        ordersCouponAdapter.setItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CouponBean conpouBean = orderConpou.get(position);
                currentPrice = 0f;
                if (conpouBean.getStatus().equals("1")) {
                    if (prePosition != -1) {
                        orderConpou.get(prePosition).setStatus("1");
                    }
                    totalReduces = Float.parseFloat(conpouBean.getNumber());
                    currentPrice = totalPay - Float.parseFloat(conpouBean.getNumber());
                    conpouBean.setStatus("2");
                    prePosition = position;
                } else if (conpouBean.getStatus().equals("2")) {
                    totalReduces = 0.0f;
                    currentPrice = totalPay;
                    conpouBean.setStatus("1");
                }
                prePosition = position;

                ordersCouponAdapter.notifyDataSetChanged();
                mTvReducePrice.setText("¥-" + totalReduces);
                mTvPay.setText("总计:" + currentPrice + "元");
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                submitOrders();
                break;
            case R.id.rl_zfb:
                checkRightWay(mTvPayZfb);
                currentPayWay = PAY_WAY_ZFB;
                break;
            case R.id.rl_wx:
                checkRightWay(mTvPayWx);
                currentPayWay = PAY_WAY_WX;
                break;
        }
    }
    @Subscribe
   public void  onEvent(WxPaySuccessEvent wxPaySuccessEvent){
            startShowDetail(wxPaySuccessEvent.isSuccess(),true);

  }
    /**
     * 提交订单
     */
    private void submitOrders() {

        if (!mCbAgree.isChecked()) {
            ToastUtils.showToast("请阅读并勾选协议");
            return;
        }
        if (mTvPayWx.getVisibility() == View.GONE && mTvPayZfb.getVisibility() == View.GONE) {
            ToastUtils.showToast("请选择一种支付方式");
            return;
        }
        if (totalPay <= 0 || -totalReduces > 0) {
            ToastUtils.showToast("订单异常");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (mConpous.size() != 0) {
            for (int i = 0; i < mConpous.size(); i++) {
                if (i == mConpous.size() - 1) {
                    stringBuilder.append(mConpous.get(i));
                } else {
                    stringBuilder.append(mConpous.get(i) + ",");
                }
            }
        }

        String coupon = stringBuilder.toString();
        Map<String, String> submitMap = MapUtils.Build().addKey().addUserId().addId(id).addCoupon(coupon).add("pay_name", currentPayWay == PAY_WAY_WX ? "2" : "1").end();
        XEventUtils.postUseCommonBackJson(IVariable.SUBMIT_ORDERS, submitMap, SUBMIT_NEW, new ConfirmOrdersEvent());

    }

    /**
     * 选择正确的支付方式
     *
     * @param mTvPayWay
     */
    private void checkRightWay(TextView mTvPayWay) {
        for (TextView textView : selectPayWay) {
            if (textView == mTvPayWay) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected String initTitle() {
        return "确认订单";
    }

}
