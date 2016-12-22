package com.yunspeak.travel.ui.me.messagecenter.privatemessage;



import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;

import java.util.List;


/**
 * Created by wangyang on 2016/7/15 0015.
 * 私信
 */
public class MessagePrivateActivity extends BaseToolBarActivity {




    @Override
    protected int initLayoutRes() {
        return R.layout.activity_message_privates;
    }

    @Override
    protected void initOptions() {
        final ConversationListFragment conversationListFragment=new ConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.lv_private, conversationListFragment).commit();

    }

    @Override
    protected String initTitle() {
        return "私信";
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
