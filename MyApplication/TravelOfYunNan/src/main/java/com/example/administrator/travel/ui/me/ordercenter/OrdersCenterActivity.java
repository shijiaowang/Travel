package com.example.administrator.travel.ui.me.ordercenter;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BaseToolBarActivity;
import com.example.administrator.travel.ui.me.ordercenter.coupon.CouponFragment;
import com.example.administrator.travel.ui.me.ordercenter.orders.MyOrdersFragment;
import com.example.administrator.travel.ui.view.SimpleViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/11 0011.
 * 我的订单
 */
public class OrdersCenterActivity extends BaseToolBarActivity {
    private String[] mTitles = {"最近订单", "全部订单", "优惠券"};
    private String[] ordersType = {"全部订单", "约伴订单", "活动订单"};
    private int currentOrders = 0;//当前为全部订单

    @BindView(R.id.svpi_indicator) SimpleViewPagerIndicator mIndicator;
    @BindView(R.id.vp_orders) ViewPager mVpOrders;
    private List<Fragment> fragments;





    private void init() {
        mIndicator.setChildMargin(10);
        mIndicator.changeToShowPop();
        mIndicator.setTitles(mTitles);
        mIndicator.setViewPager(mVpOrders);
    }

    private void showPop(final TextView tv) {

        // 获取弹出视图对象
        View viewPopup = View.inflate(OrdersCenterActivity.this, R.layout.activity_my_orders_pop, null);
        // 创建 弹出窗口
        // PopupWindow window=new PopupWindow(视图对象, 宽度, 高度);
        final PopupWindow window = new PopupWindow(viewPopup, tv.getWidth(), (int) getResources().getDimension(R.dimen.activityMyOrdersPopHeight));
         TextView mTvCurSor1 = (TextView) viewPopup.findViewById(R.id.tv_cursor1);
        TextView mTvCurSor2 = (TextView) viewPopup.findViewById(R.id.tv_cursor2);
        List<String> popOrdersType = getPopOrdersType();
        mTvCurSor1.setText(popOrdersType.get(0));
        mTvCurSor2.setText(popOrdersType.get(1));
        mTvCurSor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (currentOrders==0){
                currentOrders=1;
               }else {
                   currentOrders=0;
               }
                tv.setText(ordersType[currentOrders]);
                window.dismiss();
            }
        });
        mTvCurSor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentOrders==0){
                    currentOrders=2;
                }else if (currentOrders==1){
                    currentOrders=2;
                }else {
                    currentOrders=1;
                }
                tv.setText(ordersType[currentOrders]);
                window.dismiss();
            }
        });

        window.setOutsideTouchable(true);
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        int[] location = new int[2];
        tv.getLocationInWindow(location);
        window.showAtLocation(tv, Gravity.LEFT + Gravity.TOP, location[0] + 1, location[1] + tv.getHeight());// showAtLocation指定控件为参考点
    }

    private List<String> getPopOrdersType() {
        List<String> title = new ArrayList();
        for (int i = 0; i < ordersType.length; i++) {
            if (i!=currentOrders){
                title.add(ordersType[i]);
            }

        }
        return title;
    }







    @Override
    protected int initLayoutRes() {
        return  R.layout.activity_orders_center;
    }

    @Override
    protected void initOptions() {
        init();
        mIndicator.setOnPopShowListener(new SimpleViewPagerIndicator.OnPopShowListener() {
            @Override
            public void onShow(TextView tv) {
                //弹出popwindow
                showPop(tv);
            }
        });
        mVpOrders.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragments = new ArrayList<>();
        fragments.add(new CouponFragment());
        fragments.add(new MyOrdersFragment());
        //fragments.add(new MyOrdersFragment());

        fragments.add(new CouponFragment());
        mVpOrders.setAdapter(new OrdersPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected String initTitle() {
        return "订单中心";
    }

    class OrdersPagerAdapter extends FragmentPagerAdapter {

        public OrdersPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
