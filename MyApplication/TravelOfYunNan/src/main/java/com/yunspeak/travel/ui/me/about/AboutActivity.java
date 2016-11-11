package com.yunspeak.travel.ui.me.about;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BarBaseActivity;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;

/**
 * Created by wangyang on 2016/9/17.
 * 关于界面
 */
public class AboutActivity extends BaseToolBarActivity {


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_about;
    }

    @Override
    protected void initOptions() {

    }

    @Override
    protected String initTitle() {
        return "关于城外";
    }
}
