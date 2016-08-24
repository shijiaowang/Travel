package com.example.administrator.travel.ui.activity;


import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.TypefaceUtis;

/**
 * 创建相册
 */

public class CreateAlbumActivity extends BarBaseActivity {

    private TextView mTvMore;//更多



    @Override
    protected int setContentLayout() {
        return R.layout.activity_create_album;
    }



    @Override
    protected void initEvent() {
        init();
    }

    private void init() {
        mTvMore = getmTvRightIcon();
        mTvMore.setTypeface(TypefaceUtis.getTypeface(this));
        mTvMore.setText(getResources().getString(R.string.activity_message_center_more));
    }


    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "创建相册";
    }




    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }


}
