package yunshuo.yneb.com.myapplication.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.SystemBarHelper;

/**
 * 主页面
 */
public class HomeActivity extends BaseHideSoftActivity implements View.OnClickListener {
    public static HomeActivity instance = null;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.tv_toolbar) Toolbar tvToolbar;
    @BindView(R.id.iv_pager) ViewPager ivPager;
    private static final String[] tabs=new String[]{"聊天","群组"};
    private List<Fragment> fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        SystemBarHelper.immersiveStatusBar(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tvToolbar.setPadding(0, SystemBarHelper.getStatusBarHeight(this), 0, 0);
            ViewGroup.LayoutParams layoutParams = tvToolbar.getLayoutParams();
            layoutParams.height+=SystemBarHelper.getStatusBarHeight(this);
            tvToolbar.setLayoutParams(layoutParams);
        }
        initTabLayout();
    }

    private void initTabLayout() {
        fragments = new ArrayList<>(2);
        fragments.add(new FragmentTest());
        fragments.add(new FragmentTest());
        HomePagerAdapter homePagerAdapter=new HomePagerAdapter(getSupportFragmentManager());
        ivPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(ivPager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
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
            Snackbar.make(ivPager, "快速双击退出应用", 300)
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

    @Override
    public void onClick(View v) {

    }
    class HomePagerAdapter extends FragmentPagerAdapter{

        public HomePagerAdapter(FragmentManager fm) {
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

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}


