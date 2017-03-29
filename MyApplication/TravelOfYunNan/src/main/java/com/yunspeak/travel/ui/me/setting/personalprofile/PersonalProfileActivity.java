package com.yunspeak.travel.ui.me.setting.personalprofile;
import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.PersonalBinding;
import com.yunspeak.travel.ui.baseui.BaseBarActivity;
import com.yunspeak.travel.ui.me.setting.personalprofile.model.PersonalModel;
import com.yunspeak.travel.utils.GlobalUtils;

/**
 * Created by wangyang on 2016/8/19 0019.
 * 个人简介
 */
public class PersonalProfileActivity extends BaseBarActivity<PersonalBinding> {
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_personal_profile;
    }
    @Override
    protected void initOptions() {
        String content = GlobalUtils.getUserInfo().getContent();
        dataBinding.setPersonalModel(new PersonalModel(content));
    }
    @Override
    protected String initTitle() {
        return getString(R.string.user_des);
    }
}
