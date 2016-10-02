package com.example.administrator.travel.ui.me.identityauth.idauth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseCropPhotoActivity;
import com.example.administrator.travel.ui.me.identityauth.AuthCommonEvent;
import com.example.administrator.travel.utils.BitmapUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/10/2.
 * 身份证认证
 */

public class IdAuthActivity extends BaseCropPhotoActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_image1) ImageView mIvImage1;
    @ViewInject(R.id.iv_image2) ImageView mIvImage2;
    @ViewInject(R.id.tv_icon1)
    TextView mTvIcon1;
    @ViewInject(R.id.tv_icon2) TextView mTvIcon2;
    @ViewInject(R.id.bt_next)
    Button mBtSubmit;
    private int upSide=-1;//上传哪一面
    private static final int POSITIVE_SIDE=1;//正
    private static final int OTHER_SIDE=1;//反
    private String posUrl;
    private String otherUrl;

    @Override
    protected void initChildListener() {
        setNeedCrop(false);//不需要裁剪
         mTvIcon1.setOnClickListener(this);
        mIvImage1.setOnClickListener(this);
        mTvIcon2.setOnClickListener(this);
        mIvImage2.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);

    }

    @Override
    protected ImageView childViewShow() {
        return upSide==POSITIVE_SIDE?mIvImage1:mIvImage2;
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_id_auth;
    }

    @Override
    protected void onLoad(int type) {
        setIsProgress(false);
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "身份证认证";
    }

    @Override
    protected void onSuccess(HttpEvent httpEvent) {
        ToastUtils.showToast(httpEvent.getMessage());
    }

    @Override
    protected void onFail(HttpEvent event) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_image1:
            case R.id.tv_icon1:
                upSide=POSITIVE_SIDE;
                showPictureCutPop(mBtSubmit);
                break;
            case R.id.iv_image2:
            case R.id.tv_icon2:
                upSide=OTHER_SIDE;
                showPictureCutPop(mBtSubmit);
                break;
            case R.id.bt_next:
                submitIdPhoto();
                break;

        }
    }

    /**
     * 上传图片
     */
    private void submitIdPhoto() {
        if (StringUtils.isEmpty(posUrl) || StringUtils.isEmpty(otherUrl)){
            ToastUtils.showToast("请上传身份证正反面");
            return;
        }
        Map<String, String> idMap = MapUtils.Build().addKey(this).addUserId().addType("1").addClass("3").end();
        List<String> files=new ArrayList<>(2);
        files.add(posUrl);
        files.add(otherUrl);
        XEventUtils.postFileCommonBackJson(IVariable.IDENTITY_AUTH,idMap,files,0,new AuthCommonEvent());
    }

    @Override
    protected void showImage(Intent data) {
        Uri uri = data.getData();
        try {
            Bitmap bitmapFormUri = BitmapUtils.getBitmapFormUri(this, uri, 100);
            if (upSide==POSITIVE_SIDE){
                mIvImage1.setVisibility(View.VISIBLE);
                mTvIcon1.setVisibility(View.GONE);
                mIvImage1.setImageBitmap(bitmapFormUri);
                posUrl = BitmapUtils.getImageAbsolutePath(this, uri);
            }else {
                mIvImage2.setVisibility(View.VISIBLE);
                mTvIcon2.setVisibility(View.GONE);
                mIvImage2.setImageBitmap(bitmapFormUri);
                otherUrl = BitmapUtils.getImageAbsolutePath(this, uri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
