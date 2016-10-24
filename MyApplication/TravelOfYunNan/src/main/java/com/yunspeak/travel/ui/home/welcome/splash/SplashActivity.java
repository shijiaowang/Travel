package com.yunspeak.travel.ui.home.welcome.splash;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Splash;
import com.yunspeak.travel.ui.baseui.FullTransparencyActivity;
import com.yunspeak.travel.ui.baseui.RegisterSuccessActivity;
import com.yunspeak.travel.ui.home.welcome.splash.login.LoginActivity;
import com.yunspeak.travel.ui.home.welcome.splash.register.RegisterActivity;
import com.yunspeak.travel.utils.DensityUtils;


import org.xutils.view.annotation.ViewInject;


import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends FullTransparencyActivity {
    private boolean isClicking=false;
    private static final String CURRENT_POSITION = "current_position";
    public static final int GO_LOGIN = 0;
    public static final int GO_REGISTER = 1;
    private static final int GO_REGISTER_SUCCESS = 2;
    @ViewInject(R.id.vp_splash)
    private ViewPager mVpSplash;
    @ViewInject(R.id.vv_video)
    private VideoView mVideoView;
    private int currentPosition;
    @ViewInject(R.id.bt_login)
    private Button mBtLogin;
    @ViewInject(R.id.v_dot)
    private View mVDot;
    @ViewInject(R.id.rl_toggle)
    private RelativeLayout mRlToggle;
    @ViewInject(R.id.bt_register)
    private Button mBtRegister;
    private LinearLayout mLlDot;
    private int mPointDistance;
    private int mFirstDotLeft;
    private List<Splash> splashs=new ArrayList<>();
    private List<View> mViews=new ArrayList<>();
    private Handler mHandler=new Handler(){
      @Override
      public void handleMessage(Message msg) {
          int position=mVpSplash.getCurrentItem()+1;
          mVpSplash.setCurrentItem(position,true);
         mHandler.sendEmptyMessageDelayed(0,3000);
      }
  };
    @Override
    protected int initContentRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        initVideoView();
    }

    private void initVideoView() {
        mVideoView = (VideoView) findViewById(R.id.vv_video);
        Uri parse = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.entrance_video);
        mVideoView.setVideoURI(parse);
    }

    @Override
    protected void initListener() {
        mVpSplash.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int rightPosition=position%mViews.size();
                if (rightPosition<mViews.size()-1) {
                    //动态改变小红点的值
                    float len = mPointDistance * positionOffset + mPointDistance * rightPosition;
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVDot.getLayoutParams();
                    layoutParams.leftMargin = (int) (len + mFirstDotLeft);
                    //Utils.ShowToast(MainActivity.this,len+"");
                    mVDot.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 gotoWhere(GO_REGISTER);
            }
        });
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoWhere(GO_LOGIN);

            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.start();
            }
        });
    }

    /**
     * 登录 或者注册
     * @param type
     */
    private void gotoWhere(int type) {
        if (isClicking)return;
        isClicking=true;
        if (type==GO_LOGIN){
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivityForResult(intent, GO_LOGIN);
        }else {
            Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
            startActivityForResult(intent, GO_REGISTER);
        }
        mRlToggle.setVisibility(View.GONE);
        isClicking=false;
    }

    @Override
    protected void initData() {

        splashs.add(new Splash("偶遇", "偶遇是一种进结果"));
        splashs.add(new Splash("睡觉","睡不着起来唱山歌"));
        splashs.add(new Splash("风景","风景是一种阿萨德法师法飞"));
        splashs.add(new Splash("美女", "那是没有滴"));
        mVpSplash.setAdapter(new SplashAdapter());
        int position=(Integer.MAX_VALUE/2)-(Integer.MAX_VALUE/2)%4;
        mVpSplash.setOffscreenPageLimit(1);
        mVpSplash.setCurrentItem(position, false);
        mHandler.sendEmptyMessageDelayed(0, 3000);
        mViews.add(initPagerView(0));
        mViews.add(initPagerView(1));
        mViews.add(initPagerView(2));
        mViews.add(initPagerView(3));
       initDot();
    }

    private View initPagerView(int current) {
        View view = LayoutInflater.from(SplashActivity.this).inflate(R.layout.item_activity_splash, null, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(splashs.get(current).getTitle());
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvContent.setText(splashs.get(current).getContent());
        return view;
    }

    /**
     * 初始化小圆点
     */
    private void initDot() {

        mLlDot = (LinearLayout) findViewById(R.id.ll_indicator);
        for (int i = 0; i < splashs.size(); i++) {
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
    protected void onStart() {
        super.onStart();

        if (mVideoView == null) {
            initVideoView();
        }
        mVideoView.seekTo(currentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPosition = savedInstanceState.getInt(CURRENT_POSITION, 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_POSITION, currentPosition);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onResume() {
        if (mRlToggle.getVisibility()==View.GONE){
            mRlToggle.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_LOGIN && resultCode == LoginActivity.SPLASH_RESULT) {
            finish();
        }
        if (requestCode == GO_REGISTER_SUCCESS && resultCode == RegisterSuccessActivity.SPLASH_RESULT) {
            finish();
        }
        if (requestCode == GO_REGISTER && resultCode == RegisterActivity.REGISTER_SUCCESS) {
            if (data!=null) {
                data.setClass(this, RegisterSuccessActivity.class);
                startActivityForResult(data,GO_REGISTER_SUCCESS);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();
        currentPosition = mVideoView.getCurrentPosition();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.pause();
            mVideoView = null;
        }
    }



    class SplashAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int current=position%4;
            if (current<0){
                current = mViews.size()+position;
            }
            View view=mViews.get(current);
            ViewParent vp =view.getParent();
            if (vp!=null){
                ViewGroup parent = (ViewGroup)vp;
                parent.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
        //之前已经处理了
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
    }
}
