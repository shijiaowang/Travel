package com.yunspeak.travel.ui.me.ordercenter.orders;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.ConfirmOrdersActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.orderdetail.OrdersDetailActivity;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/8/11 0011.
 */
public class MyOrdersAdapter extends TravelBaseAdapter<MyOrdersBean.DataBean> {
    public MyOrdersAdapter(Context mContext, List<MyOrdersBean.DataBean> mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, final MyOrdersBean.DataBean item, final int position) {
        MyOrdersHolder myOrdersHolder = (MyOrdersHolder) baseHolder;
        myOrdersHolder.mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showCommonDialog(mContext, "取消订单", "确定","您是否要取消当前订单？" , new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        Map<String, String> deleteMap = MapUtils.Build().addKey(mContext).addUserId().addId(item.getId()).end();
                        MyOrdersEvent event = new MyOrdersEvent();
                        event.setPosition(position);
                        XEventUtils.postUseCommonBackJson(IVariable.CANCEL_ORDERS,deleteMap, BaseToolBarActivity.TYPE_DELETE, event);
                    }
                });

            }
        });
        myOrdersHolder.mBPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        String type = item.getType();
                        String orderId = item.getId();
                        Intent intent=new Intent(mContext, ConfirmOrdersActivity.class);
                        intent.putExtra(IVariable.TYPE,type);
                        intent.putExtra(IVariable.ID,orderId);
                        mContext.startActivity(intent);

            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new MyOrdersHolder(mContext);
    }
}
