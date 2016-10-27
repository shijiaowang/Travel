package com.yunspeak.travel.ui.baseui;



import android.graphics.Typeface;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;


import com.umeng.message.ALIAS_TYPE;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.appoint.AppointFragment;
import com.yunspeak.travel.ui.fragment.CircleFragment;
import com.yunspeak.travel.ui.find.FindFragment;
import com.yunspeak.travel.ui.home.HomeFragment;
import com.yunspeak.travel.ui.me.me.MeFragment;
import com.yunspeak.travel.ui.view.GradientTextView;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.TypefaceUtis;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;


import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    public static final int REQ = 0;
    public static final int RESULT = 1;
    private List<GradientTextView> iconFonts = new ArrayList<>(5);
    private List<TextView> iconNames = new ArrayList<>(5);
    @ViewInject(R.id.vp_home)
    private ViewPager mVpHome;
    private List<Fragment> fragments;
    //渐变图标
    @ViewInject(R.id.tv_home_fonts_icon)
    private GradientTextView mTvHomeIconFonts;
    @ViewInject(R.id.tv_appoint_fonts_icon)
    private GradientTextView mTvAppointIconFonts;
    @ViewInject(R.id.tv_circle_fonts_icon)
    private GradientTextView mTvCircleIconFonts;
    @ViewInject(R.id.tv_find_fonts_icon)
    private GradientTextView mTvFindIconFonts;
    @ViewInject(R.id.tv_me_fonts_icon)
    private GradientTextView mTvMeIconFonts;
    @ViewInject(R.id.tv_circle_name)
    private TextView mTvCircleName;
    @ViewInject(R.id.tv_appoint_name)
    private TextView mTvAppointName;
    @ViewInject(R.id.tv_me_name)
    private TextView mTvMeName;
    @ViewInject(R.id.tv_find_name)
    private TextView mTvFindName;
    @ViewInject(R.id.tv_home_name)
    private TextView mTvHomeName;
    @ViewInject(R.id.ll_appoint_click)
    private LinearLayout mLlAppointClick;
    @ViewInject(R.id.ll_circle_click)
    private LinearLayout mLlCircleClick;
    @ViewInject(R.id.ll_me_click)
    private LinearLayout mLlMeClick;
    @ViewInject(R.id.ll_find_click)
    private LinearLayout mLlFindClick;
    @ViewInject(R.id.ll_main_click)
    private LinearLayout mLlMainClick;
    @ViewInject(R.id.ll_bottom)
    private LinearLayout mLlBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHXListener();
        String registrationId = PushAgent.getInstance(this).getRegistrationId();
        LogUtils.e("注册ID为"+registrationId);

      
    }

    /**
     * 户厕环信监听
     */
    private void initHXListener() {
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        login();
    }

    private void login() {
        if (!EMClient.getInstance().isConnected()) {
            EMClient.getInstance().login(GlobalUtils.getUserInfo().getId(), GlobalUtils.getUserInfo().getPwd(), new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    LogUtils.e("登录聊天服务器成功！");
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    LogUtils.e(message);
                }
            });
        }
    }

    public LinearLayout getmLlBottom() {
        return mLlBottom;
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        iconFonts.add(mTvHomeIconFonts);
        iconFonts.add(mTvAppointIconFonts);
        iconFonts.add(mTvCircleIconFonts);
        iconFonts.add(mTvFindIconFonts);
        iconFonts.add(mTvMeIconFonts);
        iconNames.add(mTvHomeName);
        iconNames.add(mTvAppointName);
        iconNames.add(mTvCircleName);
        iconNames.add(mTvFindName);
        iconNames.add(mTvMeName);

    }

    @Override
    protected void initListener() {
        mLlAppointClick.setOnClickListener(this);
        mLlCircleClick.setOnClickListener(this);
        mLlMeClick.setOnClickListener(this);
        mLlFindClick.setOnClickListener(this);
        mLlMainClick.setOnClickListener(this);
        mVpHome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setCheckedOfPosition(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new AppointFragment());
        fragments.add(new CircleFragment());
        fragments.add(new FindFragment());
        fragments.add(new MeFragment());
        mVpHome.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager()));
        mVpHome.setOffscreenPageLimit(5);//设置缓存距离为3
        initIconFonts();
        setCheckedOfPosition(0);
    }

    /**
     * 初始化字体图标
     */
    private void initIconFonts() {
        Typeface fromAsset = TypefaceUtis.getTypeface(this);
        for (GradientTextView font : iconFonts) {
            font.setTypeface(fromAsset);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                buttonClick(v);
                break;
        }

    }

    /**
     * 底部按钮点击事件
     *
     * @param v
     */
    private void buttonClick(View v) {
        initButton();
        switch (v.getId()) {
            case R.id.ll_main_click:
                mTvHomeIconFonts.setChecked(true);
                mTvHomeName.setTextColor(getResources().getColor(R.color.homeButtonCheckedColor));
                mVpHome.setCurrentItem(0, false);
                break;
            case R.id.ll_appoint_click:
                mTvAppointIconFonts.setChecked(true);
                mTvAppointName.setTextColor(getResources().getColor(R.color.homeButtonCheckedColor));
                mVpHome.setCurrentItem(1, false);
                break;
            case R.id.ll_circle_click:
                mTvCircleIconFonts.setChecked(true);
                mTvCircleName.setTextColor(getResources().getColor(R.color.homeButtonCheckedColor));
                mVpHome.setCurrentItem(2, false);
                break;
            case R.id.ll_find_click:
                mTvFindIconFonts.setChecked(true);
                mTvFindName.setTextColor(getResources().getColor(R.color.homeButtonCheckedColor));
                mVpHome.setCurrentItem(3, false);
                break;
            case R.id.ll_me_click:
                mTvMeIconFonts.setChecked(true);
                mTvMeName.setTextColor(getResources().getColor(R.color.homeButtonCheckedColor));
                mVpHome.setCurrentItem(4, false);
                break;

        }
    }


    /**
     * 根据viewpager切换的位置设置选中button
     *
     * @param position
     */
    private void setCheckedOfPosition(int position) {

        if (position < 0 || position >= iconFonts.size()) {
            return;
        }

        initButton();
        iconFonts.get(position).setChecked(true);
        iconNames.get(position).setTextColor(getResources().getColor(R.color.homeButtonCheckedColor));

    }

    /**
     * 初始化颜色
     */
    private void initButton() {
        for (GradientTextView gradientTextView : iconFonts) {
            gradientTextView.setChecked(false);
        }
        for (TextView textView : iconNames) {
            textView.setTextColor(getResources().getColor(R.color.homeButtonUnCheckedColor));
        }
    }

    class HomeFragmentAdapter extends FragmentPagerAdapter {

        public HomeFragmentAdapter(FragmentManager fm) {
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

    //实现ConnectionListener接口
    class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            LogUtils.e("成功连接聊天服务器");
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        ToastUtils.showToast("聊天账号已被移除");
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        ToastUtils.showToast("账号在其他地方登录");
                        login();
                    } else {
                        if (NetUtils.hasNetwork(HomeActivity.this)) {
                            ToastUtils.showToast("无法连接聊天服务器");
                        }
                        //连接不到聊天服务器
                        else {
                            //当前网络不可用，请检查网络设置
                            ToastUtils.showToast("当前网络不可用，请检查网络。");
                        }
                    }

                }
            });
        }}

}


