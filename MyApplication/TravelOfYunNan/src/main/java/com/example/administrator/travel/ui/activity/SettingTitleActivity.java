package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.SettingTitle;
import com.example.administrator.travel.event.SettingTitleEvent;
import com.example.administrator.travel.event.TabEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.ui.fragment.TabFragment;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.SimpleViewPagerIndicator;
import com.example.administrator.travel.utils.DensityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6 0006.
 * 设置标签页面
 */
public class SettingTitleActivity extends LoadingBarBaseActivity {
    private static final int TYPE_MY_TITLE = 0;
    private static final int TYPE_VER_TITLE = 1;//认证标志
    private static final int TYPE_PLAY_WAY = 2;//玩法
    private static final int TYPE_DIY_TITLE = 3;
    private String[] mTitles = {"我的称号", "认证标志", "玩法", "自定义"};
    private List<Fragment> fragmentList = new ArrayList<>();
    @ViewInject(R.id.ll_indicator)
    private LinearLayout mLlDot;
    @ViewInject(R.id.v_dot)
    private View mVDot;
    @ViewInject(R.id.vp_pager)
    private ViewPager mVpPager;
    @ViewInject(R.id.indicator)
    private SimpleViewPagerIndicator mIndicator;
    @ViewInject(R.id.fl_title)
    private FlowLayout mFlTitle;
    private int mPointDistance;
    private int mFirstDotLeft;
    private LayoutInflater inflater;
    private TextView mTvTitle;
    private List<SettingTitle>  settingTitles=new ArrayList<>();

    @Override
    protected int setContentLayout() {
        return R.layout.activity_setting_title;
    }

    @Override
    protected void initEvent() {
        init();
        mVpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
                //动态改变小红点的值
                float len = mPointDistance * positionOffset + mPointDistance * position;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVDot.getLayoutParams();
                layoutParams.leftMargin = (int) (len + mFirstDotLeft);
                //Utils.ShowToast(MainActivity.this,len+"");
                mVDot.setLayoutParams(layoutParams);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void init() {
        inflater = LayoutInflater.from(this);
        mIndicator.setIsTitle(true);
        mIndicator.setViewPager(mVpPager);
        mIndicator.setTitles(mTitles);
        String[] title1 = {"我叫老司机", "我叫老司机", "小", "我叫老司机", "老司机称霸武林", "我叫老sdff司机", "神棍上路", "我叫老司机", "我就sdf呵呵", "我叫老sdff司机", "神棍上路", "我叫老司机", "我就sdf呵呵", "我叫老sdff司机", "神棍上路", "我叫老司机", "我就sdf呵呵", "我叫老sdff司机", "神棍上路", "我叫老司机", "我就sdf呵呵"};
        String[] title2 = {"我叫老司机", "我叫老机", "小", "我叫老司机", "老司机称霸武林", "我司机", "神棍上路", "我叫老司机", "我就呵呵"};
        String[] title3 = {"我叫老司机", "我叫老司机", "小", "我叫机", "老司机称霸武林", "我叫老司机", "神棍上路", "我叫老司机", "我就呵yuk呵"};
        String[] title4 = {"我叫老司机", "我叫司机", "小", "我叫老司机", "老司机称霸武林", "我叫老司机", "神棍上路", "我叫机", "我就呵呵"};
        TabFragment tabFragment1 = TabFragment.newInstance(title1, TYPE_MY_TITLE);
        TabFragment tabFragment2 = TabFragment.newInstance(title2, TYPE_VER_TITLE);
        TabFragment tabFragment3 = TabFragment.newInstance(title3, TYPE_PLAY_WAY);
        TabFragment tabFragment4 = TabFragment.newInstance(title4, TYPE_DIY_TITLE);
        fragmentList.add(tabFragment1);
        fragmentList.add(tabFragment2);
        fragmentList.add(tabFragment3);
        fragmentList.add(tabFragment4);
        mVpPager.setAdapter(new TitlePagerAdapter(getSupportFragmentManager()));
        mVpPager.setOffscreenPageLimit(3);
        initDot();
    }

    /**
     * 初始化小圆点
     */
    private void initDot() {
        if (fragmentList == null || fragmentList.size() == 0) {
            return;
        }
        mLlDot = (LinearLayout) findViewById(R.id.ll_indicator);
        for (int i = 0; i < fragmentList.size(); i++) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.dot_for_viewpager_indicator);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dipToPx(this, 6), DensityUtils.dipToPx(this, 6));
            if (i > 0) {
                params.leftMargin = DensityUtils.dipToPx(this, 11);
            }
            view.setLayoutParams(params);
            mLlDot.addView(view);
        }
        /**
         * 绘制完成回调
         */
        mLlDot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPointDistance = mLlDot.getChildAt(1).getLeft() - mLlDot.getChildAt(0).getLeft();
                //获取第一个的左边
                mFirstDotLeft = mLlDot.getChildAt(0).getLeft();
                //初始化小圆点
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVDot.getLayoutParams();
                layoutParams.leftMargin = mFirstDotLeft;
                mVDot.setLayoutParams(layoutParams);
                //移除监听事件
                mLlDot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "设置标签";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    public class TitlePagerAdapter extends FragmentPagerAdapter {

        public TitlePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValue.selectTitleNumber = 0;
    }

    @Subscribe
    public void onEvent(SettingTitleEvent event) {
        SettingTitle settingTitle = event.getSettingTitle();
        addTitle(settingTitle);
    }

    /**
     * 添加标签
     *
     * @param settingTitle
     */
    private void addTitle(final SettingTitle settingTitle) {
        if (settingTitle.getChangeType() == TabFragment.TYPE_ADD && settingTitles.size()<7) {
            settingTitles.add(settingTitle);
            View inflate = inflater.inflate(R.layout.item_activity_setting_title_select, mFlTitle, false);
            inflate.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TabEvent tabEvent = new TabEvent();
                    tabEvent.setPosition(settingTitle.getPosition());
                    tabEvent.setType(settingTitle.getType());
                    EventBus.getDefault().post(tabEvent);
                    remove(settingTitle);
                }
            });
            mTvTitle = (TextView) inflate.findViewById(R.id.tv_title);
            mTvTitle.setText(settingTitle.getTitle());
            if (mFlTitle.getChildCount() >= 7) return;
            mFlTitle.addView(inflate);
        }else {
            remove(settingTitle);
        }
    }

    /**
     * 移除
     * @param settingTitle
     */
    private void remove(SettingTitle settingTitle) {
        SettingTitle temp=null;
        for (SettingTitle settingTitle1:settingTitles){
            if (settingTitle.getType()==settingTitle1.getType() && settingTitle.getPosition()==settingTitle1.getPosition()
                    && settingTitle.getType()==settingTitle1.getType()){
                temp=settingTitle1;
                break;
            }
        }
        int index = settingTitles.indexOf(temp);
        settingTitles.remove(temp);
        mFlTitle.removeViewAt(index);
    }
}



