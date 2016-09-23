package com.example.administrator.travel.ui.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.SelectCommonBean;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.popwindow.AppointOrderPop;
import com.example.administrator.travel.ui.appoint.travelplan.TravelsPlanActivity;
import com.example.administrator.travel.ui.adapter.fragment.CommonPagerAdapter;
import com.example.administrator.travel.ui.appoint.popwindow.AppointCommonPop;
import com.example.administrator.travel.ui.appoint.travelplan.TravelsPlanBaseActivity;
import com.example.administrator.travel.ui.appoint.travelplan.TravelsPlanWithMeActivity;
import com.example.administrator.travel.utils.FastBlur;
import com.example.administrator.travel.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2016/7/20 0020.
 * 约伴
 */
public class AppointFragment extends BaseFragment implements View.OnClickListener {
   String[] orderType={"智能排序","按星级","按口碑"};
   String[] timeType={"一周内","一月内","一个月以上"};
    String []clickType;
   String[] timeTypePop={"·\u3000一周内","·\u3000一月内","·\u3000一个月以上"};

    private ViewPager mVpAppoint;
    private LinearLayout mLlSwitch;
    private boolean isTogether = false;
    private TextView mTvPlayTogether;
    private TextView mTvPlayWithMe;
    private List<Fragment> fragments;
    private FloatingActionButton mFabAdd;
    private LinearLayout mLlRoot;
    private TextView mTvTime;
    private TextView mTvType;
    private TextView mTvOrder;
    private AppointCommonPop appointCommonPop;
    private AppointOrderPop appointOrderPop;
    private int timePosition=-1;
    private int orderPosition=-1;//选中的
     private boolean isShowDialog=false;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_appoint;
    }

    @Override
    protected void initView() {
        mVpAppoint = (ViewPager) root.findViewById(R.id.vp_appoint);
        mLlSwitch = (LinearLayout) root.findViewById(R.id.ll_switch);
        mTvPlayTogether = (TextView) root.findViewById(R.id.tv_play_together);
        mTvPlayWithMe = (TextView) root.findViewById(R.id.tv_play_with_me);
        mFabAdd = (FloatingActionButton) root.findViewById(R.id.fab_add);
        mLlRoot = (LinearLayout) root.findViewById(R.id.ll_root);
        mTvTime = (TextView) root.findViewById(R.id.tv_time);
        mTvType = (TextView) root.findViewById(R.id.tv_type);
        mTvOrder = (TextView) root.findViewById(R.id.tv_order);
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>(2);
        fragments.add(new PlayTogetherFragment());
        fragments.add(new PlayWithMeFragment());
        mVpAppoint.setAdapter(new CommonPagerAdapter(getChildFragmentManager(), fragments));

    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
    @Override
    protected void initListener() {

        mTvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTvType.setOnClickListener(this);
        mTvTime.setOnClickListener(this);
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowDialog) {
                    isShowDialog=true;
                    showAppointDialog();//展示约伴框
                }
            }
        });
        mTvOrder.setOnClickListener(this);
        mLlSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTogether = !isTogether;
                if (isTogether) {
                    selectPager(0);
                    mVpAppoint.setCurrentItem(0, false);
                } else {
                    selectPager(1);
                    mVpAppoint.setCurrentItem(1, false);
                }

            }
        });

        mVpAppoint.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




    private void showAppointDialog() {

        Bitmap viewBitmap = createViewBitmap(mLlRoot);
        Bitmap bitmap = FastBlur.zoomImage(viewBitmap, 300, 500);//压缩图片
        viewBitmap.recycle();
        Bitmap blurBg = FastBlur.doBlur( bitmap, 10,true);
        showPop(blurBg);
    }

    private void showPop(final Bitmap blurBg) {
        final View dialogView=View.inflate(getContext(), R.layout.popup_window_create_appoiint, null);
        //创建 Dialog
        ImageView mIvBg = (ImageView) dialogView.findViewById(R.id.iv_bg);
       mIvBg.setImageBitmap(blurBg);//设置模糊背景

//		Dialog dialog=new Dialog(上下文,风格style);
        final Dialog dialog = new Dialog(getContext(), R.style.myDialog);
        //layout_width layout_height
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        dialog.setContentView(dialogView, params);
        dialogView.findViewById(R.id.rl_together).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalValue.mAppointType = IVariable.TYPE_TOGETHER;
                startActivity(new Intent(getContext(), TravelsPlanActivity.class));

            }
        });
        dialogView.findViewById(R.id.rl_with_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalValue.mAppointType= IVariable.TYPE_WITH_ME;
                startActivity(new Intent(getContext(), TravelsPlanWithMeActivity.class));

            }
        });
        dialogView.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                blurBg.recycle();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isShowDialog = false;
            }
        });
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.myDialog);
        dialog.show();

    }


    private void selectPager(int position) {
        if (position < 0 || position > fragments.size() - 1) {
            return;
        }
        if (position == 0) {
            mTvPlayTogether.setBackgroundResource(R.drawable.fragment_appoint_cursor);
            mTvPlayTogether.setTextColor(getResources().getColor(R.color.otherTitleBg));
            mTvPlayWithMe.setBackgroundColor(Color.TRANSPARENT);
            mTvPlayWithMe.setTextColor(getResources().getColor(R.color.colorFAFAFA));
        } else {
            mTvPlayWithMe.setBackgroundResource(R.drawable.fragment_appoint_cursor);
            mTvPlayWithMe.setTextColor(getResources().getColor(R.color.otherTitleBg));
            mTvPlayTogether.setBackgroundColor(Color.TRANSPARENT);
            mTvPlayTogether.setTextColor(getResources().getColor(R.color.colorFAFAFA));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_type:
                showType();
                break;
            case R.id.tv_order:
                clickType=orderType;
                orderPop(mTvOrder,null,orderPosition);
                break;
            case R.id.tv_time:
                clickType=timeType;
                orderPop(mTvTime,timeTypePop,timePosition);

                break;
        }
    }

    private void orderPop(final TextView textView,String[] titile,int clickPosition) {
        if (appointOrderPop==null) {
            appointOrderPop = new AppointOrderPop();

        }
        appointOrderPop.setOnItemClickListener(new AppointOrderPop.OnItemClickListener() {
            @Override
            public void onItemClick(int type) {
                textView.setText(clickType[type]);
                if (textView==mTvTime){
                    timePosition=type;
                }else {
                    orderPosition=type;
                }
            }
        });
        appointOrderPop.showOrderPop(getContext(),mTvOrder,titile,clickPosition);
    }

    private void showType() {
        List<SelectCommonBean> left=new ArrayList<>();
        List<SelectCommonBean> right=new ArrayList<>();
        SelectCommonBean playWay = new SelectCommonBean("玩法");
        playWay.setIsChecked(true);
        left.add(playWay);
        left.add(new SelectCommonBean("路线"));
        left.add(new SelectCommonBean("路线1"));
        left.add(new SelectCommonBean("路线2"));
        for (int i=0;i<20;i++){
            right.add(new SelectCommonBean("游戏"+i));
        }
        if (appointCommonPop==null) {
            appointCommonPop = AppointCommonPop.newInstance(left, right);
        }
        if (appointCommonPop.isShowing()){
            appointCommonPop.dismiss();
        }else {
            appointCommonPop.showDown(getContext(),mTvType);
        }
    }
}
