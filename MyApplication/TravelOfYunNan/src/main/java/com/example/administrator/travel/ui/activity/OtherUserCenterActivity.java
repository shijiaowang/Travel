package com.example.administrator.travel.ui.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;

import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.ui.fragment.DynamicFragment;
import com.example.administrator.travel.ui.fragment.InformationFragment;
import com.example.administrator.travel.ui.view.CustomScrollView;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.utils.TypefaceUtis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class OtherUserCenterActivity extends BaseActivity implements View.OnClickListener{

    private int[] location=new int[2];
    private String[] titles = {"老司机", "新司机", "旧司机", "486", "我是老司机", "新手", "小清新", "我勒个去去", "速度"};
    private boolean isInflate = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isInflate) {
                return;
            }
            mHandler.removeCallbacksAndMessages(null);//移除所有的参数和消息
            switch (msg.what) {
                case 0:
                    mPbLoad.clearAnimation();
                    mPbLoad.setVisibility(View.GONE);
                    ViewStub vsNewUser = (ViewStub) findViewById(R.id.vs_new_user);
                    vsNewUser.inflate();
                    isInflate = true;
                    break;
                case 1:
                    mPbLoad.clearAnimation();
                    mPbLoad.setVisibility(View.GONE);
                    ViewStub vsFl = (ViewStub) findViewById(R.id.vs_title);
                    vsFl.inflate();
                    isInflate = true;
                    FlowLayout mFlTitleInVs = (FlowLayout) findViewById(R.id.fl_title_in_vs);
                    for (int i = 0; i < titles.length; i++) {
                        TextView textView = (TextView) inflater.inflate(R.layout.item_activity_other_title_item, mFlTitleInVs, false);
                        textView.setText(titles[i]);
                        mFlTitleInVs.addView(textView);
                    }
                    break;
            }
        }
    };
    private View mPbLoad;
    private int i = -1;
    private LayoutInflater inflater;
    private AnimationSet animationSet;
    private TextView mTvPrivateIcon;
    private TextView mTvFollowIcon;
    private TextView mTvFollow;
    private LinearLayout mLlPrivate;
    private LinearLayout mLlFollow;
    private TextView mTvAlbum;
    private TextView mTvInfo;
    private TextView mTvDynamic;

    private List<BaseFragment> fragmentList=new ArrayList<>(3);
    private LinearLayout mLlInfo;

    private TextView mTvFlyAlbum;
    private TextView mTvFlyDynamic;
    private TextView mTvFlyInfo;
    private LinearLayout mLlFly;
    private CustomScrollView mSvMove;
    private LinearLayout mLlCursor;
    private View mVHeight;//状态栏高度，辅助变色
    public int[] flyLocation=new int[2];
    private ViewPager mVpDynamic;


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_other_user_center;
    }

    @Override
    protected void initView() {
        mPbLoad = findViewById(R.id.pb_load);
        inflater = LayoutInflater.from(this);
        mTvPrivateIcon = (TextView) findViewById(R.id.tv_private_icon);
        mTvFollowIcon = (TextView) findViewById(R.id.tv_follow_icon);
        mTvFollow = (TextView) findViewById(R.id.tv_follow);
        mLlPrivate = (LinearLayout) findViewById(R.id.ll_private);
        mLlFollow = (LinearLayout) findViewById(R.id.ll_follow);
        mTvAlbum = (TextView) findViewById(R.id.tv_album);
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mTvDynamic = (TextView) findViewById(R.id.tv_dynamic);

        mSvMove = (CustomScrollView) findViewById(R.id.sv_move);
        mLlCursor = (LinearLayout) findViewById(R.id.ll_cursor);

        mTvFlyAlbum = (TextView) findViewById(R.id.tv_fly_album);
        mTvFlyDynamic = (TextView) findViewById(R.id.tv_fly_dynamic);
        mTvFlyInfo = (TextView) findViewById(R.id.tv_fly_info);
        mLlFly = (LinearLayout)findViewById(R.id.ll_fly);
        mVpDynamic = (ViewPager) findViewById(R.id.vp_dynamic);


        mVHeight = findViewById(R.id.v_height);

    }

    @Override
    protected void initListener() {
        mTvDynamic.setOnClickListener(this);
        mTvFlyDynamic.setOnClickListener(this);
        mTvInfo.setOnClickListener(this);
        mTvFlyInfo.setOnClickListener(this);
        mTvFlyAlbum.setOnClickListener(this);
        mTvAlbum.setOnClickListener(this);
        mLlFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlFollow.setBackgroundResource(R.drawable.activity_other_follow);
                mTvFollow.setTextColor(Color.parseColor("#fafafa"));
                mTvFollowIcon.setTextColor(Color.parseColor("#fafafa"));
                mTvFollow.setText("已关注");
                mTvFollowIcon.setText(getResources().getString(R.string.activity_other_followed));
            }
        });

    }




    @Override
    protected void initData() {
        initIconFonts();
        fragmentList.add(new DynamicFragment());
        fragmentList.add(new DynamicFragment());
        fragmentList.add(new InformationFragment());
        mVpDynamic.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        ViewGroup.LayoutParams layoutParams = mVpDynamic.getLayoutParams();
        layoutParams.height=2890;
        mVpDynamic.setLayoutParams(layoutParams);

        //获取数据
        Random random = new Random();
        i = random.nextInt(2);
        initAnimation();
    }

    private void initIconFonts() {
        Typeface typeface = TypefaceUtis.getTypeface(this);
        mTvFollowIcon.setTypeface(typeface);
        mTvPrivateIcon.setTypeface(typeface);
    }


    private void initAnimation() {
        animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1500);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 0.8f, 0.0f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setFillAfter(true);
        //
        RotateAnimation rotateAnimation = new RotateAnimation(0, 8888, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(10000);
        rotateAnimation.setFillAfter(true);


        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);


    }

    @Override
    protected void onResume() {
        super.onResume();


        if (!isInflate) {
            mPbLoad.startAnimation(animationSet);
            mHandler.sendEmptyMessageDelayed(i, 1500);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_album:
            case R.id.tv_fly_album:

                break;
            case R.id.tv_dynamic:
            case R.id.tv_fly_dynamic:

                break;
            case R.id.tv_info:
            case R.id.tv_fly_info:

                break;
        }
    }
    class MyPagerAdapter extends FragmentPagerAdapter{
        public MyPagerAdapter(FragmentManager fm) {
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
}
