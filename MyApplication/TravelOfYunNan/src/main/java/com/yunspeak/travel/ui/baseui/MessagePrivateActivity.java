package com.yunspeak.travel.ui.baseui;

import android.widget.ListView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.MessagePrivateAdapter;

import org.xutils.view.annotation.ViewInject;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/15 0015.
 * 私信
 */
public class MessagePrivateActivity extends BaseToolBarActivity {
    @BindView(R.id.lv_private)
     ListView mLvPrivate;




    @Override
    protected int initLayoutRes() {
        return R.layout.activity_message_privates;
    }

    @Override
    protected void initOptions() {
        mLvPrivate.setAdapter(new MessagePrivateAdapter(this,null));
    }

    @Override
    protected String initTitle() {
        return "私信";
    }
}
