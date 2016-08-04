package com.example.administrator.travel.ui.activity;


import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.EditAlbumAdapter;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * 编辑相册
 */

public class EditAlbumActivity extends BarBaseActivity {

    @ViewInject(R.id.lv_photo)
    private ListView mLvPhoto;
    private TextView mTvMore;


    @Override
    protected void initContentView() {
        getmVsRightIcon().inflate();
        mTvMore = FontsIconUtil.findIconFontsById(R.id.tv_ok, this);
        mTvMore.setText(getResources().getString(R.string.activity_message_center_more));
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_edit_album;
    }


    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {
        mLvPhoto.setAdapter(new EditAlbumAdapter(this,null));
    }

    @Override
    protected String setTitleName() {
        return "";
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected boolean haveRightIcon() {
        return true;
    }
}
