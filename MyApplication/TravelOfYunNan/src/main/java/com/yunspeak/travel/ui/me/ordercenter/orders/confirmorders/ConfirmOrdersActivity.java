package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.myalbum.createalbum.CreateAlbumBean;
import com.yunspeak.travel.ui.me.ordercenter.BasecPriceBean;
import com.yunspeak.travel.ui.me.ordercenter.CouponBean;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.orderdetail.OrdersDetailActivity;
import com.yunspeak.travel.ui.view.ToShowAllListView;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/12 0012.
 * 确认订单
 */
public class ConfirmOrdersActivity extends BaseNetWorkActivity<ConfirmOrdersEvent> implements View.OnClickListener {
   public static final String ORDER_IS_SURE="1";//之前确认过未付款
   public static final String ORDER_IS_NEW="0";//新订单，尚未做任何操作
    public static final int PAY_WAY_ZFB=1;
    public static final int PAY_WAY_WX=2;
    public static final int SUBMIT_NEW=45;
    public static final int SUBMIT_USED=54;
    private int currentPayWay=-1;
    @BindView(R.id.tv_pay_zfb) TextView mTvPayZfb;
    @BindView(R.id.tv_pay_wx) TextView mTvPayWx;
    @BindView(R.id.rv_coupon)
    RecyclerView mRvConpun;
    @BindView(R.id.rl_zfb) RelativeLayout mRlZfb;
    @BindView(R.id.rl_wx) RelativeLayout mRlWx;
    @BindView(R.id.cb_agree) CheckBox mCbAgree;
    @BindView(R.id.lv_price) ListView mLvPrice;
    @BindView(R.id.tv_route_price) TextView mTvPriceRoute;
    @BindView(R.id.tv_order_id) TextView mTvOrderId;
    @BindView(R.id.tv_reduce_price) TextView mTvReducePrice;
    @BindView(R.id.tv_pay) TextView mTvPay;
    @BindView(R.id.bt_submit) Button mAcpSubmit;
    private List<String> mConpous=new ArrayList<>();


