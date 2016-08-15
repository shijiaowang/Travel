package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class OrdersCouponHolder extends BaseHolder {

    @ViewInject(R.id.v_line)
    private View line1;
    @ViewInject(R.id.v_line2)
    private View line2;
    private boolean isCouponFragment;
    public OrdersCouponHolder(Context context,boolean isCouponFragment) {
        super(context);
        this.isCouponFragment=isCouponFragment;

    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {
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
        View view = inflateView(R.layout.item_activity_confirm_orders);
        return view;
    }
}
