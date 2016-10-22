package com.example.administrator.travel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.db.DBManager;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.me.myappoint.chat.ChatBean;
import com.example.administrator.travel.ui.me.myappoint.chat.ChatEvent;
import com.example.administrator.travel.ui.me.myappoint.chat.ChatFragment;
import com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.ChatSettingActivity;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.UserInfo;
import com.hyphenate.easeui.ui.EaseBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.x;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends EaseBaseActivity {
    public static ChatActivity activityInstance;
    @BindView(R.id.iv_back) ImageView ivBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_setting) TextView tvSetting;
    private ChatFragment chatFragment;
    String toChatUsername;
    public static boolean isGetMessage = false;
    private int tryCount;
    private String tId;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_test_chat);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        ButterKnife.bind(this);
        activityInstance = this;
        //user or group id
        toChatUsername = getIntent().getStringExtra(IVariable.CHAT_ID);
        tId = getIntent().getStringExtra(IVariable.TID);
        getChatInfo();
        chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        bundle.putString(EaseConstant.EXTRA_USER_ID, toChatUsername);
        //set arguments
        chatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
         initListener();
    }

    private void initListener() {
        tvTitle.setText("群组聊天");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatSettingActivity.start(ChatActivity.this,tId);
            }
        });
    }

    /**
     * 获取聊天信息
     */
    private void getChatInfo() {
        Map<String, String> end = MapUtils.Build().addKey(this).addtId(tId).end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_CHAT_MESSAGE, end, 0, new ChatEvent());
    }

    public static void start(Context context, String tId, String chatId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(IVariable.TID, tId);
        intent.putExtra(IVariable.CHAT_ID, chatId);
        context.startActivity(intent);
    }

    @Subscribe
    public void onEvent(ChatEvent chatEvent) {
        if (chatEvent.isSuccess() && !isGetMessage) {
            try {
                isGetMessage = true;
                ChatBean chatBean = GsonUtils.getObject(chatEvent.getResult(), ChatBean.class);
                List<UserInfo> data = chatBean.getData();
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
