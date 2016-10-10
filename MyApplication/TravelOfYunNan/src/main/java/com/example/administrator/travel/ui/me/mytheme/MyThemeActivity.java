package com.example.administrator.travel.ui.me.mytheme;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BaseToolBarActivity;
import com.example.administrator.travel.ui.me.titlemanage.TitlePagerAdapter;

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
    private List<Fragment> fragments;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_my_theme;
    }

    @Override
    protected void initOptions() {
        fragments = new ArrayList<>();
        fragments.add(new MyPostFragment());
        fragments.add(new MyPublishFragment());
        mVpTheme.setAdapter(new TitlePagerAdapter(getSupportFragmentManager(),fragments,mTitles));
        mTbCursor.setupWithViewPager(mVpTheme);
    }



    @Override
    protected String initTitle() {
        return "我的主题";
    }

}
