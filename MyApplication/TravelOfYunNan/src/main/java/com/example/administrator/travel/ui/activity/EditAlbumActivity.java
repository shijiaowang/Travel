package com.example.administrator.travel.ui.activity;


import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.EditAlbumAdapter;

public class EditAlbumActivity extends BaseActivity {


    private ListView mLvPhoto;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_edit_album;
    }

    @Override
    protected void initView() {
        mLvPhoto = (ListView) findViewById(R.id.lv_photo);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
       mLvPhoto.setAdapter(new EditAlbumAdapter(this,null));
    }
}
