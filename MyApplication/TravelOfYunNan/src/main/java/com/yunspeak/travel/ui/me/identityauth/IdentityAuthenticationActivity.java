package com.yunspeak.travel.ui.me.identityauth;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.IdentityAuthBinding;
import com.yunspeak.travel.ui.baseui.BaseBarActivity;
import com.yunspeak.travel.ui.me.identityauth.model.IdentityAuthModel;


/**
 * Created by wangyang on 2016/8/19 0019.
 * 身份认证
 */
public class IdentityAuthenticationActivity extends BaseBarActivity<IdentityAuthBinding> {


    private IdentityAuthModel identityAuthModel;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_identity_authentication;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (identityAuthModel!=null){
            identityAuthModel.refresh();
        }
    }

    @Override
    protected void initOptions() {
        identityAuthModel = new IdentityAuthModel();
        dataBinding.setIdentityAuth(identityAuthModel);
    }


    @Override
    protected String initTitle() {
        return getString(R.string.indetity_auth);
    }




}
