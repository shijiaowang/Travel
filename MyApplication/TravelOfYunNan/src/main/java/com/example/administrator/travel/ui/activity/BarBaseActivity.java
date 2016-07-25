package com.example.administrator.travel.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/25 0025.
 * 带有相同头布局
 */
public abstract class BarBaseActivity extends BaseActivity {

    private TextView mTvBack;
    private TextView mTitleName;
    private View mBg1;

    private int normalBgColor= Color.parseColor("#5cd0c2");
    private float alpha=1.0f;
    private ViewStub mVsContent;
    private ViewStub mVsRightIcon;

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
        initContentView();
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
