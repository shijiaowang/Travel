package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.CircleAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/8 0008.
 * 圈子
 */
public class CircleActivity extends BarBaseActivity implements View.OnClickListener {


    private TextView mTvCreatePost;//创建帖子按钮
    @ViewInject(R.id.lv_post)
    private ToShowAllListView mLvPost;//帖子列表
    @ViewInject(R.id.iv_post_bg)
    private ImageView mIvPostBg;//帖子背景






    @Override
    protected void initContentView() {
       getmVsRightIcon().inflate();
        mTvCreatePost = FontsIconUtil.findIconFontsById(R.id.tv_ok,this);
        mTvCreatePost.setText(getResources().getString(R.string.activity_circle_create_post_font_icon));//设置创建帖子按钮

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_circle;
    }

    @Override
    protected void initEvent() {
        mTvCreatePost.setOnClickListener(this);
        mLvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CircleActivity.this, PostActivity.class));
            }
        });
    }
    @Override
    protected void initViewData() {
        mLvPost.setAdapter(new CircleAdapter(this, null));
    }

    @Override
    protected String setTitleName() {
        return "圈子详情";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_ok:
                startActivity(new Intent(CircleActivity.this,CreatePostActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
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
