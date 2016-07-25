package com.example.administrator.travel.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.DeliciousFoodAdapter;
import com.example.administrator.travel.ui.adapter.SpaceItemDecoration;
import com.example.administrator.travel.ui.view.SlippingScrollView;
import com.example.administrator.travel.utils.LogUtils;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class DeliciousFoodActivity extends BarBaseActivity {
    private static final float CHANGE_COLOR_LIMIT=600f;//设置变色区间
    private RecyclerView mRvDelicious;
    private SlippingScrollView mSsvScroll;

    @Override
    protected void initContentView() {
        mRvDelicious = (RecyclerView) findViewById(R.id.rv_delicious);
        mSsvScroll = (SlippingScrollView) findViewById(R.id.ssv_scroll);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_delicious_food;
    }

    @Override
    protected void initEvent() {
           mSsvScroll.setSlippingListener(new SlippingScrollView.SlippingListener() {
               @Override
               public void slipping(int l, int i, int oldl, int t) {
                   getmBg1().setAlpha(Math.abs(t/CHANGE_COLOR_LIMIT)>1?1f:Math.abs(t/CHANGE_COLOR_LIMIT));
               }
           });
    }

    @Override
    protected void initViewData() {
        mRvDelicious.setAdapter(new DeliciousFoodAdapter(this,null));
        mRvDelicious.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRvDelicious.addItemDecoration(new SpaceItemDecoration(20));
    }

    @Override
    protected String setTitleName() {
        return "美食";
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    public float getAlpha() {
        return 0.0f;
    }
}
