package com.example.administrator.travel.ui.me.createalbum;


import android.app.Activity;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.utils.TypefaceUtis;

/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class CreateAlbumActivity extends LoadingBarBaseActivity {
    private TextView mTvMore;//更多
    @Override
    protected int setContentLayout() {
        return R.layout.activity_create_album;
    }



    @Override
    protected void initEvent() {
        init();
    }

    @Override
    protected void onLoad(int type) {
        setIsProgress(false);
    }

    private void init() {
        mTvMore = getmTvRightIcon();
        mTvMore.setTypeface(TypefaceUtis.getTypeface(this));
        mTvMore.setText(getResources().getString(R.string.activity_message_center_more));
    }


    @Override
    protected Activity initViewData() {
          return this;
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
