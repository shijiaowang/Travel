package com.yunspeak.travel.ui.me.identityauth.idauth;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseCropPictureActivity;
import com.yunspeak.travel.ui.me.identityauth.AuthCommonEvent;
import com.yunspeak.travel.utils.BitmapUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/2.
 * 身份证认证
 */

public class IdAuthActivity extends BaseCropPictureActivity<AuthCommonEvent> implements View.OnClickListener {
    @BindView(R.id.iv_image1) SimpleDraweeView mIvImage1;
    @BindView(R.id.iv_image2) SimpleDraweeView mIvImage2;
    @BindView(R.id.tv_icon1) TextView mTvIcon1;
    @BindView(R.id.tv_icon2) TextView mTvIcon2;
    @BindView(R.id.bt_next)
    Button mBtSubmit;
    private int upSide=-1;//上传哪一面
    private static final int POSITIVE_SIDE=1;//正
    private static final int OTHER_SIDE=2;//反
    private String posUrl;
    private String otherUrl;


    @Override
    protected SimpleDraweeView childViewShow() {
        return upSide==POSITIVE_SIDE?mIvImage1:mIvImage2;
    }




    @Override
    protected void initEvent() {
        setNeedCrop(false);//不需要裁剪
        mTvIcon1.setOnClickListener(this);
        mIvImage1.setOnClickListener(this);
        mTvIcon2.setOnClickListener(this);
        mIvImage2.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);
    }

    @Override
    protected void onLoad(int type) {
        setIsProgress(false);
        setSuccess();
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }
    @Override
    protected String initUrl() {
        return null;
    }

    @Override
    protected void onSuccess(AuthCommonEvent authCommonEvent) {
        ToastUtils.showToast(authCommonEvent.getMessage());
        UserInfo userInfo = UserUtils.getUserInfo();
        userInfo.setId_card("1");
        UserUtils.saveUserInfo(userInfo);

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
        Map<String, String> idMap = MapUtils.Build().addKey().addUserId().addType("1").addClass("3").end();
        List<String> files=new ArrayList<>(2);
        files.add(posUrl);
        files.add(otherUrl);
        XEventUtils.postFileCommonBackJson(IVariable.IDENTITY_AUTH,idMap,files,0,new AuthCommonEvent());
    }

    @Override
    protected void showImage(Intent data) {
        Uri uri = data.getData();
        try {
            if (upSide==POSITIVE_SIDE){
                mIvImage1.setVisibility(View.VISIBLE);
                mTvIcon1.setVisibility(View.GONE);
                posUrl = BitmapUtils.getImageAbsolutePath(this, uri);
                FrescoUtils.displayNormal(mIvImage1,"file://"+posUrl);
            }else {
                mIvImage2.setVisibility(View.VISIBLE);
                mTvIcon2.setVisibility(View.GONE);
                otherUrl = BitmapUtils.getImageAbsolutePath(this, uri);
                FrescoUtils.displayNormal(mIvImage2,"file://"+otherUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected int initLayoutRes() {
        return R.layout.activity_id_auth;
    }

    @Override
    protected String initTitle() {
        return "身份认证";
    }

    @Override
    protected void onFail(AuthCommonEvent authCommonEvent) {
        
    }
}
