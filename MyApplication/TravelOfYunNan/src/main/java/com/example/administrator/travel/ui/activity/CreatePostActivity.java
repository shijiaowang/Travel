package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.CreatePostPhotoAdapter;
import com.example.administrator.travel.ui.fragment.EmojiFragment;
import com.example.administrator.travel.utils.DensityUtils;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.LogUtils;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/29 0029.
 * 发表帖子
 */
public class CreatePostActivity extends FragmentActivity implements View.OnClickListener {
    private TextView mTvAite;
    private TextView mTvPicture;
    private TextView mTvEmoji;
    private TextView mTvCreate;
    private GridView mGvPhoto;
    private ViewStub mVsPhoto;
    private ViewStub mVsEmoji;
    private ViewPager mVpEmoji;
    private List<EmojiFragment> fragments;
    private RelativeLayout mRlEmoji;
    private LinearLayout mLlDot;
    private int mPointDistance;
    private int mFirstDotLeft;
    private View mVDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        initView();
        initListener();
        initData();
    }

    private void initData() {

    }

    private void initListener() {
        mTvPicture.setOnClickListener(this);
        mTvEmoji.setOnClickListener(this);
        mTvAite.setOnClickListener(this);
    }


    private void initView() {
        mTvAite = FontsIconUtil.findIconFontsById(R.id.tv_aite, this);
        mTvPicture = FontsIconUtil.findIconFontsById(R.id.tv_picture, this);
        mTvEmoji = FontsIconUtil.findIconFontsById(R.id.tv_emoji, this);

    }

    /**
     * 初始化小圆点
     */
    private void initDot() {
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        mLlDot = (LinearLayout) findViewById(R.id.ll_indicator);
        for (int i = 0; i < fragments.size(); i++) {
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
                layoutParams.leftMargin=mFirstDotLeft;
                mVDot.setLayoutParams(layoutParams);
                //移除监听事件
                mLlDot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_picture:
                if (mVsPhoto == null) {
                    mVsPhoto = (ViewStub) findViewById(R.id.vs_photo);
                    mVsPhoto.inflate();
                    mGvPhoto = (GridView) findViewById(R.id.gv_photo);
                    mGvPhoto.setAdapter(new CreatePostPhotoAdapter(CreatePostActivity.this, null));
                } else {
                    mGvPhoto.setVisibility(mGvPhoto.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
                if (mRlEmoji != null && mRlEmoji.getVisibility() == View.VISIBLE) {
                    mRlEmoji.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_emoji:
                if (mVsEmoji == null) {
                    mVsEmoji = (ViewStub) findViewById(R.id.vs_emoji);
                    mVsEmoji.inflate();
                    mVpEmoji = (ViewPager) findViewById(R.id.vp_emoji);
                    mRlEmoji = (RelativeLayout) findViewById(R.id.rl_emoji);
                    fragments = new ArrayList<>();
                    fragments.add(new EmojiFragment());
                    fragments.add(new EmojiFragment());
                    fragments.add(new EmojiFragment());
                    fragments.add(new EmojiFragment());
                    mVpEmoji.setAdapter(new FragmentEmojiAdapter(getSupportFragmentManager()));
                    mVDot = findViewById(R.id.v_dot);
                    initDot();

                    mVpEmoji.setOnPageChangeListener(new EmojiOnPagerChangeListener());
                } else {
                    mRlEmoji.setVisibility(mRlEmoji.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

                }
                if (mGvPhoto != null && mGvPhoto.getVisibility() == View.VISIBLE) {
                    mGvPhoto.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_aite:
                startActivity(new Intent(this,AiteActivity.class));
                break;

        }

    }

    class FragmentEmojiAdapter extends FragmentPagerAdapter {

        public FragmentEmojiAdapter(FragmentManager fm) {
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

    //监听事件
    class EmojiOnPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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
    }


}
