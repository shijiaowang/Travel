package com.example.administrator.travel.ui.activity;

import android.graphics.Color;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ActionMenuView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.fragment.AlbumFragment;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.ui.fragment.DynamicFragment;
import com.example.administrator.travel.ui.fragment.UserInfoFragment;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.WrapViewPager;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.TypefaceUtis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class OtherUserCenterActivity extends BaseActivity {


    private String[] titles={"老司机","新司机","旧司机","486","我是老司机","新手","小清新","我勒个去去","速度"};
    private boolean isInflate=false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isInflate){
                return;
            }
            mHandler.removeCallbacksAndMessages(null);//移除所有的参数和消息
            switch (msg.what){
               case 0:
                   mPbLoad.clearAnimation();
                   mPbLoad.setVisibility(View.GONE);
                   ViewStub vsNewUser = (ViewStub) findViewById(R.id.vs_new_user);
                   vsNewUser.inflate();
                   isInflate=true;
                   break;
               case 1:
                   mPbLoad.clearAnimation();
                   mPbLoad.setVisibility(View.GONE);
                   ViewStub vsFl = (ViewStub) findViewById(R.id.vs_title);
                   vsFl.inflate();
                   isInflate=true;
                   FlowLayout mFlTitleInVs = (FlowLayout)findViewById(R.id.fl_title_in_vs);
                   for (int i=0;i<titles.length;i++){
                       TextView textView = (TextView) inflater.inflate(R.layout.item_activity_other_title_item, mFlTitleInVs, false);
                       textView.setText(titles[i]);
                       mFlTitleInVs.addView(textView);
                   }
                   break;
           }
        }
    };
    private View mPbLoad;
    private int i=-1;
    private LayoutInflater inflater;
    private AnimationSet animationSet;
    private TextView mTvPrivateIcon;
    private TextView mTvFollowIcon;
    private TextView mTvFollow;
    private LinearLayout mLlPrivate;
    private LinearLayout mLlFollow;
    private ViewPager mVpDynamic;
    private List<BaseFragment> fragments;



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


        mVpDynamic = (ViewPager) findViewById(R.id.vp_dynamic);

    }

    @Override
    protected void initListener() {
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
        fragments = new ArrayList<>();
        fragments.add(new AlbumFragment());
        fragments.add(new DynamicFragment());
        fragments.add(new UserInfoFragment());
        mVpDynamic.setAdapter(new OtherAdapter(getSupportFragmentManager()));



    }

    private void initIconFonts() {
        Typeface typeface = TypefaceUtis.getTypeface(this);
        mTvFollowIcon.setTypeface(typeface);
        mTvPrivateIcon.setTypeface(typeface);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取数据
        Random random = new Random();
        i = random.nextInt(2);
        initAnimation();
    }

    private void initAnimation() {
        animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation=new AlphaAnimation(1,0);
        alphaAnimation.setDuration(1500);
        ScaleAnimation scaleAnimation=new ScaleAnimation(0.0f,0.8f,0.0f,0.8f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setFillAfter(true);
        //
        RotateAnimation rotateAnimation=new RotateAnimation(0,8888, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
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
            mHandler.sendEmptyMessageDelayed(i,1500);
        }

    }
    class OtherAdapter extends FragmentPagerAdapter{

        public OtherAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public BaseFragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
