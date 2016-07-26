package com.example.administrator.travel.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.view.SlippingScrollView;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/25 0025.
 * 带有相同头布局
 */
public abstract class BarBaseActivity extends BaseActivity {
    private static final float CHANGE_COLOR_LIMIT=600f;//设置变色区间
    private TextView mTvBack;
    private TextView mTitleName;
    private View mBg1;

    private int normalBgColor= Color.parseColor("#5cd0c2");
    private float alpha=1.0f;
    private ViewStub mVsContent;
    private ViewStub mVsRightIcon;
    private SlippingScrollView mSsvScroll;

    @Override
    protected int initLayoutRes() {
        if (rootIsLinearLayout()){
            return R.layout.activity_bar_base_ll;//竖直布局
        }
        return R.layout.activity_bar_base_rl;//重叠布局
    }

    protected  boolean rootIsLinearLayout(){
        return true;
    }

    /**
     * 获取背景
     * @return
     */
    public View getmBg1() {
        return mBg1;
    }

    @Override
    protected void initView() {
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
        mTitleName = (TextView) findViewById(R.id.tv_name);
        mVsContent = (ViewStub) findViewById(R.id.vs_content);
        mBg1 = findViewById(R.id.bg_1);
        mVsContent.setLayoutResource(setContentLayout());
        mVsContent.inflate();
        if (haveRightIcon()){
            mVsRightIcon = (ViewStub) findViewById(R.id.vs_right_icon);
        }
        if (canScrollToChangeTitleBgColor()){
            mSsvScroll = (SlippingScrollView) findViewById(R.id.ssv_scroll);
        }
        initContentView();
    }

    /**
     * 是否能够跟随滑动改变背景颜色，一定要有SlippingScrollView且id固定不变！
     * @return
     */
    protected  boolean canScrollToChangeTitleBgColor(){
        return false;
    }

    protected  boolean haveRightIcon(){
        return false;
    }

    /**
     * 如果需要动态添加右边的图标
     * @return
     */
    public ViewStub getmVsRightIcon() {
        return mVsRightIcon;
    }

    /**
     * 初始化布局
     */
    protected abstract void initContentView();

    /**
     * 设置布局
     * @return
     */
    protected abstract int setContentLayout();

    @Override
    protected void initListener() {
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (canScrollToChangeTitleBgColor() && mSsvScroll!=null){
            mSsvScroll.setSlippingListener(new SlippingScrollView.SlippingListener() {
                @Override
                public void slipping(int l, int i, int oldl, int t) {
                    getmBg1().setAlpha(Math.abs(t/CHANGE_COLOR_LIMIT)>1?1f:Math.abs(t/CHANGE_COLOR_LIMIT));
                }
            });
        }
        initEvent();
    }

    protected abstract void initEvent();


    @Override
    protected void initData() {
        mTitleName.setText(setTitleName());
        mBg1.setBackgroundColor(getBgColor());
        mBg1.setAlpha(getAlpha());

        initViewData();
    }




    /**
     * 初始化数据
     */
    protected abstract void initViewData();



    /**
     * 设置标题
     * @return
     */
    protected abstract String setTitleName();



    /**
     * 背景颜色
     * @return
     */
    public int getBgColor() {
        return normalBgColor;
    }

    /**
     * 获取背景透明度
     * @return
     */
    public float getAlpha() {
        return alpha;
    }
}
