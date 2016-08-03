package com.example.administrator.travel.ui.activity;


import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * 创建相册
 */

public class CreateAlbumActivity extends BarBaseActivity implements View.OnClickListener {

    private TextView mTvMore;//更多

    @Override
    protected void initContentView() {
        getmVsRightIcon().inflate();
        mTvMore = FontsIconUtil.findIconFontsById(R.id.tv_ok, this);
        mTvMore.setText(getResources().getString(R.string.activity_message_center_more));
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_create_album;
    }



    @Override
    protected void initEvent() {
        mTvMore.setOnClickListener(this);
    }


    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "创建相册";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_more:
                break;
        }
    }

    @Override
    protected boolean haveRightIcon() {
        return true;
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
