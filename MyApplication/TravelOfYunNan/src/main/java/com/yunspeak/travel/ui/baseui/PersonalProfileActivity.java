package com.yunspeak.travel.ui.baseui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.event.PersonalProfileEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/19 0019.
 * 个人简介
 */
public class PersonalProfileActivity extends BaseNetWorkActivity<PersonalProfileEvent> {
    @BindView(R.id.et_profile) EditText mEtProfile;
    @BindView(R.id.bt_save_change) AvoidFastButton mBtSaveChange;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.ll_hint) LinearLayout mLlHint;


    @Override
    protected void initEvent() {
        String content = GlobalUtils.getUserInfo().getContent();
        if (!StringUtils.isEmpty(content)){
            mLlHint.setVisibility(View.GONE);
            mEtProfile.setText(content);
            mTvNumber.setText(content.length()+"/80");
        }
        mBtSaveChange.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                setIsProgress(true);
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
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
    }

    @Override
    protected void onSuccess(PersonalProfileEvent personalProfileEvent) {
        UserInfo userInfo = GlobalUtils.getUserInfo();
        userInfo.setContent(getString(mEtProfile));
        UserUtils.saveUserInfo(userInfo);
        finish();
    }

    @Override
    protected void onFail(PersonalProfileEvent personalProfileEvent) {
        setIsProgress(false);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_personal_profile;
    }

    @Override
    protected String initTitle() {
        return "个人简介";
    }
}
