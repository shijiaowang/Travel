package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22 0022.
 * 搜索页面导航条
 */
public class FontsIconCursorView extends LinearLayout implements View.OnClickListener {
    private ViewPager mViewPager;
    private List<TextView> textViews=new ArrayList<>(4);
    private List<TextView> textIcons=new ArrayList<>(4);
    public FontsIconCursorView(Context context) {
        this(context, null);
    }

    public FontsIconCursorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontsIconCursorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initView() {
        LinearLayout mLlUser = (LinearLayout) this.findViewById(R.id.ll_user);
        LinearLayout mLlDestination = (LinearLayout) this.findViewById(R.id.ll_destination);
        LinearLayout mLlContent = (LinearLayout) this.findViewById(R.id.ll_content);
        LinearLayout mLlCircle = (LinearLayout) this.findViewById(R.id.ll_circle);
        TextView mTvUserIcon = (TextView) this.findViewById(R.id.tv_user_icon);
        TextView mTvDestinationIcon= (TextView) this.findViewById(R.id.tv_destination_icon);
        TextView mTvContent = (TextView) this.findViewById(R.id.tv_content);
        TextView mTvContentIcon = (TextView) this.findViewById(R.id.tv_content_icon);
        TextView mTvUser = (TextView) this.findViewById(R.id.tv_user);
        TextView mTvDestination = (TextView) this.findViewById(R.id.tv_destination);
        TextView mTvCircle = (TextView) this.findViewById(R.id.tv_circle);
        TextView mTvCircleIcon = (TextView) this.findViewById(R.id.tv_circle_icon);
        textViews.add(mTvUser);
        textViews.add(mTvDestination);
        textViews.add(mTvCircle);
        textViews.add(mTvContent);

        textIcons.add(mTvUserIcon);
        textIcons.add(mTvDestinationIcon);
        textIcons.add(mTvCircleIcon);
        textIcons.add(mTvContentIcon);
        mLlUser.setOnClickListener(this);
        mLlDestination.setOnClickListener(this);
        mLlCircle.setOnClickListener(this);
        mLlContent.setOnClickListener(this);
    }
    public void setViewPager(ViewPager viewPager){
        initView();
        this.mViewPager=viewPager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                initCursor(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_user:
                initCursor(0);
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.ll_destination:
                initCursor(1);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.ll_circle:
                initCursor(2);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.ll_content:
                initCursor(3);
                mViewPager.setCurrentItem(3,false);
                break;
        }
    }

    private void initCursor(int position) {
        for (int i=0;i<textViews.size();i++){
            if (i==position){
                textViews.get(i).setTextColor(getResources().getColor(R.color.Ffbf75));
                textIcons.get(i).setTextColor(getResources().getColor(R.color.Ffbf75));
            }else {
                textViews.get(i).setTextColor(getResources().getColor(R.color.color888888));
                textIcons.get(i).setTextColor(getResources().getColor(R.color.color888888));
            }
        }
    }
}
