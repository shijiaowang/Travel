package com.example.administrator.travel.ui.me.myalbum;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseLoadAndRefreshActivity;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.me.myalbum.createalbum.CreateAlbumActivity;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.GsonUtils;


import java.util.List;

/**
 * Created by wangyang on 2016/7/18 0018.
 * 我的相册
 */
public class MyAlbumActivity extends BaseLoadAndRefreshActivity<MyAlbumEvent,MyAlbumBean,MyAlbumBean.DataBean> implements View.OnClickListener {
    private FontsIconTextView mTvAdd;
    private TextView mTvCreateAlbum;
    private TextView mTvAlbumSum;
    private boolean initAlbumSize=false;

    @Override
    protected void initEvent() {
        super.initEvent();
        mVsContent.setLayoutResource(R.layout.activity_my_album_header);
        mVsContent.inflate();
        mTvAdd = (FontsIconTextView)findViewById(R.id.tv_add);
        mTvCreateAlbum = (TextView)findViewById(R.id.tv_create_album);
        mTvAlbumSum = (TextView)findViewById(R.id.tv_sum);
        mTvAdd.setOnClickListener(this);
        mTvCreateAlbum.setOnClickListener(this);
    }

    @Override
    protected void onSuccess(MyAlbumEvent event) {
        super.onSuccess(event);
        if (!initAlbumSize){
            initAlbumSize=true;
            MyAlbumBean myAlbumBean = GsonUtils.getObject(event.getResult(), MyAlbumBean.class);
            int count = myAlbumBean.getData().get(0).getCount();
            mTvAlbumSum.setText(getString(R.string.total_album,count));
        }

    }

    @Override
    protected Activity initDataAndRegisterEventBus() {
        return this;
    }
    @Override
    protected String initTitle() {
        return "我的相册";
    }
    @Override
    protected String initUrl() {
        return IVariable.ALBUM_LIST;
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
    @Override
    protected boolean isGridLayoutManager() {
        return true;
    }
    @Override
    protected BaseRecycleViewAdapter<MyAlbumBean.DataBean> initAdapter(List<MyAlbumBean.DataBean> mDatas) {
        return new MyAlbumAdapter(mDatas,this);
    }
}
