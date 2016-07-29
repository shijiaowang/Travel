package com.example.administrator.travel.ui.activity;

import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class CreatePostActivity extends BarBaseActivity {

    private TextView mTvAite;
    private TextView mTvPicture;
    private TextView mTvEmoji;
    private TextView mTvCreate;

    @Override
    protected void initContentView() {
        mTvAite = FontsIconUtil.findIconFontsById(R.id.tv_aite, this);
        mTvPicture = FontsIconUtil.findIconFontsById(R.id.tv_picture, this);
        mTvEmoji = FontsIconUtil.findIconFontsById(R.id.tv_emoji, this);
        ViewStub viewStub = getmVsRightIcon();
        viewStub.setLayoutResource(R.layout.activity_create_post_right_text);
        viewStub.inflate();
        mTvCreate = (TextView)findViewById(R.id.tv_ok);
        mTvCreate.setText("发表");
    }

    @Override
    protected int setContentLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_create_post;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "发布帖子";
    }


    @Override
    protected boolean haveRightIcon() {
        return true;
    }

    @Override
    protected boolean leftIsFontIcon() {
        return false;
    }

    @Override
    protected String getLeftText() {
        return "取消";
    }

    @Override
    protected float getLeftTextSize() {
        return 13f;
    }

    @Override
    public float getAlpha() {
        return 1f;
    }
}
