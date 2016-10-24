package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.ordercenter.BasecPriceBean;
import com.yunspeak.travel.ui.me.ordercenter.CouponBean;
import com.yunspeak.travel.ui.view.ToShowAllListView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
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
    public static final int SUBMIT_NEW=45;
    public static final int SUBMIT_USED=54;
    @BindView(R.id.tv_pay_zfb) TextView mTvPayZfb;
    @BindView(R.id.tv_pay_wx) TextView mTvPayWx;
    @BindView(R.id.lv_coupon) ToShowAllListView mLvCoupon;
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
        mLvCoupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                ToastUtils.showToast(confirmOrdersEvent.getMessage());
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
        mLvCoupon.setAdapter(ordersCouponAdapter);
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
                break;
            case R.id.rl_wx:
                checkRightWay(mTvPayWx);
                break;
        }
    }

    /**
     *
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
        if (totalPay<=0 || -totalReduces>0){
            ToastUtils.showToast("订单异常");
            return;
        }
        if (type.equals(ORDER_IS_SURE)){
            Map<String, String> end = MapUtils.Build().addKey(this).addId(id).end();
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

            Map<String, String> submitMap = MapUtils.Build().addKey(this).addUserId().addMoney(totalPay).addId(id).addCoupon(coupon).end();
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
