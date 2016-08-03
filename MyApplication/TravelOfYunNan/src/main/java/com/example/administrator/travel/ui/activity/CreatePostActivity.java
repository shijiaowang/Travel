package com.example.administrator.travel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.ui.adapter.CreatePostPhotoAdapter;
import com.example.administrator.travel.ui.fragment.EmojiFragment;
import com.example.administrator.travel.utils.DensityUtils;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.LogUtils;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 * Created by Administrator on 2016/7/29 0029.
 * 发表帖子
 */
public class CreatePostActivity extends FragmentActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final int SHOW_PHOTO=0;
    private static final int SHOW_EMOJI=1;
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
    private InputMethodManager imm;
    private EditText mEtEnter;
    private EditText mEtContent;
    private int sendDelayTime=0;//延迟发送广播时间，解决软键盘弹出情况下，再弹出表情包等等闪屏状况
   private Handler mHandler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           removeCallbacksAndMessages(null);
           switch (msg.what){
               case SHOW_PHOTO:
                   showPicture();
                   break;
               case SHOW_EMOJI:
                   showEmoji();
                   break;
           }
       }
   };
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
        mEtEnter.setOnClickListener(this);
        mEtContent.setOnClickListener(this);
        mEtEnter.setOnFocusChangeListener(this);
        mEtContent.setOnFocusChangeListener(this);

    }


    private void initView() {
        mTvAite = FontsIconUtil.findIconFontsById(R.id.tv_aite, this);
        mTvPicture = FontsIconUtil.findIconFontsById(R.id.tv_picture, this);
        mTvEmoji = FontsIconUtil.findIconFontsById(R.id.tv_emoji, this);
        mEtEnter = (EditText) findViewById(R.id.et_enter);
        mEtContent = (EditText) findViewById(R.id.et_content);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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
    protected void onResume() {
        super.onResume();
        mEtEnter.setFocusable(true);
        mEtEnter.requestFocus();
        //第一次进入强制显示软键盘
        if(!imm.isActive()){
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_enter:
            case R.id.et_content:
                 hideEmojiOrPhoto();
                break;
            case R.id.tv_aite:
                startActivity(new Intent(this, AiteActivity.class));
                break;
            default:
                hideSoftClick(v);
                break;

        }

    }

    private void hideSoftClick(View v) {
        sendDelayTime=0;//初始化
        if (imm.isActive()){
            imm.hideSoftInputFromWindow(mEtEnter.getWindowToken(), 0); //强制隐藏键盘
            sendDelayTime=300;
        }
        switch (v.getId()) {

            case R.id.tv_picture:
                mHandler.sendEmptyMessageDelayed(SHOW_PHOTO,sendDelayTime);
                return;
            case R.id.tv_emoji:
                mHandler.sendEmptyMessageDelayed(SHOW_EMOJI,sendDelayTime);
                break;
        }
    }

    private void showEmoji() {
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
    }

    private void showPicture() {
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
    }


    private void hideEmojiOrPhoto() {
        if (mRlEmoji!=null && mRlEmoji.getVisibility()== View.VISIBLE){
            mRlEmoji.setVisibility(View.GONE);
        }
        if (mGvPhoto!=null && mGvPhoto.getVisibility()==View.VISIBLE){
            mGvPhoto.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            hideEmojiOrPhoto();
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
