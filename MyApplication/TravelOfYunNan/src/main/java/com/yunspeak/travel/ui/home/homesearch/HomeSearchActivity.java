package com.yunspeak.travel.ui.home.homesearch;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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
    private List<SearchCommonFragment> fragments;
    private TextView mTvSearch;
    private EditText mEtSearch;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initOptions() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_search, mToolbar,false);
        mTvSearch = (TextView) inflate.findViewById(R.id.tv_search);
        mEtSearch = (EditText) inflate.findViewById(R.id.et_search);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //一般输入法或搜狗输入法点击搜索按键
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //这里调用搜索方法
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0); //强制隐藏键盘
                    search();
                    return true;
                }
                return false;
            }
        });
        mTvSearch.setOnClickListener(this);
        mToolbar.addView(inflate);
        setIsProgress(false);
        fragments = new ArrayList<>();
        SearchCommonFragment searchUser=SearchCommonFragment.newInstance(SEARCH_USER);
        SearchCommonFragment searchDestination=SearchCommonFragment.newInstance(SEARCH_DESTINATION);
        SearchCommonFragment searchCircl=SearchCommonFragment.newInstance(SEARCH_CIRCLE);
        SearchCommonFragment searchContent=SearchCommonFragment.newInstance(SEARCH_CONTENT);
        fragments.add(searchUser);
        fragments.add(searchDestination);
        fragments.add(searchCircl);
        fragments.add(searchContent);
        mVpSearch.setAdapter(new SearchPagerAdapter(getSupportFragmentManager()));
        mVpSearch.setOffscreenPageLimit(4);
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
            case R.id.tv_search:
                search();
                break;
        }
    }

    private void search() {
        content=getString(mEtSearch);
        fragments.get(mVpSearch.getCurrentItem()).search();
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

    @Override
    protected void onStop() {
        super.onStop();
        content="";
    }
}
