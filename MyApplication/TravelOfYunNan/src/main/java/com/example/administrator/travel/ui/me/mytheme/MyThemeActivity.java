package com.example.administrator.travel.ui.me.mytheme;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.me.titlemanage.TitlePagerAdapter;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/9/28 0028.
 * 我的主题
 */

public class MyThemeActivity extends BarBaseActivity {
    @ViewInject(R.id.tb_cursor) private TabLayout mTbCursor;
    @ViewInject(R.id.vp_theme) private ViewPager mVpTheme;
    String[] mTitles={"我的帖子","我的发表"};
    private List<Fragment> fragments;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_theme;
    }

    @Override
    protected void initEvent() {
        fragments = new ArrayList<>();
        fragments.add(new MyPostFragment());
        fragments.add(new MyPublishFragment());
        mVpTheme.setAdapter(new TitlePagerAdapter(getSupportFragmentManager(),fragments,mTitles));
        mTbCursor.setupWithViewPager(mVpTheme);

    }

    @Override
    protected void initViewData() {

    }


    @Override
    protected String setTitleName() {
        return "我的主题";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
