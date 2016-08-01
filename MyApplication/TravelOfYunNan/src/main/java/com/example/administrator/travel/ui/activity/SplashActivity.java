package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.LogUtils;

public class SplashActivity extends FullTransparencyActivity {
   public static final int GO_LOGIN=0;

    private VideoView mVideoView;
    private int currentPosition;
    private Button mBtLogin;

    @Override
    protected int initContentRes() {
        return  R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mVideoView = (VideoView) findViewById(R.id.vv_video);
        mBtLogin = (Button) findViewById(R.id.bt_login);
    }

    @Override
    protected void initListener() {
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));

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
        Uri parse = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.entrance_video);
        mVideoView.setVideoURI(parse);

    }

    private void gotoHome() {
        Intent homeIntent=new Intent(this,HomeActivity.class);
       startActivityForResult(homeIntent, GO_LOGIN);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mVideoView.seekTo(currentPosition);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_LOGIN && resultCode == LoginActivity.SPLASH_RESULT) {
            if (mVideoView != null) {
                mVideoView.pause();
            }
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
        if (mVideoView!=null) {
            mVideoView.pause();
            mVideoView=null;
        }
    }
}