    private List<TextView> selectPayWay = new ArrayList<>();
    private String type;
    private String id;
    private CouponAdapter ordersCouponAdapter;
    private List<CouponBean> orderConpou;
    private float totalReduces =0f;
    private float totalPay =0f;
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
                        Toast.makeText(ConfirmOrdersActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrdersActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
            }
        };

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_confirm_orders;
    }

    private void init() {
        type = getIntent().getStringExtra(IVariable.TYPE);
        id = getIntent().getStringExtra(IVariable.ID);
        selectPayWay.add(mTvPayZfb);
        selectPayWay.add(mTvPayWx);
    }


    @Override
    protected void initEvent() {
        init();
        mAcpSubmit.setOnClickListener(this);
        mRlZfb.setOnClickListener(this);
        mRlWx.setOnClickListener(this);

    }

    /**
     * 优惠券添加或者移除
     * @param conpouBean
     */
    private void removeOrAdd(CouponBean conpouBean) {
        String id = conpouBean.getId();
        if (mConpous.contains(id)){
            mConpous.remove(id);
        }else {
            mConpous.add(id);
        }
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.addType(this.type);
        builder.addId(id);

    }

    @Override
    protected String initUrl() {
        return IVariable.PAY_ORDERS;
    }

    @Override
    protected void onSuccess(ConfirmOrdersEvent confirmOrdersEvent) {
        switch (confirmOrdersEvent.getType()){
            case TYPE_REFRESH:
                dealRefresh(confirmOrdersEvent);
                break;
            case SUBMIT_NEW:
            case SUBMIT_USED:
                CreateAlbumBean order = GsonUtils.getObject(confirmOrdersEvent.getResult(), CreateAlbumBean.class);
                final String info = order.getData();
                if (StringUtils.isEmpty(info)){
                    ToastUtils.showToast("订单信息错误！");
                    return;
                }
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(ConfirmOrdersActivity.this);
                        Map<String, String> result = alipay.payV2(info, true);
                        Log.i("msp", result.toString());
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();
                break;
        }
    }

    private void dealRefresh(ConfirmOrdersEvent confirmOrdersEvent) {
        ConfirmOrdersBean confirmOrdersBean = GsonUtils.getObject(confirmOrdersEvent.getResult(), ConfirmOrdersBean.class);
        ConfirmOrdersBean.DataBean.OrderBean order = confirmOrdersBean.getData().getOrder();
        //订单头
        mTvPriceRoute.setText("¥" + order.getPrice());
        totalPay+=Float.parseFloat(order.getPrice());
        List<BasecPriceBean> basecPrice = confirmOrdersBean.getData().getBasec_price();
        if (basecPrice!=null && basecPrice.size()!=0) {
            for (BasecPriceBean basecPriceBean : basecPrice){
                totalPay+=Float.parseFloat(basecPriceBean.getValue());
            }
        }
        mLvPrice.setAdapter(new PriceDeatilAdapter(this, basecPrice));
        mTvOrderId.setText("订单号:"+order.getOrder_sn());
        //优惠券
        if (type.equals(ORDER_IS_SURE)){//之前没有确认订单
            orderConpou = confirmOrdersBean.getData().getOrder_conpou();
            ConfirmOrdersBean.DataBean.PayBean pay = confirmOrdersBean.getData().getPay();
            float money = Float.parseFloat(pay.getMoney());
            mTvPay.setText("总计:"+money+"元");
            initReduceMoney();
        }else if (type.equals(ORDER_IS_NEW)){//之前确认过订单
            orderConpou =confirmOrdersBean.getData().getConpou();
            mTvPay.setText("总计:"+totalPay+"元");
        }
        ordersCouponAdapter = new CouponAdapter(this, orderConpou, false);
        mRvConpun.setAdapter(ordersCouponAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRvConpun.setHasFixedSize(true);
        mRvConpun.setNestedScrollingEnabled(false);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        mRvConpun.setLayoutManager(linearLayoutManager);
        ordersCouponAdapter.setItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (type.equals(ORDER_IS_NEW)  && orderConpou!=null){//新订单才可以进行选择
                    CouponBean conpouBean = orderConpou.get(position);
                    removeOrAdd(conpouBean);
                    if (conpouBean.getStatus().equals("1")){
                        totalReduces +=Float.parseFloat(conpouBean.getNumber());
                        totalPay-=Float.parseFloat(conpouBean.getNumber());
                        conpouBean.setStatus("2");
                    }else if (conpouBean.getStatus().equals("2")){
                        totalReduces -=Float.parseFloat(conpouBean.getNumber());
                        totalReduces = totalReduces <0?0: totalReduces;
                        totalPay+=Float.parseFloat(conpouBean.getNumber());
                        conpouBean.setStatus("1");
                    }
                    ordersCouponAdapter.notifyDataSetChanged();
                    mTvReducePrice.setText("¥-" + totalReduces);
                    mTvPay.setText("总计:"+totalPay+"元");
                }
            }
        });
    }




    /**
     * 计算一共优惠了多少钱
     */
    private void initReduceMoney() {
        totalReduces = 0f;
        if (orderConpou != null || orderConpou.size() == 0) {
            for (CouponBean conpouBean : orderConpou) {
                totalReduces += Float.parseFloat(conpouBean.getNumber());
            }
            mTvReducePrice.setText("¥-" + totalReduces);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                submitOrders();
                break;
            case R.id.rl_zfb:
                checkRightWay(mTvPayZfb);
                currentPayWay=PAY_WAY_ZFB;
                break;
            case R.id.rl_wx:
                checkRightWay(mTvPayWx);
                currentPayWay=PAY_WAY_WX;
                break;
        }
    }

    /**
     *
     * 提交订单
     */
    private void submitOrders() {
        if (currentPayWay==PAY_WAY_WX){
            ToastUtils.showToast("暂不支持微信支付。");
            return;
        }
        if (!mCbAgree.isChecked()) {
            ToastUtils.showToast("请阅读并勾选协议");
            return;
        }
        if (mTvPayWx.getVisibility() == View.GONE && mTvPayZfb.getVisibility() == View.GONE) {
            ToastUtils.showToast("请选择一种支付方式");
            return;
        }
        if (totalPay<=0 || -totalReduces>0){
            ToastUtils.showToast("订单异常");
            return;
        }
        if (type.equals(ORDER_IS_SURE)){
            Map<String, String> end = MapUtils.Build().addKey().addId(id).end();
            XEventUtils.postUseCommonBackJson(IVariable.SUBMIT_ORDERS_USED,end,SUBMIT_USED,new ConfirmOrdersEvent());
        }else {
            StringBuilder stringBuilder=new StringBuilder();
            if (mConpous.size()!=0){
                for (int i=0;i<mConpous.size();i++){
                  if (i==mConpous.size()-1){
                      stringBuilder.append(mConpous.get(i));
                  }else {
                      stringBuilder.append(mConpous.get(i)+",");
                  }
                }
            }

            String coupon = stringBuilder.toString();
            Map<String, String> submitMap = MapUtils.Build().addKey().addUserId().addId(id).addCoupon(coupon).end();
            XEventUtils.postUseCommonBackJson(IVariable.SUBMIT_ORDERS,submitMap,SUBMIT_NEW,new ConfirmOrdersEvent());
        }
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
