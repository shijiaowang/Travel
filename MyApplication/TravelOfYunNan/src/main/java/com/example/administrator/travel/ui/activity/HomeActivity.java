package com.example.administrator.travel.ui.activity;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.fragment.CircleFragment;
import com.example.administrator.travel.ui.fragment.HomeFragment;
import com.example.administrator.travel.ui.view.GradientTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */
public class HomeActivity extends FragmentActivity implements View.OnClickListener {
    private List<GradientTextView> iconFonts = new ArrayList<>(5);
    private List<TextView> iconNames = new ArrayList<>(5);
    private ViewPager mVpHome;
    private List<Fragment> fragments;
    private GradientTextView mTvHomeIconFonts;
    private GradientTextView mTvAppointIconFonts;
    private GradientTextView mTvCircleIconFonts;
    private GradientTextView mTvFindIconFonts;
    private GradientTextView mTvMeIconFonts;
    private TextView mTvCircleName;
    private TextView mTvAppointName;
    private TextView mTvMeName;
    private TextView mTvFindName;
    private TextView mTvHomeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Translucent status bar
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//状态栏完全透明
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //Typeface fromAsset = Typeface.createFromAsset(getAssets(), "fonts/icomoon.ttf");
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            // window.setNavigationBarColor(Color.TRANSPARENT);//底部虚拟按键透明
        }
        setContentView(R.layout.activity_home);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mVpHome = (ViewPager) findViewById(R.id.vp_home);
        //按钮点击
        LinearLayout mLlAppointClick = (LinearLayout) findViewById(R.id.ll_appoint_click);
        LinearLayout mLlCircleClick = (LinearLayout) findViewById(R.id.ll_circle_click);
        LinearLayout mLlMeClick = (LinearLayout) findViewById(R.id.ll_me_click);
        LinearLayout mLlFindClick = (LinearLayout) findViewById(R.id.ll_find_click);
        LinearLayout mLlMainClick = (LinearLayout) findViewById(R.id.ll_main_click);
        mLlAppointClick.setOnClickListener(this);
        mLlCircleClick.setOnClickListener(this);
        mLlMeClick.setOnClickListener(this);
        mLlFindClick.setOnClickListener(this);
        mLlMainClick.setOnClickListener(this);


        mTvHomeIconFonts = (GradientTextView) findViewById(R.id.tv_home_fonts_icon);//主页按钮
        iconFonts.add(mTvHomeIconFonts);
        mTvAppointIconFonts = (GradientTextView) findViewById(R.id.tv_appoint_fonts_icon);
        iconFonts.add(mTvAppointIconFonts);
        mTvCircleIconFonts = (GradientTextView) findViewById(R.id.tv_circle_fonts_icon);
        iconFonts.add(mTvCircleIconFonts);
        mTvFindIconFonts = (GradientTextView) findViewById(R.id.tv_find_fonts_icon);
        iconFonts.add(mTvFindIconFonts);
        mTvMeIconFonts = (GradientTextView) findViewById(R.id.tv_me_fonts_icon);
        iconFonts.add(mTvMeIconFonts);
        //主页按钮名字
        mTvCircleName = (TextView) findViewById(R.id.tv_circle_name);
        mTvAppointName = (TextView) findViewById(R.id.tv_appoint_name);
        mTvMeName = (TextView) findViewById(R.id.tv_me_name);
        mTvFindName = (TextView) findViewById(R.id.tv_find_name);
        mTvHomeName = (TextView) findViewById(R.id.tv_home_name);
        iconNames.add(mTvHomeName);
        iconNames.add(mTvAppointName);
        iconNames.add(mTvCircleName);
        iconNames.add(mTvFindName);
        iconNames.add(mTvMeName);

    }

    private void initListener() {
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

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CircleFragment());
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        mVpHome.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager()));
        initIconFonts();
        setCheckedOfPosition(0);

    }

    /**
     * 初始化字体图标
     */
    private void initIconFonts() {
        Typeface fromAsset = Typeface.createFromAsset(getAssets(), "fonts/icomoon.ttf");
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
                mVpHome.setCurrentItem(0,false);
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
}
