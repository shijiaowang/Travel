
package com.yunspeak.travel.ui.home.homesearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.HomeSearchBinding;
import com.yunspeak.travel.ui.baseui.BaseBarActivity;
import com.yunspeak.travel.ui.home.homesearch.model.TextChangeModel;
import com.yunspeak.travel.ui.view.FontsIconCursorView;
import com.yunspeak.travel.ui.view.SearchView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangyang on 2016/8/22 0022.
 * 首页搜索
 */
public class HomeSearchActivity extends BaseBarActivity<HomeSearchBinding> {
    FontsIconCursorView mFicvCursor;
    ViewPager mVpSearch;
    public static final String SEARCH_USER="1";
    public static final String SEARCH_DESTINATION="2";
    public static final String SEARCH_CIRCLE="3";
    public static final String SEARCH_CONTENT="4";
    private List<SearchFragment> fragments;
    private TextChangeModel textChange;
    private SearchView mSvSearch;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initOptions() {
        mFicvCursor = (FontsIconCursorView) findViewById(R.id.ficv_cursor);
        mVpSearch = (ViewPager) findViewById(R.id.vp_search);
        mSvSearch = (SearchView) findViewById(R.id.search_view);
        textChange = new TextChangeModel();
        dataBinding.setTextChange(textChange);
        initPagerAndListener();
    }

    private void initPagerAndListener() {
        fragments = new ArrayList<>();
        SearchFragment searchUser=SearchFragment.newInstance(SEARCH_USER);
        SearchFragment searchDestination=SearchFragment.newInstance(SEARCH_DESTINATION);
        SearchFragment searchCircle=SearchFragment.newInstance(SEARCH_CIRCLE);
        SearchFragment searchContent=SearchFragment.newInstance(SEARCH_CONTENT);
        fragments.add(searchUser);
        fragments.add(searchDestination);
        fragments.add(searchCircle);
        fragments.add(searchContent);
        mVpSearch.setAdapter(new SearchPagerAdapter(getSupportFragmentManager()));
        mVpSearch.setOffscreenPageLimit(4);
        mFicvCursor.setViewPager(mVpSearch);
        mSvSearch.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearch(String text) {
                search(text);
            }
        });
        mVpSearch.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textChange.text.set("");
                search(textChange.text.get());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected String initTitle() {
        return "";
    }

    @Override
    protected void createTitle() {
        //没有title
    }


    private void search(String text) {
        fragments.get(mVpSearch.getCurrentItem()).onSearch(text);
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

