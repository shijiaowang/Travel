package com.yunspeak.travel.ui.me.identityauth.idauth;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseCutPhotoActivity;
import com.yunspeak.travel.ui.me.identityauth.AuthCommonEvent;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/2.
 * 身份证认证
 */

public class IdAuthActivity extends BaseCutPhotoActivity<AuthCommonEvent> implements View.OnClickListener {
    @BindView(R.id.iv_image1)
    SimpleDraweeView mIvImage1;
    @BindView(R.id.iv_image2)
    SimpleDraweeView mIvImage2;
    @BindView(R.id.tv_icon1)
    TextView mTvIcon1;
    @BindView(R.id.tv_icon2)
    TextView mTvIcon2;
    @BindView(R.id.bt_next)
    Button mBtSubmit;
    @BindView(R.id.iv_example1) ImageView ivExample1;
    @BindView(R.id.tv_example1) TextView tvExample1;
    @BindView(R.id.iv_example2) ImageView ivExample2;
    @BindView(R.id.tv_example2) TextView tvExample2;
    @BindView(R.id.tv_up1) TextView tvUp1;
    @BindView(R.id.tv_up2) TextView tvUp2;
    private int upSide = -1;//上传哪一面
    private static final int POSITIVE_SIDE = 1;//正
    private static final int OTHER_SIDE = 2;//反
    private String posUrl;
    private String otherUrl;
    private boolean isIdCard;


    public static void start(Context context, boolean isId) {
        Intent intent = new Intent(context, IdAuthActivity.class);
        intent.putExtra(IVariable.DATA, isId);
        context.startActivity(intent);
    }

    @Override
    protected void initEvent() {
        setNeedCrop(false);//不需要裁剪
        isIdCard = getIntent().getBooleanExtra(IVariable.DATA, false);
        mTvIcon1.setOnClickListener(this);
        mIvImage1.setOnClickListener(this);
        mTvIcon2.setOnClickListener(this);
        mIvImage2.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);
        if (!isIdCard){
            tvExample1.setText("车辆行驶证");
            tvExample2.setText("车辆照片");
            tvUp1.setText("车辆行驶证");
            tvUp2.setText("车辆照片");
            ivExample1.setImageResource(R.drawable.car_card);
            ivExample2.setImageResource(R.drawable.car_photo);
        }
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
        try {
            UserInfo userInfo = UserUtils.getUserInfo();
            if (isIdCard) {
                userInfo.setId_card("1");
            }else {
                userInfo.setRun_card("1");
            }
            UserUtils.saveUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_image1:
            case R.id.tv_icon1:
                upSide = POSITIVE_SIDE;
                showPictureCutPop(mBtSubmit);
                break;
            case R.id.iv_image2:
            case R.id.tv_icon2:
                upSide = OTHER_SIDE;
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
        if (StringUtils.isEmpty(posUrl) || StringUtils.isEmpty(otherUrl)) {
            ToastUtils.showToast("请上传身份证正反面");
            return;
        }
        setIsProgress(true);
        String  type=isIdCard?"1":"3";
        Map<String, String> idMap = MapUtils.Build().addKey().addUserId().addType(type).addClass("3").end();
        List<String> files = new ArrayList<>(2);
        files.add(posUrl);
        files.add(otherUrl);
        XEventUtils.postFileCommonBackJson(IVariable.IDENTITY_AUTH, idMap, files, 0, new AuthCommonEvent());
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

    @Override
    protected void showImage(String data) {
        try {
            if (upSide == POSITIVE_SIDE) {
                mIvImage1.setVisibility(View.VISIBLE);
                mTvIcon1.setVisibility(View.GONE);
                posUrl = data;
                FrescoUtils.displayNormal(mIvImage1, data);
            } else {
                otherUrl = data;
                mIvImage2.setVisibility(View.VISIBLE);
                mTvIcon2.setVisibility(View.GONE);
                FrescoUtils.displayNormal(mIvImage2, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
