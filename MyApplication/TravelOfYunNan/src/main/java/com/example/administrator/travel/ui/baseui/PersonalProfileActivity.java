package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.UserInfo;
import com.example.administrator.travel.event.PersonalProfileEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.view.AvoidFastButton;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.UserUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/19 0019.
 * 个人简介
 */
public class PersonalProfileActivity extends LoadingBarBaseActivity<PersonalProfileEvent> {
    @ViewInject(R.id.et_profile)
    private EditText mEtProfile;
    @ViewInject(R.id.bt_save_change)
    private AvoidFastButton mBtSaveChange;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    @ViewInject(R.id.ll_hint)
    private LinearLayout mLlHint;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_personal_profile;
    }

    @Override
    protected void initEvent() {
        mBtSaveChange.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> contentMap = MapUtils.Build().addKey(PersonalProfileActivity.this).add(IVariable.USER_ID, GlobalUtils.getUserInfo().getId()).add(IVariable.CONTENT, getString(mEtProfile)).end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_USER_INFO, contentMap, 0,new PersonalProfileEvent());
            }
        });
   mEtProfile.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
          if (mLlHint.getVisibility()== View.VISIBLE)mLlHint.setVisibility(View.GONE);
           mTvNumber.setText(getString(mEtProfile).length()+"/80");
       }

       @Override
       public void afterTextChanged(Editable s) {

       }
   });
    }

    @Override
    protected void onLoad(int typeRefresh) {

    }

    @Override
    protected Activity initViewData() {
        String content = GlobalUtils.getUserInfo().getContent();
        if (!StringUtils.isEmpty(content)){
            mLlHint.setVisibility(View.GONE);
            mEtProfile.setText(content);
            mTvNumber.setText(content.length()+"/80");
        }
       return this;
    }

    @Override
    protected String setTitleName() {
        return "个人简介";
    }
    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected void onSuccess(PersonalProfileEvent personalProfileEvent) {
        UserInfo userInfo = GlobalUtils.getUserInfo();
        userInfo.setContent(getString(mEtProfile));
        UserUtils.saveUserInfo(userInfo);
        finish();
    }


}
