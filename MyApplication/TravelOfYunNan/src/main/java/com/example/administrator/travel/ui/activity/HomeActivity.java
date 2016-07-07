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
import android.widget.TextView;


import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */
public class HomeActivity extends FragmentActivity {
   private List<TextView> iconFonts=new ArrayList<>(4);
    private ViewPager mVpHome;
    private List<Fragment> fragments;
    private TextView mTvHomeIconFonts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP) {
            // Translucent status bar
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//状态栏完全透明
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
        mTvHomeIconFonts = (TextView) findViewById(R.id.tv_home_fonts_icon);//主页按钮
        iconFonts.add(mTvHomeIconFonts);
    }
    private void initListener() {

    }
    private void initData() {
        fragments=new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        mVpHome.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager()));
        initIconFonts();
    }

    /**
     *初始化字体图标
     */
    private void initIconFonts() {
        Typeface fromAsset = Typeface.createFromAsset(getAssets(), "fonts/icomoon.ttf");
         for (TextView font:iconFonts){
             font.setTypeface(fromAsset);
         }
    }

    class HomeFragmentAdapter extends FragmentPagerAdapter{

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
