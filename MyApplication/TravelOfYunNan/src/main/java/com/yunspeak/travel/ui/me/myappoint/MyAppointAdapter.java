package com.yunspeak.travel.ui.me.myappoint;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.travel.ChatActivity;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.bulltetinboard.BulletinBoardActivity;
import com.yunspeak.travel.ui.me.myappoint.memberdetail.MemberDetailActivity;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.MyWithMeSelectActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.ConfirmOrdersActivity;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;


import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class MyAppointAdapter extends BaseRecycleViewAdapter<Object> {
    public static final  int TYPE_TOGETHER=1;
    public static final  int TYPE_WITH_ME=2;
    public MyAppointAdapter(List<Object> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public BaseRecycleViewHolder<Object> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_TOGETHER){
            View view = inflateView(R.layout.item_activity_my_appoint_together, parent);
            return new MyAppointTogetherHolder(view);
        }else {
            View view = inflateView(R.layout.item_activity_my_appoint_with_me, parent);
            return new MyAppointingWithMeHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(0) instanceof MyAppointTogetherBean.DataBean) {
            return TYPE_TOGETHER;
        } else {
            return TYPE_WITH_ME;
        }
    }


}
