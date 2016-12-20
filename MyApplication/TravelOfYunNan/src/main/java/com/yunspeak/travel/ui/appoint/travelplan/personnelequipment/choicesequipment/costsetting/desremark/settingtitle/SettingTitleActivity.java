package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.settingtitle;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.SettingTitleBean;
import com.yunspeak.travel.bean.UserLabelBean;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.view.PagerCursorView;
import com.yunspeak.travel.ui.view.SimpleViewPagerIndicator;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/6 0006.
 * 设置标签页面
 */
public class SettingTitleActivity extends BaseNetWorkActivity<SettingTitleEvent> {
    private static final int TYPE_MY_TITLE = 0;
    private static final int TYPE_VER_TITLE = 1;//认证标志
    private static final int TYPE_PLAY_WAY = 2;//玩法
    private static final int TYPE_DIY_TITLE = 3;
    private String[] mTitles = {"我的称号", "认证标志", "玩法"};
    private List<Fragment> fragmentList = new ArrayList<>();
    @BindView(R.id.vp_pager) ViewPager mVpPager;
    @BindView(R.id.indicator) SimpleViewPagerIndicator mIndicator;
    @BindView(R.id.fl_title) FlexboxLayout mFlTitle;
    @BindView(R.id.pager_cursor)
    PagerCursorView mPagerCursorView;
    private boolean isSure=false;//如果用户一旦确认过就一只保存标签，除非用户清除
    private LayoutInflater inflater;
    private TextView mTvTitle;
    private List<SettingTitle>  settingTitles=new ArrayList<>();





    @Override
    protected void initEvent() {
        init();
        mPagerCursorView.setPagerOnChangeListener(new PagerCursorView.PagerOnChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position,positionOffset);
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
                insertTitle(settingTitle);
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
            sb.append(settingTitle.getTitle()).append(",");
        }
        JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
        try {
            String label = sb.toString();
            if (!StringUtils.isEmpty(label)){
                 label=label.substring(0,label.length()-1);
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



    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return IVariable.GET_TITLE_LIST;
    }


    private void dealData(SettingTitleEvent event){
        SettingTitleBean settingTitleBean = GsonUtils.getObject(event.getResult(), SettingTitleBean.class);
        List<UserLabelBean> userLabel = settingTitleBean.getData().getUser_label();
        List<UserLabelBean> platformLabel = settingTitleBean.getData().getPlatform_label();
        List<UserLabelBean> playWayLabel = settingTitleBean.getData().getPlay_way();
        TabFragment tabFragment1 = TabFragment.newInstance(userLabel, TYPE_MY_TITLE,settingTitles);
        TabFragment tabFragment2 = TabFragment.newInstance(platformLabel, TYPE_VER_TITLE,settingTitles);
        TabFragment tabFragment3 = TabFragment.newInstance(playWayLabel, TYPE_PLAY_WAY,settingTitles);
        fragmentList.add(tabFragment1);
        fragmentList.add(tabFragment2);
        fragmentList.add(tabFragment3);
        mPagerCursorView.setViewPager(mVpPager,fragmentList.size(),false,null);
        mVpPager.setOffscreenPageLimit(2);
        mVpPager.setAdapter(new TitlePagerAdapter(getSupportFragmentManager()));

    }


    @Override
    protected void onSuccess(SettingTitleEvent addTitleEvent) {
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
    }


    @Subscribe
    public void onEvent(AddTitleEvent event) {
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
            insertTitle(settingTitle);
        }else {
            remove(settingTitle);
        }
    }

    /**
     * 添加
     * @param settingTitle
     */
    private void insertTitle(final SettingTitle settingTitle) {
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
    }


    /**
     * 移除
     * @param settingTitle
     */
    private void remove(SettingTitle settingTitle) {
        for (SettingTitle settingTitle1:settingTitles){
            if (settingTitle1.getId().equals(settingTitle.getId())){
                int index = settingTitles.indexOf(settingTitle1);
                if (index!=-1) {
                    settingTitles.remove(settingTitle1);
                    mFlTitle.removeViewAt(index);
                }
                break;
            }
        }

    }
}



