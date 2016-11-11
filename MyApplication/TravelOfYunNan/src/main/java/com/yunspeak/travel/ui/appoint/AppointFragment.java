package com.yunspeak.travel.ui.appoint;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.adapter.fragment.CommonPagerAdapter;
import com.yunspeak.travel.ui.appoint.popwindow.AppointCommonPop;
import com.yunspeak.travel.ui.appoint.popwindow.AppointOrderPop2;
import com.yunspeak.travel.ui.appoint.searchappoint.SearchAppointActivity;
import com.yunspeak.travel.ui.appoint.together.PlayTogetherFragment;
import com.yunspeak.travel.ui.appoint.travelplan.TravelsPlanActivity;
import com.yunspeak.travel.ui.appoint.travelplan.TravelsPlanWithMeActivity;
import com.yunspeak.travel.ui.appoint.withme.PlayWithMeFragment;
import com.yunspeak.travel.ui.find.findcommon.CityBean;
import com.yunspeak.travel.ui.fragment.BaseFragment;
import com.yunspeak.travel.utils.FastBlur;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
/**
 * Created by wangyang on 2016/7/20 0020.
 * 约伴
 */
public class AppointFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_play_together) TextView mTvPlayTogether;
    @BindView(R.id.tv_play_with_me) TextView mTvPlayWithMe;
    @BindView(R.id.ll_switch) LinearLayout mLlSwitch;
    @BindView(R.id.tv_type) TextView mTvType;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_order) TextView mTvOrder;
    @BindView(R.id.vp_appoint) ViewPager mVpAppoint;
    @BindView(R.id.fab_add) FloatingActionButton mFabAdd;
    @BindView(R.id.ll_root) LinearLayout mLlRoot;
    @BindView(R.id.tv_search) TextView mTvSearch;
  private boolean isInitLabel=false;//是否初始化label
    private boolean isTogether = false;
    private List<Fragment> fragments;
    String[] orderType = {"最新发布", "价格升序", "价格降序","出行人数","行程天数"};
    String[] timeType = {"默认时序","一周内", "一个月内", "一个月以上"};
    public  int timePosition = 1;
    public  int orderPosition = 1;//选中的
    public  String label="";
    private AppointCommonPop appointCommonPop;
    private Map<String, List<CityBean>> rights;

    private List<CityBean> lefts;
    private boolean isShowDialog = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        getLabel();
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_appoint;
    }

    @Subscribe
    public void onEvent(AppointEvent appointEvent){
        if (!getUserVisibleHint())return;
        AppointBean appointBean = GsonUtils.getObject(appointEvent.getResult(), AppointBean.class);
        try {
            AppointBean.DataBean data = appointBean.getData();
            List<CityBean> labelUser = data.getLabel_user();
            List<CityBean> labelOffice = data.getLabel_office();
            List<CityBean> labelPlay = data.getLabel_play();
            rights = new HashMap<>();
            rights.put("1",labelUser);
            rights.put("2",labelOffice);
            rights.put("3",labelPlay);
            isInitLabel=true;
        } catch (Exception e) {
            e.printStackTrace();
            isInitLabel=false;
        }

    }
    @Override
    protected void initView() {

    }

    private void getLabel() {
        Map<String, String> end = MapUtils.Build().addKey().end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_PLAY_LABEL,end,0,new AppointEvent());
    }

    @Override
    protected void initData() {
        lefts = new ArrayList<>(3);
        CityBean cityBean1=new CityBean();
        cityBean1.setId("1");
        cityBean1.setName("用户标签");
        CityBean cityBean2=new CityBean();
        cityBean2.setId("2");
        cityBean2.setName("认证标签");
        CityBean cityBean3=new CityBean();
        cityBean3.setId("3");
        cityBean3.setName("玩法");
        lefts.add(cityBean1);
        lefts.add(cityBean2);
        lefts.add(cityBean3);
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
        mTvType.setOnClickListener(this);
        mTvTime.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowDialog) {
                    isShowDialog = true;
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

        mVpAppoint.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        Bitmap blurBg = FastBlur.doBlur(bitmap, 10, true);
        showPop(blurBg);
    }

    private void showPop(final Bitmap blurBg) {
        final View dialogView = View.inflate(getContext(), R.layout.popup_window_create_appoiint, null);
        //创建 Dialog
        ImageView mIvBg = (ImageView) dialogView.findViewById(R.id.iv_bg);
        mIvBg.setImageBitmap(blurBg);//设置模糊背景

//		Dialog dialog=new Dialog(上下文,风格style);
        final Dialog dialog = new Dialog(getContext(), R.style.myDialog);
        //layout_width layout_height
       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.getScreenWidth(),DensityUtil.getScreenHeight());
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
                GlobalValue.mAppointType = IVariable.TYPE_WITH_ME;
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
        switch (v.getId()) {
            case R.id.tv_type:
                if (isInitLabel){
                    showType();
                }else {
                    getLabel();
                }

                break;
            case R.id.tv_order:
                AppointOrderPop2.showOrderPop(getContext(),mTvOrder, new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        orderPosition=type;
                        mTvOrder.setText(orderType[type-1]);
                        refreshAppoint();
                    }
                },orderPosition-1);
                break;
            case R.id.tv_time:
                AppointOrderPop2.showTimePop(getContext(), mTvTime, new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        timePosition=type;
                        mTvTime.setText(timeType[type-1]);
                      refreshAppoint();
                    }
                },timePosition-1);
                break;
            case R.id.tv_search:
                startActivity(new Intent(getContext(),SearchAppointActivity.class));
                break;
        }
    }



    private void showType() {
        if (appointCommonPop == null) {
            lefts.get(0).setChecked(true);
            appointCommonPop = AppointCommonPop.newInstance(lefts, rights, new ParentPopClick() {
                @Override
                public void onClick(int type) {
                    label = appointCommonPop.getTyepName();
                    refreshAppoint();
                }
            });
        }
        if (appointCommonPop.isShowing()) {
            appointCommonPop.dismiss();
        } else {
            appointCommonPop.showDown(getContext(),mTvType);
        }
    }

    private void refreshAppoint() {
        EventBus.getDefault().post(new SelectEvent(label,timePosition+"",orderPosition+""));
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
