package com.yunspeak.travel.ui.home;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.umeng.analytics.MobclickAgent;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Login;
import com.yunspeak.travel.bean.UpdateBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.AppointFragment;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseActivity;
import com.yunspeak.travel.ui.circle.CircleFragment;
import com.yunspeak.travel.ui.home.welcome.home.HomeFragment;
import com.yunspeak.travel.ui.home.welcome.splash.login.LoginActivity;
import com.yunspeak.travel.ui.me.messagecenter.appointmessage.AppointMessageActivity;
import com.yunspeak.travel.ui.me.messagecenter.appointmessage.AppointMessageAdapter;
import com.yunspeak.travel.ui.me.messagecenter.relateme.detailmessage.RelateMeDetailActivity;
import com.yunspeak.travel.ui.me.myappoint.chat.ChatActivity;
import com.yunspeak.travel.ui.me.ordercenter.OrdersCenterActivity;
import com.yunspeak.travel.ui.view.GradientTextView;
import com.yunspeak.travel.ui.view.NoScrollViewPager;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.TypefaceUtis;
import com.yunspeak.travel.utils.UIUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
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
    public static HomeActivity instance = null;
    private List<GradientTextView> iconFonts = new ArrayList<>(5);
    private List<TextView> iconNames = new ArrayList<>(5);
    private List<Fragment> fragments;
    @BindView(R.id.vp_home)
    NoScrollViewPager mVpHome;
    @BindView(R.id.tv_home_fonts_icon)
    GradientTextView mTvHomeIconFonts;
    @BindView(R.id.tv_appoint_fonts_icon)
    GradientTextView mTvAppointIconFonts;
    @BindView(R.id.tv_circle_fonts_icon)
    GradientTextView mTvCircleIconFonts;
    @BindView(R.id.tv_find_fonts_icon)
    GradientTextView mTvFindIconFonts;
    @BindView(R.id.tv_me_fonts_icon)
    GradientTextView mTvMeIconFonts;
    @BindView(R.id.tv_circle_name)
    TextView mTvCircleName;
    @BindView(R.id.tv_appoint_name)
    TextView mTvAppointName;
    @BindView(R.id.tv_me_name)
    TextView mTvMeName;
    @BindView(R.id.tv_find_name)
    TextView mTvFindName;
    @BindView(R.id.tv_home_name)
    TextView mTvHomeName;
    @BindView(R.id.ll_appoint_click)
    LinearLayout mLlAppointClick;
    @BindView(R.id.ll_circle_click)
    LinearLayout mLlCircleClick;
    @BindView(R.id.ll_me_click)
    LinearLayout mLlMeClick;
    @BindView(R.id.ll_find_click)
    LinearLayout mLlFindClick;
    @BindView(R.id.ll_main_click)
    LinearLayout mLlMainClick;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    private SharedPreferences sharedPreferences;
    private EMMessageListener messageListener;
    private Callback.Cancelable cancelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().exit();
        registerEventBus(this);
        if (!NetworkUtils.isNetworkConnected()) {
            ToastUtils.showToast("网络未连接");
        }
        //收到消息刷新
        messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {

            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        goToWhere();
        instance = this;
        boolean isNetwork = getIntent().getBooleanExtra(IVariable.CACHE_LOGIN_ARE_WITH_NETWORK, true);
        if (!isNetwork || GlobalUtils.getUserInfo() == null) {
            sharedPreferences = getSharedPreferences(IVariable.SHARE_NAME, MODE_PRIVATE);
            String userName = sharedPreferences.getString(IVariable.SAVE_NAME, "");
            String userPwd = sharedPreferences.getString(IVariable.SAVE_PWD, "");
            //网络可用验证登录
            Map<String, String> stringMap = MapUtils.Build().addKey().addUserName(userName).addPassword(userPwd).end();
            XEventUtils.getUseCommonBackJson(IVariable.LOGIN_URL, stringMap, TYPE_REFRESH, new HomeLoginEvent());

        }
        Map<String, String> end = MapUtils.Build().addKey().addType("1").end();
        XEventUtils.getUseCommonBackJson(IVariable.UPDATE, end, TYPE_UPDATE, new HomeLoginEvent());
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

    }

    /**
     * 跳转到其他页面
     */
    private void goToWhere() {
        String msgType = getIntent().getStringExtra(IVariable.MSG_TYPE);
        if (StringUtils.isEmpty(msgType))return;
        switch (msgType) {
            case "1":
            case "3":
            case "4":
                startActivity(new Intent(this, AppointMessageActivity.class));
                break;
            case "2":
                startActivity(new Intent(this, OrdersCenterActivity.class));
                break;
            case "5":
                Intent intent = new Intent(this, RelateMeDetailActivity.class);
                intent.putExtra(IVariable.TYPE, AppointMessageAdapter.TYPE_DISCUSS);
                startActivity(intent);
                break;
            case "6":
                Intent intent2 = new Intent(this, RelateMeDetailActivity.class);
                intent2.putExtra(IVariable.TYPE, AppointMessageAdapter.TYPE_AITE);
                startActivity(intent2);
                break;
            case "7":
                Intent intent3 = new Intent(this, RelateMeDetailActivity.class);
                intent3.putExtra(IVariable.TYPE, AppointMessageAdapter.TYPE_ZAMBIA);
                startActivity(intent3);
                break;
            case "9":
                Intent intentChat = getIntent();
                intentChat.setClass(this, ChatActivity.class);
                startActivity(intentChat);
                break;
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
        fragments.add(new com.yunspeak.travel.ui.find.find.FindFragment());
        fragments.add(new com.yunspeak.travel.ui.me.me.MeFragment());
        mVpHome.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager()));
        mVpHome.setOffscreenPageLimit(5);//设置缓存距离为3
        initIconFonts();
        setCheckedOfPosition(0);
    }

    @Subscribe
    public void onEvent(HomeLoginEvent event) {
        switch (event.getType()) {
            case TYPE_REFRESH:
                loginResult(event);
                break;
            case TYPE_UPDATE://更新
                if (event.isSuccess()) {
                    UpdateBean updateBean = GsonUtils.getObject(event.getResult(), UpdateBean.class);
                    int currentVersionCode = UIUtils.getVersionCode(this);
                    final UpdateBean.DataBean data = updateBean.getData();
                    if (currentVersionCode < data.getVersion()) {
                        EnterAppointDialog.showCommonDialog(this, "更新应用", "确定", "发现新版本，是否更新？", new ParentPopClick() {
                            @Override
                            public void onClick(int type) {
                                RequestParams requestParams = new RequestParams(data.getDownloadurl());
                                requestParams.setMethod(HttpMethod.GET);
                                cancelable = x.http().get(requestParams, new DownloadProgress());
                            }
                        });
                    }
                    break;
                }

        }
    }

    /**
     * 登录结果
     *
     * @param event
     */
    private void loginResult(HomeLoginEvent event) {
        LogUtils.e(event.getMessage());
        if (event.isSuccess()) {
            Login object = GsonUtils.getObject(event.getResult(), Login.class);
            UserUtils.saveUserInfo(object.getData());
        } else {
                if (event.getCode()==0) {
                    ToastUtils.showToast("密码错误！请重新登录");
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } else if (!NetworkUtils.isNetworkConnected()){
                    ToastUtils.showToast(getString(R.string.network_unavailable));
                }
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


    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }

    class DownloadProgress implements Callback.ProgressCallback<File> {
        private UpAppDialog upAppDialog;

        @Override
        public void onWaiting() {

        }

        @Override
        public void onStarted() {
            upAppDialog=  new UpAppDialog(HomeActivity.this, true, new DialogInterface.OnCancelListener() {
               @Override
               public void onCancel(DialogInterface dialog) {
                     if (cancelable!=null){
                         cancelable.cancel();
                     }
               }
           });
            upAppDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (cancelable!=null){
                        cancelable.cancel();
                    }
                }
            });
            upAppDialog.show();
        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {
            if (isDownloading) {
                upAppDialog.setTotal(total);
                upAppDialog.upProgress(current);
            }
        }

        @Override
        public void onSuccess(File result) {
            upAppDialog.dismiss();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
            startActivity(intent);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            ex.printStackTrace();
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                ToastUtils.showToast(getString(R.string.sd_not_available));
            } else if (!NetworkUtils.isNetworkConnected()) {
                ToastUtils.showToast(getString(R.string.network_not_available));
            } else {
                ToastUtils.showToast(getString(R.string.download_fail));
            }
            upAppDialog.dismiss();

        }

        @Override
        public void onCancelled(CancelledException cex) {
            ToastUtils.showToast("下载失败：取消下载");
            upAppDialog.dismiss();
        }

        @Override
        public void onFinished() {

        }
    }

    /**
     * 双击退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    long preTime = 0;

    private void exit() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - preTime > 1000) {
            Snackbar.make(mLlBottom, "再点一下退出应用", 300)
                    .setAction("", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    })
                    .show();
        } else {
            //退出程序
            onBackPressed();

        }
        preTime = currentTimeMillis;
    }

}


