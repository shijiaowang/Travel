package com.yunspeak.travel.ui.me.about;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BarBaseActivity;

/**
 * Created by wangyang on 2016/9/17.
 * 关于界面
 */
public class AboutActivity extends BarBaseActivity {
    @Override
    protected int setContentLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "关于云说";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
