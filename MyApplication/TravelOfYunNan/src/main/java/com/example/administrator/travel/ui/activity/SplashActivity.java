package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.administrator.travel.R;

public class SplashActivity extends BaseActivity {


    private VideoView mVideoView;
    private int currentPosition;

    @Override
    protected int initLayoutRes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP) {
            // Translucent status bar
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//状态栏完全透明
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mVideoView = (VideoView) findViewById(R.id.vv_video);
    }

    @Override
    protected void initListener() {
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.start();
            }
        });
    }

    @Override
    protected void initData() {
        Uri parse = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.entrance_video);
        mVideoView.setVideoURI(parse);

    }

    private void gotoHome() {
        Intent homeIntent=new Intent(this,HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mVideoView.seekTo(currentPosition);

    }

    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = mVideoView.getCurrentPosition();
    }
}
