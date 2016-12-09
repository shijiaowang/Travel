package com.yunspeak.travel.ui.appoint.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.global.SendTextClick;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.find.findcommon.CityBean;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/9/14 0014.
 * 排序
 */
public class AppointOrderPop2 {

    private static int pre;



    public  static void showOrderPop(final Context context, View view, final ParentPopClick parentPopClick, int clickPosition){
        if (parentPopClick==null)return;
           // 获取弹出视图对象
        View viewPopup = View.inflate(context, R.layout.appoint_pop_order, null);
           // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(viewPopup, DensityUtil.getScreenWidth(), DensityUtil.dip2px(90));
        RadioButton rbNow = (RadioButton) viewPopup.findViewById(R.id.rb_now);
        RadioButton rbUp = (RadioButton) viewPopup.findViewById(R.id.rb_up);
        RadioButton rbDown = (RadioButton) viewPopup.findViewById(R.id.rb_down);
        RadioButton rbOut = (RadioButton) viewPopup.findViewById(R.id.rb_out);
        RadioButton rbDay = (RadioButton) viewPopup.findViewById(R.id.rb_day);
        List<RadioButton> radioButtons=new ArrayList<>();
        radioButtons.add(rbNow);
        radioButtons.add(rbUp);
        radioButtons.add(rbDown);
        radioButtons.add(rbOut);
        radioButtons.add(rbDay);
        radioButtons.get(clickPosition).setChecked(true);
        rbNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(1);
                window.dismiss();
            }
        });
        rbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(2);
                window.dismiss();
            }
        });
        rbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(3);
                window.dismiss();
            }
        });
        rbOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(4);
                window.dismiss();
            }
        });
        rbDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(5);
                window.dismiss();
            }
        });

           // 响应 视图外的地方 点击关闭当前
        window.setOutsideTouchable(true);
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha=0.7f;
        ((Activity) context).getWindow().setAttributes(lp);

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity) context).getWindow().setAttributes(lp);


            }
        });
           // 响应返回键的关闭
           window.setFocusable(true);
           window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));

        // 获取控件的坐标 x y
       window.showAsDropDown(view);
    }
    public  static void showTimePop(final Context context, View view, final ParentPopClick parentPopClick, int clickPosition){
        if (parentPopClick==null)return;
        // 获取弹出视图对象
        View viewPopup = View.inflate(context, R.layout.appoint_pop_order, null);
        // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(viewPopup, DensityUtil.getScreenWidth(), DensityUtil.dip2px(90));
        RadioButton rbNow = (RadioButton) viewPopup.findViewById(R.id.rb_now);
        RadioButton rbUp = (RadioButton) viewPopup.findViewById(R.id.rb_up);
        RadioButton rbDown = (RadioButton) viewPopup.findViewById(R.id.rb_down);
        RadioButton rbOut = (RadioButton) viewPopup.findViewById(R.id.rb_out);
        RadioButton rbDay = (RadioButton) viewPopup.findViewById(R.id.rb_day);
        rbNow.setText("·\u3000\u3000默认时序");
        rbUp.setText("·\u3000一周内");
        rbDown.setText("·\u3000一个月内");
        rbOut.setText("·\u3000一个月以上");
        rbDay.setVisibility(View.INVISIBLE);
        rbDay.setClickable(false);
        rbDay.setFocusable(false);
        rbDay.setFocusableInTouchMode(false);
        List<RadioButton> radioButtons=new ArrayList<>();
        radioButtons.add(rbNow);
        radioButtons.add(rbUp);
        radioButtons.add(rbDown);
        radioButtons.add(rbOut);
        radioButtons.get(clickPosition).setChecked(true);
        rbNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(1);
                window.dismiss();
            }
        });
        rbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(2);
                window.dismiss();
            }
        });
        rbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(3);
                window.dismiss();
            }
        });
        rbOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(4);
                window.dismiss();
            }
        });

        // 响应 视图外的地方 点击关闭当前
        window.setOutsideTouchable(true);
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha=0.7f;
        ((Activity) context).getWindow().setAttributes(lp);

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity) context).getWindow().setAttributes(lp);


            }
        });
        // 响应返回键的关闭
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));

        // 获取控件的坐标 x y
        window.showAsDropDown(view);
    }
    public  static void showAppointType(final Context context, View view, final SendTextClick parentPopClick, boolean isTogether){
        if (parentPopClick==null)return;
        // 获取弹出视图对象

        final TextView tvType = (TextView) View.inflate(context, R.layout.activity_search_appoint_pop, null);
        // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(tvType, DensityUtil.dip2px(80), DensityUtil.dip2px(38));
        tvType.setText(isTogether?"找人带":"一起玩");
        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(tvType.getText().toString());
                window.dismiss();
            }
        });

        // 响应 视图外的地方 点击关闭当前
        window.setOutsideTouchable(true);
        // 响应返回键的关闭
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        // 获取控件的坐标 x y
        window.showAsDropDown(view);
    }
    public  static PopupWindow showDeliciousType(final Context context, final List<CityBean> cityBeen, View view, final ParentPopClick parentPopClick){
        if (parentPopClick==null)return null;

            // 获取弹出视图对象
            View viewPopup; viewPopup = View.inflate(context, R.layout.pop_delicious_type, null);
            // 创建 弹出窗口
            final PopupWindow window;  window = new PopupWindow(viewPopup, DensityUtil.getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
            RecyclerView mRvType = (RecyclerView) viewPopup.findViewById(R.id.rv_type);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            final DeliciousTypeAdapter deliciousTypeAdapter = new DeliciousTypeAdapter(cityBeen, context);
            mRvType.setAdapter(deliciousTypeAdapter);
            mRvType.setHasFixedSize(true);
            mRvType.setLayoutManager(gridLayoutManager);
            cityBeen.get(0).setChecked(false);
            pre = 0;
            deliciousTypeAdapter.setItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (pre == position) {
                        return;
                    } else {
                        cityBeen.get(pre).setChecked(false);
                    }
                    cityBeen.get(position).setChecked(true);
                    parentPopClick.onClick(position);
                    pre = position;
                    deliciousTypeAdapter.notifyDataSetChanged();
                    window.dismiss();
                }
            });
            // 响应 视图外的地方 点击关闭当前
            window.setOutsideTouchable(true);
            WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
            lp.alpha = 0.7f;
            ((Activity) context).getWindow().setAttributes(lp);

            window.setOnDismissListener(new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                    lp.alpha = 1.0f;
                    ((Activity) context).getWindow().setAttributes(lp);


                }
            });
            // 响应返回键的关闭
            window.setFocusable(true);
            window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));

        // 获取控件的坐标 x y
        window.showAsDropDown(view);
        return window;
    }
}
