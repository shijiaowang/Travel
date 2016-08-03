package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.service.carrier.CarrierService;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.HotSpotsAdapter;
import com.example.administrator.travel.ui.adapter.MyAlbumAdapter;
import com.example.administrator.travel.ui.adapter.SpaceItemDecoration;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.LogUtils;

/**
 * Created by Administrator on 2016/7/18 0018.
 *我的相册
 */
public class MyAlbumActivity extends BarBaseActivity implements View.OnClickListener {

    private TextView mTvAdd;
    private RecyclerView mRvAlbum;



    @Override
    protected void initContentView() {
        mTvAdd = FontsIconUtil.findIconFontsById(R.id.tv_add, this);//添加
        mRvAlbum = (RecyclerView) findViewById(R.id.rv_album);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_album;
    }


    @Override
    protected void initEvent() {

        mTvAdd.setOnClickListener(this);
    }


    @Override
    protected void initViewData() {
        mRvAlbum.setAdapter(new MyAlbumAdapter(this, null));
        mRvAlbum.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRvAlbum.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected String setTitleName() {
        return "我的相册";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add:
                startActivity(new Intent(MyAlbumActivity.this, CreateAlbumActivity.class));
                break;
        }

    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
