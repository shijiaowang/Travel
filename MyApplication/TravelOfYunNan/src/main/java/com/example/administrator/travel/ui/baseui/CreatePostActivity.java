package com.example.administrator.travel.ui.baseui;

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
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.CreatePostEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.CreatePostPhotoAdapter;
import com.example.administrator.travel.ui.appoint.aite.AiteActivity;
import com.example.administrator.travel.ui.fragment.EmojiFragment;
import com.example.administrator.travel.utils.DensityUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.example.administrator.travel.utils.Xutils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29 0029.
 * 发表帖子
 */
public class CreatePostActivity extends FragmentActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final int SHOW_PHOTO = 0;
    private static final int SHOW_EMOJI = 1;
    protected static final int SEND_PICTURE = 2;//发送图片
    private static final int CREATE_POST = 3;//创建帖子
    private TextView mTvAite;
    private TextView mTvPicture;
    private TextView mTvEmoji;
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
    private EditText mEtTitle;
    private EditText mEtContent;
    private int sendDelayTime = 0;//延迟发送广播时间，解决软键盘弹出情况下，再弹出表情包等等闪屏状况
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            removeCallbacksAndMessages(null);
            switch (msg.what) {
                case SHOW_PHOTO:
                    showPicture();
                    break;
                case SHOW_EMOJI:
                    showEmoji();
                    break;
            }
        }
    };
    private TextView mTvBack;
    private TextView mTvCreate;
    private List<String> mImages;
    private List<String> pictures;
    private CreatePostPhotoAdapter createPostPhotoAdapter;
    private String addFlag="add";
    private String cId;
    private boolean isCreateing=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        cId = getIntent().getStringExtra(IVariable.C_ID);
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
        mEtTitle.setOnClickListener(this);
        mEtContent.setOnClickListener(this);
        mEtTitle.setOnFocusChangeListener(this);
        mEtContent.setOnFocusChangeListener(this);
        mTvBack.setOnClickListener(this);
        mTvCreate.setOnClickListener(this);

    }


    private void initView() {
        mTvAite = ((TextView) findViewById(R.id.tv_aite));
        mTvPicture = ((TextView) findViewById(R.id.tv_picture));
        mTvEmoji = ((TextView) findViewById(R.id.tv_emoji));
        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtContent = (EditText) findViewById(R.id.et_content);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mTvBack = ((TextView) findViewById(R.id.tv_back));

        mTvCreate = ((TextView) findViewById(R.id.tv_push));
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
                layoutParams.leftMargin = mFirstDotLeft;
                mVDot.setLayoutParams(layoutParams);
                //移除监听事件
                mLlDot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mEtTitle.setFocusable(true);
        mEtTitle.requestFocus();
        //第一次进入强制显示软键盘
        if (!imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_push:
                createPost();
                break;
            case R.id.et_title:
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
        sendDelayTime = 0;//初始化
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(mEtTitle.getWindowToken(), 0); //强制隐藏键盘
            sendDelayTime = 300;
        }
        switch (v.getId()) {
            case R.id.tv_picture:
                mHandler.sendEmptyMessageDelayed(SHOW_PHOTO, sendDelayTime);
                return;
            case R.id.tv_emoji:
                mHandler.sendEmptyMessageDelayed(SHOW_EMOJI, sendDelayTime);
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
            pictures = new ArrayList<>();
            pictures.add(addFlag);
            createPostPhotoAdapter = new CreatePostPhotoAdapter(CreatePostActivity.this, pictures);
            mGvPhoto.setAdapter(createPostPhotoAdapter);
            mGvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (pictures == null) return;
                    if (pictures.size() != 12 && position == pictures.size() - 1) {
                        startActivity(new Intent(CreatePostActivity.this, AlbumSelectorActivity.class));
                        GlobalValue.mSelectImages = pictures;
                        GlobalValue.mSelectImages.remove(addFlag);
                    }
                }
            });
        } else {
            mGvPhoto.setVisibility(mGvPhoto.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
        if (mRlEmoji != null && mRlEmoji.getVisibility() == View.VISIBLE) {
            mRlEmoji.setVisibility(View.GONE);
        }
    }


    private void hideEmojiOrPhoto() {
        if (mRlEmoji != null && mRlEmoji.getVisibility() == View.VISIBLE) {
            mRlEmoji.setVisibility(View.GONE);
        }
        if (mGvPhoto != null && mGvPhoto.getVisibility() == View.VISIBLE) {
            mGvPhoto.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
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

    /**
     * 创建帖子
     */
    private void createPost() {
        String title = mEtTitle.getText().toString().trim();
        String content = mEtContent.getText().toString().trim();
        if (StringUtils.isEmpty(content)) {
            ToastUtils.showToast("请输入内容");
            return;
        }

        if (isCreateing)return;
        isCreateing = true;

        Map<String, String> createPostMap = Xutils.getCreatePostMap(GlobalUtils.getKey(this), title, content,cId);
        XEventUtils.postFileCommonBackJson(IVariable.CIRCLE_CREATE_POST,createPostMap,pictures,CREATE_POST,new CreatePostEvent());
    }

    @Subscribe
    public void onEvent(CreatePostEvent event) {
        isCreateing=false;
        if (event.isSuccess()) {
            dealData(event);
        } else {
            ToastUtils.showToast(event.getMessage());
        }
    }

    private void dealData(CreatePostEvent event) {
        if (event.getType() == SEND_PICTURE) {
            pictures.clear();
            pictures.addAll(event.getmImages());
            if (pictures.size() != 12 && !pictures.contains("add")) {
                pictures.add(addFlag);
            }
            createPostPhotoAdapter.notifyData(pictures);
        } else {
            ToastUtils.showToast("创建成功");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        GlobalValue.mSelectImages=null;

    }
}
