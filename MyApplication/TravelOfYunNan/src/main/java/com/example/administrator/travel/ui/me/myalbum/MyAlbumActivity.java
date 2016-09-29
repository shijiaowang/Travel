package com.example.administrator.travel.ui.me.myalbum;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.createalbum.CreateAlbumActivity;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/18 0018.
 * 我的相册
 */
public class MyAlbumActivity extends LoadingBarBaseActivity<MyAlbumEvent> implements View.OnClickListener {
    @ViewInject(R.id.tv_add)
    private FontsIconTextView mTvAdd;
    @ViewInject(R.id.xs_scroll)
    private XScrollView mXScroll;
    @ViewInject(R.id.tv_create_album)
    private TextView mTvCreateAlbum;
    @ViewInject(R.id.tv_sum)
    private TextView mTvAlbumSum;
    private List<MyAlbumBean.DataBean> myAlbumBeanData;
    private MyAlbumAdapter myAlbumAdapter;
    private RecyclerView mRvAlbum;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_album;
    }


    @Override
    protected void initEvent() {
        mRvAlbum = (RecyclerView) LayoutInflater.from(this).inflate(R.layout.activity_my_album_content, null);
        if (mRvAlbum != null) {
            mXScroll.setPullLoadEnable(true);
            mXScroll.setPullRefreshEnable(false);
            mXScroll.setIXScrollViewListener(this);
            mXScroll.setRefreshTime(getTime());
            mXScroll.setView(mRvAlbum);
        }
        mTvCreateAlbum.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
    }

    @Override
    protected void onLoad(int type) {
        int count = type == TYPE_REFRESH ? 0 : getListSize(myAlbumBeanData);
        Map<String, String> albumMap = MapUtils.Build().addKey(this).addUserId().addPageSize().addCount(count).end();
        XEventUtils.getUseCommonBackJson(IVariable.ALBUM_LIST, albumMap, type, new MyAlbumEvent());
    }


    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "我的相册";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_create_album:
            case R.id.tv_add:
                startActivity(new Intent(MyAlbumActivity.this, CreateAlbumActivity.class));
                break;
        }

    }


    private void dealData(MyAlbumEvent event) {
        MyAlbumBean myAlbumBean = GsonUtils.getObject(event.getResult(), MyAlbumBean.class);
        if (myAlbumAdapter == null) {
            myAlbumBeanData = myAlbumBean.getData();
            myAlbumAdapter = new MyAlbumAdapter(this, myAlbumBeanData);
            mRvAlbum.setAdapter(myAlbumAdapter);
            mRvAlbum.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            mRvAlbum.setItemAnimator(new DefaultItemAnimator());
            mTvAlbumSum.setText(getString(R.string.total_album, myAlbumBeanData.size()));
        } else {
            myAlbumBeanData.addAll(myAlbumBean.getData());
            myAlbumAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected void onSuccess(MyAlbumEvent event) {
        dealData(event);
    }
}
