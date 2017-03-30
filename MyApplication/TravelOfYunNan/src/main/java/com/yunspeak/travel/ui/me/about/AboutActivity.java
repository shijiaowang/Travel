package com.yunspeak.travel.ui.me.about;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.AboutBinding;
import com.yunspeak.travel.ui.baseui.BaseBarActivity;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.home.welcome.splash.register.WebViewActivity;
import com.yunspeak.travel.ui.me.about.model.AboutModel;
import com.yunspeak.travel.utils.UIUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/17.
 * 关于界面
 */
public class AboutActivity extends BaseBarActivity<AboutBinding> {

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_about;
    }

    @Override
    protected void initOptions() {
        dataBinding.setAboutModel(new AboutModel());
    }

    @Override
    protected String initTitle() {
        return "关于城外";
    }
}
