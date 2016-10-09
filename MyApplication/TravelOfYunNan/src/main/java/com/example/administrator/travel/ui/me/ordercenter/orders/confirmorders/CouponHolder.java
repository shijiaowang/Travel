package com.example.administrator.travel.ui.me.ordercenter.orders.confirmorders;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.me.ordercenter.CouponBean;
import com.example.administrator.travel.utils.FormatDateUtils;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/12 0012.
 */
public class CouponHolder extends BaseHolder<CouponBean> {

    @BindView(R.id.v_line) View line1;
    @BindView(R.id.v_line2) View line2;
    @BindView(R.id.tv_how)TextView mTvHow;
    @BindView(R.id.tv_valuee)TextView mTvValue;
    @BindView(R.id.tv_time)TextView mTvTime;
    @BindView(R.id.tv_status)TextView mTvStatus;
    @BindColor(R.color.color90c75c) @ColorInt int canUseColor;
    @BindColor(R.color.colorff806d) @ColorInt int usedColor;
    @BindColor(R.color.colorb5b5b5) @ColorInt int runOut;//已过期
    private boolean isCouponFragment;
    public CouponHolder(Context context, boolean isCouponFragment) {
        super(context);
        this.isCouponFragment=isCouponFragment;

    }

    @Override
    protected void initItemDatas(CouponBean datas, Context mContext, int position) {
        mTvValue.setText("¥"+datas.getNumber());
        mTvHow.setText(datas.getNumber()+"元优惠券");
        mTvTime.setText("有效期至"+FormatDateUtils.FormatLongTime(IVariable.Y_M_D,datas.getEnd_time()));
        if (datas.getStatus().equals("1")){
            mTvStatus.setText("可使用");
            mTvStatus.setTextColor(canUseColor);
        }else if (datas.getStatus().equals("2")){
            mTvStatus.setText("已使用");
            mTvStatus.setTextColor(usedColor);
        }else{
            mTvStatus.setText("已过期");
            mTvStatus.setTextColor(runOut);
        }

        /**
         * 优惠券页面没有line2，确认订单页面没有line1
         */
          if (isCouponFragment){
              line2.setVisibility(View.GONE);
              line1.setVisibility(View.VISIBLE);
          }else {
              line2.setVisibility(View.VISIBLE);
              line1.setVisibility(View.GONE);
          }
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_confirm_orders);
    }
}
