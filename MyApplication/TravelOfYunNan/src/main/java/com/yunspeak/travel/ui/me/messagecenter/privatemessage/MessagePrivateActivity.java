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


    private EMMessageListener messageListener;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_message_privates;
    }

    @Override
    protected void initOptions() {
        final ConversationListFragment conversationListFragment=new ConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.lv_private, conversationListFragment).commit();
        //收到消息刷新
        messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
              conversationListFragment.refresh();//收到消息刷新
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {

            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };


    }

    @Override
    protected String initTitle() {
        return "私信";
    }

    @Override
    protected void onStart() {
        super.onStart();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }
}
