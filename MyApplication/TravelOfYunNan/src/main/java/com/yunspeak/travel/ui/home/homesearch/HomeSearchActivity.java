package com.yunspeak.travel.ui.home.homesearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseActivity;
import com.yunspeak.travel.ui.baseui.BaseFullScreenActivity;
import com.yunspeak.travel.ui.fragment.SearchUserFragment;
import com.yunspeak.travel.ui.view.FontsIconCursorView;
import com.yunspeak.travel.ui.view.FontsIconTextView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/22 0022.
 * 首页搜索
 */
public class HomeSearchActivity extends BaseFullScreenActivity implements View.OnClickListener {
    @BindView(R.id.ficv_cursor) FontsIconCursorView mFicvCursor;
    @BindView(R.id.vp_search) ViewPager mVpSearch;
    @BindView(R.id.tv_back)
    ImageView mTvBack;
    private List<Fragment> fragments;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
     mTvBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(new SearchUserFragment());
        fragments.add(new SearchUserFragment());
        fragments.add(new SearchUserFragment());
        fragments.add(new SearchUserFragment());
        mVpSearch.setAdapter(new SearchPagerAdapter(getSupportFragmentManager()));
        mFicvCursor.setViewPager(mVpSearch);

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
