package com.yunspeak.travel.ui.me.messagecenter.privatemessage;



import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;

import java.util.List;


/**
 * Created by wangyang on 2016/7/15 0015.
 * 私信
 */
public class MessagePrivateActivity extends BaseToolBarActivity {


    private EMMessageListener emMessageListener;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_message_privates;
    }

    @Override
    protected void initOptions() {
        final ConversationListFragment conversationListFragment=new ConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.lv_private, conversationListFragment).commit();

        emMessageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                conversationListFragment.refresh();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
    }

    @Override
    protected String initTitle() {
        return "私信";
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
    }
}
