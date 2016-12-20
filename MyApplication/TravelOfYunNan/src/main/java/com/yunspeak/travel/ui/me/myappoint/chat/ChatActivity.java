package com.yunspeak.travel.ui.me.myappoint.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.UserInfo;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.ChatBean;
import com.yunspeak.travel.db.DBManager;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.me.myappoint.chat.chatsetting.ChatSettingActivity;
import com.yunspeak.travel.ui.me.myappoint.chat.chatsetting.privatesetting.PrivateChatSettingActivity;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.x;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends EaseBaseActivity {
    public static ChatActivity activityInstance;

    @BindView(R.id.tv_appbar_title) TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    private ChatFragment chatFragment;
    String toChatUsername;
    public  boolean isGetMessage = false;
    private int tryCount;
    private int chatType;
    private String title;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_chat);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        ButterKnife.bind(this);
        activityInstance = this;
        //user or group id
        toChatUsername = getIntent().getStringExtra(IVariable.CHAT_ID);
        chatType = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        getChatInfo();
        if (chatType==EaseConstant.CHATTYPE_GROUP){
            title="群组聊天";
        }else {
            title="单聊";

        }
        chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE,chatType);
        bundle.putString(EaseConstant.EXTRA_USER_ID, toChatUsername);
        //set arguments
        chatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
         initListener();
    }

    private void initListener() {

        tvTitle.setText(title);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_appoint_menu,menu);
        menu.findItem(R.id.action_history).setTitle("设置");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                switch (chatType){
                    case  EaseConstant.CHATTYPE_SINGLE:
                        Intent intent1 = new Intent(ChatActivity.this,PrivateChatSettingActivity.class);
                        intent1.putExtra(IVariable.DATA,toChatUsername);
                        startActivity(intent1);
                        break;
                    case EaseConstant.CHATTYPE_GROUP:
                        Intent intent = new Intent(ChatActivity.this, ChatSettingActivity.class);
                        intent.putExtra(IVariable.DATA,toChatUsername);
                        startActivity(intent);
                        break;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 获取聊天信息
     */
    private void getChatInfo() {
        Map<String, String> end = MapUtils.Build().addKey().addtId(toChatUsername).addType(chatType==EaseConstant.CHATTYPE_SINGLE?"2":"1").end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_CHAT_MESSAGE, end, 0, new ChatEvent());
    }

    public static void start(Context context,String chatId,int chatType) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(IVariable.CHAT_ID, chatId);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        context.startActivity(intent);
    }

    @Subscribe
    public void onEvent(ChatEvent chatEvent) {
        if (chatEvent.isSuccess() && !isGetMessage) {
            try {
                isGetMessage = true;
                ChatBean chatBean = GsonUtils.getObject(chatEvent.getResult(), ChatBean.class);
                List<UserInfo> data = chatBean.getData();
                if (chatType==EaseConstant.CHATTYPE_SINGLE){
                    tvTitle.setText(data.get(0).getNick_name());
                }
                DBManager.insertChatUserInfo(data);//初次存入用户信息
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e("获取用户信息出错了");
            }
        } else {

            if (!isGetMessage && tryCount < 4) {
                tryCount++;
                x.task().run(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            getChatInfo();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

}
