package com.example.administrator.travel.ui.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.OrdersCouponAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/12 0012.
 * 确认订单
 */
public class ConfirmOrdersActivity extends BarBaseActivity implements View.OnClickListener {

    private TextView mTvPayZfb;
    private TextView mTvPayWx;
    @ViewInject(R.id.tv_submit)
    private TextView mTvSubmit;
    @ViewInject(R.id.lv_coupon)
    private ToShowAllListView mLvCoupon;
    @ViewInject(R.id.rl_zfb)
    private RelativeLayout mRlZfb;
    @ViewInject(R.id.rl_wx)
    private RelativeLayout mRlWx;
    @ViewInject(R.id.cb_agree)
    private CheckBox mCbAgree;
    private List<TextView> selectPayWay=new ArrayList<>();

    @Override
    protected void initContentView() {
        mTvPayZfb = FontsIconUtil.findIconFontsById(R.id.tv_pay_zfb, this);
        mTvPayWx = FontsIconUtil.findIconFontsById(R.id.tv_pay_wx, this);
        selectPayWay.add(mTvPayZfb);
        selectPayWay.add(mTvPayWx);

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_confirm_orders;
    }

    @Override
    protected void initEvent() {
        mTvSubmit.setOnClickListener(this);
        mRlZfb.setOnClickListener(this);
        mRlWx.setOnClickListener(this);
    }

    @Override
    protected void initViewData() {
        mLvCoupon.setAdapter(new OrdersCouponAdapter(this, null,false));
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected String setTitleName() {
        return "确认订单";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (!mCbAgree.isChecked()) {
                    ToastUtils.showToast( "请阅读并勾选协议");
                    return;
                }
                if (mTvPayWx.getVisibility()==View.GONE && mTvPayZfb.getVisibility()==View.GONE){
                    ToastUtils.showToast("请选这一种支付方式");
                    return;
                }
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
     * 选择正确的支付方式
     * @param mTvPayWay
     */
    private void checkRightWay(TextView mTvPayWay) {
        for (TextView textView:selectPayWay){
            if (textView==mTvPayWay){
                textView.setVisibility(View.VISIBLE);
            }else {
                textView.setVisibility(View.GONE);
            }
        }
    }
}
