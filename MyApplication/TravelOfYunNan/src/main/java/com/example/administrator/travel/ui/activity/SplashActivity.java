package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.LogUtils;

public class SplashActivity extends FullTransparencyActivity {
    private static final String CURRENT_POSITION = "current_position";
    public static final int GO_LOGIN = 0;

    private VideoView mVideoView;
    private int currentPosition;
    private Button mBtLogin;

    @Override
    protected int initContentRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mVideoView = (VideoView) findViewById(R.id.vv_video);
        mBtLogin = (Button) findViewById(R.id.bt_login);
        initVideoView();
    }

    private void initVideoView() {
        mVideoView = (VideoView) findViewById(R.id.vv_video);
        Uri parse = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.entrance_video);
        mVideoView.setVideoURI(parse);
    }

    @Override
    protected void initListener() {
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivityForResult(intent,GO_LOGIN);

            }
        });
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


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mVideoView == null) {
            initVideoView();
        }
        mVideoView.seekTo(currentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPosition = savedInstanceState.getInt(CURRENT_POSITION, 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_POSITION, currentPosition);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_LOGIN && resultCode == LoginActivity.SPLASH_RESULT) {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();
        currentPosition = mVideoView.getCurrentPosition();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.pause();
            mVideoView = null;
        }
    }
}
