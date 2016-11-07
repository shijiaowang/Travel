package com.yunspeak.travel.ui.home;



import android.content.SharedPreferences;
import android.graphics.Typeface;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;


import com.umeng.message.PushAgent;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Login;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.event.WelcomeEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.AppointFragment;
import com.yunspeak.travel.ui.baseui.BaseActivity;
import com.yunspeak.travel.ui.fragment.CircleFragment;
import com.yunspeak.travel.ui.find.FindFragment;
import com.yunspeak.travel.ui.me.me.MeFragment;
import com.yunspeak.travel.ui.view.GradientTextView;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.TypefaceUtis;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;


import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 主页面
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    public static final int REQ = 0;
    public static final int RESULT = 1;
    public static final int UP_RESULT = 2;
    private List<GradientTextView> iconFonts = new ArrayList<>(5);
    private List<TextView> iconNames = new ArrayList<>(5);
    private List<Fragment> fragments;
    //渐变图标
    @BindView(R.id.vp_home) ViewPager mVpHome;
    @BindView(R.id.tv_home_fonts_icon) GradientTextView mTvHomeIconFonts;
    @BindView(R.id.tv_appoint_fonts_icon) GradientTextView mTvAppointIconFonts;
    @BindView(R.id.tv_circle_fonts_icon) GradientTextView mTvCircleIconFonts;
    @BindView(R.id.tv_find_fonts_icon) GradientTextView mTvFindIconFonts;
    @BindView(R.id.tv_me_fonts_icon) GradientTextView mTvMeIconFonts;
    @BindView(R.id.tv_circle_name) TextView mTvCircleName;
    @BindView(R.id.tv_appoint_name) TextView mTvAppointName;
    @BindView(R.id.tv_me_name) TextView mTvMeName;
    @BindView(R.id.tv_find_name) TextView mTvFindName;
    @BindView(R.id.tv_home_name) TextView mTvHomeName;
    @BindView(R.id.ll_appoint_click) LinearLayout mLlAppointClick;
    @BindView(R.id.ll_circle_click) LinearLayout mLlCircleClick;
    @BindView(R.id.ll_me_click) LinearLayout mLlMeClick;
    @BindView(R.id.ll_find_click) LinearLayout mLlFindClick;
    @BindView(R.id.ll_main_click) LinearLayout mLlMainClick;
    @BindView(R.id.ll_bottom) LinearLayout mLlBottom;
    private boolean isNetwork;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHXListener();
        String registrationId = PushAgent.getInstance(this).getRegistrationId();
        LogUtils.e("注册ID为"+registrationId);
        registerEventBus(this);
        if (!NetworkUtils.isNetworkConnected(this)) {
            ToastUtils.showToast("网络未连接");
        }
        isNetwork = getIntent().getBooleanExtra(IVariable.CACHE_LOGIN_ARE_WITH_NETWORK, true);
        if (!isNetwork || GlobalUtils.getUserInfo()==null){
            sharedPreferences = getSharedPreferences(IVariable.SHARE_NAME, MODE_PRIVATE);
            String userName = sharedPreferences.getString(IVariable.SAVE_NAME, "");
            String userPwd = sharedPreferences.getString(IVariable.SAVE_PWD, "");
            //网络可用验证登录
                Map<String, String> stringMap = MapUtils.Build().addKey().addUserName(userName).addPassword(userPwd).end();
                XEventUtils.getUseCommonBackJson(IVariable.LOGIN_URL, stringMap, IVariable.TYPE_POST_LOGIN, new HomeLoginEvent());

        }
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
        mVpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    @Subscribe
   public void onEvent(HomeLoginEvent event){
       if (event.isSuccess()){
           Login object = GsonUtils.getObject(event.getResult(), Login.class);
           UserInfo data = object.getData();
           UserUtils.saveUserInfo(data);
           if (GlobalUtils.getUserInfo()==null){
               // TODO: 2016/11/5 0005 清除app所有缓存，不再提示
               ToastUtils.showToast("用户信息发生错误，请尝试重新登录，若多次无效，可清除缓存！");
           }
       }else {
           // TODO: 2016/11/7 0007 确定网络问题
           ToastUtils.showToast("您的登录信息有误！可能导致无法进行正常浏览，请重新登录！");
       }
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

    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(this);
    }
}


