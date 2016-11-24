package com.yunspeak.travel.ui.me.about;

import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.utils.UIUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/17.
 * 关于界面
 */
public class AboutActivity extends BaseToolBarActivity {
   @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_about;
    }

    @Override
    protected void initOptions() {
       mTvVersion.setText("v"+ UIUtils.getVersion(this));
    }

    @Override
    protected String initTitle() {
        return "关于城外";
    }
}
