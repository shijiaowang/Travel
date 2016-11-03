package com.yunspeak.travel.ui.home.homesearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.view.FontsIconCursorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/22 0022.
 * 首页搜索
 */
public class HomeSearchActivity extends BaseToolBarActivity implements View.OnClickListener {
    @BindView(R.id.ficv_cursor) FontsIconCursorView mFicvCursor;
    @BindView(R.id.vp_search) ViewPager mVpSearch;
    public static final String SEARCH_USER="1";
    public static final String SEARCH_DESTINATION="2";
    public static final String SEARCH_CIRCLE="3";
    public static final String SEARCH_CONTENT="4";
    private String type=SEARCH_USER;
    protected static String content="";
    private List<Fragment> fragments;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initOptions() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_search, mToolbar,false);
        mToolbar.addView(inflate);
        setIsProgress(false);
        fragments = new ArrayList<>();
        fragments.add(new SearchCommonFragment());
        fragments.add(new SearchCommonFragment());
        fragments.add(new SearchCommonFragment());
        fragments.add(new SearchCommonFragment());
        mVpSearch.setAdapter(new SearchPagerAdapter(getSupportFragmentManager()));
        mFicvCursor.setViewPager(mVpSearch);
    }

    @Override
    protected String initTitle() {
        mTvTitle.setVisibility(View.GONE);
        return "";
    }







    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
        }
    }

    class SearchPagerAdapter extends FragmentPagerAdapter{

        public SearchPagerAdapter(FragmentManager fm) {
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
