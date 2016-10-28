package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.settingtitle;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.ui.view.SimpleViewPagerIndicator;
import com.yunspeak.travel.utils.DensityUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/9/6 0006.
 * 设置标签页面
 */
public class SettingTitleActivity extends BaseNetWorkActivity<AddTitleEvent> {
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
    private boolean isSure=false;//如果用户一旦确认过就一只保存标签，除非用户清除
    private int mPointDistance;
    private int mFirstDotLeft;
    private LayoutInflater inflater;
    private TextView mTvTitle;
    private List<SettingTitle>  settingTitles=new ArrayList<>();





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
        settingTitles= (List<SettingTitle>) getIntent().getSerializableExtra(IVariable.DATA);
        inflater = LayoutInflater.from(this);
        if (settingTitles!=null){
            for (SettingTitle settingTitle:settingTitles) {
                addTitle(settingTitle);
            }
        }

        mIndicator.setIsTitle(true);
        mIndicator.setViewPager(mVpPager);
        mIndicator.setTitles(mTitles);

    }

    /**
     * 确定选择
     */
    private void sureTitle() {
        StringBuffer sb=new StringBuffer("");
        for (SettingTitle settingTitle:settingTitles){
            sb.append(settingTitle.getTitle()+",");
        }
        JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
        try {
            String label = sb.toString();
            if (!StringUtils.isEmpty(label)){
                 label.substring(label.length()-1,label.length());
            }
            basecJsonObject.put(IVariable.LABEL,label);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setResult(RESULT_CODE,new Intent().putExtra(IVariable.DATA, (Serializable) settingTitles));
        finish();
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
         sureTitle();
    }

    @Override
    protected String initRightText() {
        return "确定";
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
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return IVariable.GET_TITLE_LIST;
    }


    private void dealData(AddTitleEvent event){
        SettingTitleBean settingTitleBean = GsonUtils.getObject(event.getResult(), SettingTitleBean.class);
        List<UserLabelBean> userLabel = settingTitleBean.getData().getUser_label();
        List<UserLabelBean> platformLabel = settingTitleBean.getData().getPlatform_label();
        List<UserLabelBean> playWayLabel = settingTitleBean.getData().getPlay_way();
        TabFragment tabFragment1 = TabFragment.newInstance(userLabel, TYPE_MY_TITLE,settingTitles);
        TabFragment tabFragment2 = TabFragment.newInstance(platformLabel, TYPE_VER_TITLE,settingTitles);
        TabFragment tabFragment3 = TabFragment.newInstance(playWayLabel, TYPE_PLAY_WAY,settingTitles);
        TabFragment tabFragment4 = TabFragment.newInstance(userLabel, TYPE_DIY_TITLE,settingTitles);
        fragmentList.add(tabFragment1);
        fragmentList.add(tabFragment2);
        fragmentList.add(tabFragment3);
        fragmentList.add(tabFragment4);
        mVpPager.setAdapter(new TitlePagerAdapter(getSupportFragmentManager()));
        mVpPager.setOffscreenPageLimit(3);
        initDot();
    }


    @Override
    protected void onSuccess(AddTitleEvent addTitleEvent) {
        dealData(addTitleEvent);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_setting_title;
    }

    @Override
    protected String initTitle() {
        return "设置标签";
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
        LogUtils.e("设置标签页面被销毁了");
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
        if (settingTitles==null)settingTitles=new ArrayList<>();
        if (settingTitle.getChangeType() == TabFragment.TYPE_ADD && settingTitles.size()<7) {
            if (!settingTitles.contains(settingTitle)){
                settingTitles.add(settingTitle);
            }
            View inflate = inflater.inflate(R.layout.item_activity_setting_title_select, mFlTitle, false);
            inflate.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TabEvent tabEvent = new TabEvent();
                    tabEvent.setId(settingTitle.getId());
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
        for (SettingTitle settingTitle1:settingTitles){
            if (settingTitle1.getId().equals(settingTitle.getId())){
                int index = settingTitles.indexOf(settingTitle1);
                settingTitles.remove(settingTitle1);
                mFlTitle.removeViewAt(index);
                break;
            }
        }

    }
}



