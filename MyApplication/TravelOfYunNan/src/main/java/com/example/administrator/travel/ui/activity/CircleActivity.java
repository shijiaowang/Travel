package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.CircleAdapter;
import com.example.administrator.travel.ui.view.SlippingScrollView;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.TypefaceUtis;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvBack;
    private TextView mCreatePost;
    private ToShowAllListView mLvPost;
    private SlippingScrollView mSvScroll;
    private ImageView mIvPostBg;
    int [] location=new int[2];
    private View mLlTitleBg;
    private int mTitleBgHeight;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_circle;
    }

    @Override
    protected void initView() {
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mCreatePost = (TextView) findViewById(R.id.tv_create_post);
        mLvPost = (ToShowAllListView) findViewById(R.id.lv_post);
        mSvScroll = (SlippingScrollView) findViewById(R.id.sv_scroll);
        mIvPostBg = (ImageView) findViewById(R.id.iv_post_bg);
        mLlTitleBg = findViewById(R.id.ll_title_bg);



        initFontsIcon();
    }

    private void initFontsIcon() {
        Typeface fromAsset = TypefaceUtis.getTypeface(this);
        mTvBack.setTypeface(fromAsset);
        mCreatePost.setTypeface(fromAsset);
    }

    @Override
    protected void initListener() {
        mTvBack.setOnClickListener(this);
        mLvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CircleActivity.this,PostActivity.class));
            }
        });

        //监听滑动和惯性滑动
        mSvScroll.setSlippingListener(new SlippingScrollView.SlippingListener() {
            @Override
            public void slipping(int l, int i, int oldl, int t) {
                if (mTitleBgHeight<=0) {
                    mTitleBgHeight = mIvPostBg.getHeight();
                    mIvPostBg.getLocationInWindow(location);
                }
                if (location!=null&&location[1]>=mTitleBgHeight){
                    //不做操作
                }else {
                    mIvPostBg.getLocationInWindow(location);
                    changeBarBg((float)location[1]/mTitleBgHeight);
                }
            }
        });



    }

    /**
     * 改变顶部操作栏颜色
     */
    private void changeBarBg(float alpha) {
        if (mLlTitleBg!=null){
            mLlTitleBg.setAlpha(Math.abs(alpha));
        }
    }

    @Override
    protected void initData() {
        mLvPost.setAdapter(new CircleAdapter(this, null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
