package com.yunspeak.travel.ui.me.identityauth.idauth;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yalantis.ucrop.UCrop;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.home.UserInfo;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseCutPhotoActivity;
import com.yunspeak.travel.ui.me.identityauth.AuthCommonEvent;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GlobalUtils;
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
 * Created by wangyang on 2016/12/5 0005.
 * 绑定驾驶证
 */

public class DriverAuthActivity extends BaseCutPhotoActivity<AuthCommonEvent> implements View.OnClickListener {
    @BindView(R.id.tv_icon1) FontsIconTextView tvIcon1;
    @BindView(R.id.iv_image1) SimpleDraweeView ivImage1;
    @BindView(R.id.bt_next) AvoidFastButton btNext;
    private String url;


    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void showImage(String data) {
        this.url=data;
        tvIcon1.setVisibility(View.GONE);
        ivImage1.setVisibility(View.VISIBLE);
        FrescoUtils.displayNormal(ivImage1,url);
    }
    @Override
    protected void userResultSize(UCrop uCrop, int width, int height) {
        super.userResultSize(uCrop, 2000, 1000);
    }
    @Override
    protected void initEvent() {
        setNeedCrop(false);
        tvIcon1.setOnClickListener(this);
        ivImage1.setOnClickListener(this);
        btNext.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(url)){
                    ToastUtils.showToast("请上传图片");
                    return;
                }
                setIsProgress(true);
                Map<String, String> upMap = MapUtils.Build().addKey().addUserId().addType("2").addClass("1").end();
                List<String> urls=new ArrayList<>();
                urls.add(url);
                XEventUtils.postFileCommonBackJson(IVariable.IDENTITY_AUTH,upMap,urls,TYPE_UP_FILE,new AuthCommonEvent());
            }
        });

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
       ToastUtils.showToast("上传成功");
        try {
            UserInfo userInfo = GlobalUtils.getUserInfo();
            userInfo.setRun_card("1");
            UserUtils.saveUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onFail(AuthCommonEvent authCommonEvent) {

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_driver_card;
    }

    @Override
    protected String initTitle() {
        return "驾驶证认证";
    }

    @Override
    public void onClick(View v) {
        showPictureCutPop(btNext);
    }
}
