package com.example.administrator.travel.ui.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.AlbumAdapter;
import com.example.administrator.travel.ui.adapter.DynamicAdapter;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.SlippingScrollView;
import com.example.administrator.travel.ui.view.ToShowAllGridView;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.TypefaceUtis;

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


    private ToShowAllGridView mGvAlbum;
    private ToShowAllListView mLvDynamic;
    private LinearLayout mLlInfo;
    private DynamicAdapter dynamicAdapter;
    private AlbumAdapter albumAdapter;
    private TextView mTvFlyAlbum;
    private TextView mTvFlyDynamic;
    private TextView mTvFlyInfo;
    private LinearLayout mLlFly;
    private SlippingScrollView mSvMove;
    private LinearLayout mLlCursor;
    private View mVHeight;//状态栏高度，辅助变色
    public int[] flyLocation=new int[2];


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

        mSvMove = (SlippingScrollView) findViewById(R.id.sv_move);
        mLlCursor = (LinearLayout) findViewById(R.id.ll_cursor);

        mTvFlyAlbum = (TextView) findViewById(R.id.tv_fly_album);
        mTvFlyDynamic = (TextView) findViewById(R.id.tv_fly_dynamic);
        mTvFlyInfo = (TextView) findViewById(R.id.tv_fly_info);
        mLlFly = (LinearLayout)findViewById(R.id.ll_fly);

        mLlInfo = (LinearLayout)findViewById(R.id.ll_info);
        mLvDynamic = (ToShowAllListView)findViewById(R.id.lv_dynamic);
        mGvAlbum = ((ToShowAllGridView) findViewById(R.id.gv_album));

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
        mSvMove.setSlippingListener(new SlippingScrollView.SlippingListener() {
            private boolean isFirst=true;
            private int flyHeight=0;//顶部悬浮cursor的高度
            @Override
            public void slipping(int l, int i, int oldl, int t) {
                //System.out.println("l"+l+"i"+i+"ol"+oldl+"t"+t);
                if (isFirst){
                    isFirst=false;
                    mLlFly.getLocationInWindow(flyLocation);
                    mLlCursor.getLocationInWindow(location);
                    System.out.println(flyLocation[1] + "没漂浮的" + location[1]);
                    flyHeight=location[1]-flyLocation[1];

                }
                if (t<flyHeight){
                    if (mLlFly.getVisibility()==View.VISIBLE) {
                        mLlFly.setVisibility(View.GONE);//如果比悬浮框高就悬浮框隐藏
                    }
                    mLlCursor.setVisibility(View.VISIBLE);
                    if (mVHeight.getAlpha()>0){
                        float alpha = mVHeight.getAlpha();
                        AlphaAnimation animation=new AlphaAnimation(alpha,0);
                        animation.setDuration(500);
                        animation.setFillAfter(true);
                        mVHeight.startAnimation(animation);

                    }

                }else {
                    mVHeight.clearAnimation();
                    mLlCursor.setVisibility(View.INVISIBLE);
                    if (mLlFly.getVisibility()!=View.VISIBLE) {
                        mLlFly.setVisibility(View.VISIBLE);//如果比悬浮框高就悬浮框隐藏
                    }//显示
                    //设置背景透明度
                    float alpha = Math.abs((float) t-flyHeight) / flyHeight;
                    alpha=alpha>1?1:alpha;
                   // System.out.println(alpha+"");
                    mVHeight.setAlpha(alpha);
                }
            }
        });
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

    /**
     * 设置展示的View
     *
     * @param view
     */
    private void initShow(View view) {
       if (view==mLlInfo){
           mLlInfo.setVisibility(View.VISIBLE);
           mLvDynamic.setVisibility(View.GONE);
           mGvAlbum.setVisibility(View.GONE);
       }else if (view==mLvDynamic){
           mLlInfo.setVisibility(View.GONE);
           mLvDynamic.setVisibility(View.VISIBLE);
           mGvAlbum.setVisibility(View.GONE);
       }else {
           mLlInfo.setVisibility(View.GONE);
           mLvDynamic.setVisibility(View.GONE);
           mGvAlbum.setVisibility(View.VISIBLE);
       }
    }

    @Override
    protected void initData() {
        initIconFonts();
        if (albumAdapter==null) {
            albumAdapter = new AlbumAdapter(this, null);
            mGvAlbum.setAdapter(albumAdapter);
            initShow(mGvAlbum);
        }

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
                initShow(mGvAlbum);
                break;
            case R.id.tv_dynamic:
            case R.id.tv_fly_dynamic:
                if (dynamicAdapter==null) {
                    dynamicAdapter = new DynamicAdapter(OtherUserCenterActivity.this, null);
                    mLvDynamic.setAdapter(dynamicAdapter);
                }
                initShow(mLvDynamic);
                break;
            case R.id.tv_info:
            case R.id.tv_fly_info:
                initShow(mLlInfo);
                break;
        }
    }
}
