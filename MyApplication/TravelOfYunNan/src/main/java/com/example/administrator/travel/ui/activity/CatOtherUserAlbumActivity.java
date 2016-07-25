package com.example.administrator.travel.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.CatOtherAlbumAdapter;
import com.example.administrator.travel.utils.FontsIconUtil;

public class CatOtherUserAlbumActivity extends BaseActivity {


    private ListView mLvAlbum;//相册列表
    private TextView mTvBack;//返回

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_cat_other_user_album;
    }

    @Override
    protected void initView() {
        mLvAlbum = (ListView) findViewById(R.id.lv_album);
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
    }

    @Override
    protected void initListener() {
       mTvBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }

    @Override
    protected void initData() {
      mLvAlbum.setAdapter(new CatOtherAlbumAdapter(this,null));
    }
}
