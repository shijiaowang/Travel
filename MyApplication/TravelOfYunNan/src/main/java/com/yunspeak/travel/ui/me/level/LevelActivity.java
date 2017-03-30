package com.yunspeak.travel.ui.me.level;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.LevelBinding;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.baseui.BaseNetBarActivity;
import com.yunspeak.travel.ui.me.level.model.Level;

/**
 * Created by wangyang on 2016/9/17.
 * 等级
 */
public class LevelActivity extends BaseNetBarActivity<Level,LevelBinding> {

    @Override
    protected void acceptData(Level level) {
        dataBinding.setLevel(level.getData());
    }
    @Override
    protected String initUrl() {
        return IRequestUrl.USER_LEVEL;
    }
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_level;
    }
    @Override
    protected String initTitle() {
        return getString(R.string.level);
    }
}
