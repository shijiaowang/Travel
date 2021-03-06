package com.yunspeak.travel.ui.me.mytheme;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.titlemanage.TitlePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/28 0028.
 * 我的主题
 */

public class MyThemeActivity extends BaseToolBarActivity {
    @BindView(R.id.tb_cursor)  TabLayout mTbCursor;
    @BindView(R.id.vp_theme)  ViewPager mVpTheme;
    String[] mTitles={"我的帖子","我的发表"};

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_my_theme;
    }

    @Override
    protected void initOptions() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PostFragment());
        fragments.add(new PublishFragment());
        mVpTheme.setAdapter(new TitlePagerAdapter(getSupportFragmentManager(), fragments,mTitles));
        mTbCursor.setupWithViewPager(mVpTheme);
    }



    @Override
    protected String initTitle() {
        return "我的主题";
    }

}
