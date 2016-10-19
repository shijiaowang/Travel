package com.example.administrator.travel.ui.me.myappoint.chat;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BaseToolBarActivity;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/10/14 0014.
 * 聊天界面
 */

public class ChatActivity extends BaseToolBarActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.iv_voice)
    ImageView ivVoice;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.iv_emoji)
    ImageView ivEmoji;
    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.tv_photo)
    TextView tvPhoto;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_picture)
    LinearLayout llPicture;
    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.bt_voice)
    Button btVoice;
    private boolean isVoice = true;
    private List<EMMessage> mMessage = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private LinearLayoutManager linearLayoutManager;
    private EMConversation conversation;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initOptions() {
        initEvent();
        hideSoftWore(etInput);
        conversation = EMClient.getInstance().chatManager().getConversation("254699598117863848");
        //获取此会话的所有消息
        EMMessage lastMessage = conversation.getLastMessage();
        if (lastMessage != null) {
            mMessage = conversation.loadMoreMsgFromDB(lastMessage.getMsgId(), 20);
        }
        chatAdapter = new ChatAdapter(mMessage, this);
        recyclerView.setAdapter(chatAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);//软键盘弹出，RecycleView内容上移
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ChatDecoration(7));
        linearLayoutManager.scrollToPosition(mMessage.size() - 1);
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    /**
     * 初始化监听事件
     */
    private void initEvent() {
        etInput.addTextChangedListener(new ChatTextChangeListener());
        btSend.setOnClickListener(this);
        ivPicture.setOnClickListener(this);
        btVoice.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    protected String initRightText() {
        return "设置";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {

    }

    @Override
    protected String initTitle() {
        return "聊天";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picture:
                openOrHidePhoto();
                break;
            case R.id.bt_send:
                sendTextMsg();
                break;
            case R.id.bt_voice:
                hideOrShowVoice();
                break;
        }
    }

    /**
     * 发送文本消息
     */
    private void sendTextMsg() {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(etInput.getText().toString(), "254699598117863848");
        //如果是群聊，设置chattype，默认是单聊
        mMessage.add(message);
        chatAdapter.notifyItemInserted(mMessage.size()-1);
        message.setChatType(EMMessage.ChatType.GroupChat);
        message.setMessageStatusCallback(new MyCallBack());
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 切换语音与键盘
     */
    private void hideOrShowVoice() {
        hideSoftWore(etInput);
        if (isVoice) {
            ivVoice.setImageResource(R.drawable.chat_soft_wore);
            btVoice.setVisibility(View.VISIBLE);
            etInput.setVisibility(View.GONE);
        } else {
            ivVoice.setImageResource(R.drawable.chat_voice);
            btVoice.setVisibility(View.GONE);
            etInput.setVisibility(View.VISIBLE);
        }
        isVoice = !isVoice;
    }

    /**
     * 关闭或者打开，发送图片地理位置等
     */
    private void openOrHidePhoto() {
        if (llPicture.getVisibility() == View.VISIBLE) {
            llPicture.setVisibility(View.GONE);
        } else {
            llPicture.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        if (mMessage == null || mMessage.size() == 0) {
            noMessageRefresh();
        } else {
            List<EMMessage> emMessages = conversation.loadMoreMsgFromDB(mMessage.get(0).getMsgId(), 20);
            int size = emMessages.size();
            if (size == 0) {
                ToastUtils.showToast("没有更多消息了");
                swipeRefreshLayout.setRefreshing(false);
            } else {
                mMessage.addAll(0, emMessages);
                if (size != 0) {
                    chatAdapter.setDatas(mMessage);
                    chatAdapter.notifyItemRangeInserted(0, size);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    /**
     * 没有消息时刷新处理
     */
    private void noMessageRefresh() {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                x.task().post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast("没有更多消息了");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }


    /**
     * 文字改变监听
     */
    class ChatTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = getString(etInput);
            if (text.length() == 0) {
                ivPicture.setVisibility(View.VISIBLE);
                btSend.setVisibility(View.GONE);
            } else {
                ivPicture.setVisibility(View.GONE);
                btSend.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            LogUtils.e("收到消息");
            mMessage.addAll(messages);
            x.task().post(new Runnable() {
                @Override
                public void run() {
                    chatAdapter.notifiyData(mMessage);
                }
            });
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            LogUtils.e("收到透传消息");
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            //收到已读回执
            LogUtils.e("收到已读回执");
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            //收到已送达回执
            LogUtils.e("收到已送达回执");
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            LogUtils.e("消息状态变动");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
    class MyCallBack implements EMCallBack{

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(int i, String s) {
            LogUtils.e("消息发送失败"+i+"---"+s);
        }

        @Override
        public void onProgress(int i, String s) {

        }
    }

}

