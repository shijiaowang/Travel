package com.yunspeak.travel.ui.me.myappoint.chat;

import android.content.Context;
import android.content.Intent;
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
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.myhobby.UserLabelBean;
import com.yunspeak.travel.ui.view.VoiceRecorderView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.UserInfo;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.x;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @BindView(R.id.bt_voice) Button btVoice;
    @BindView(R.id.vrv_voice)
    VoiceRecorderView vrvVoice;
    private boolean isVoice = true;
    private List<EMMessage> mMessage = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private LinearLayoutManager linearLayoutManager;
    private EMConversation conversation;
    private String tId;
    private String chatId;
    public static List<UserInfo> chatInfo;//聊天信息
    public static Map<String,UserInfo> userMap=new HashMap<>();
    private boolean needMoveToLast=false;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initOptions() {
        getUserInfo();
        initMessage();
        registerEventBus(this);
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
        etInput.addTextChangedListener(new ChatTextChangeListener());
        btSend.setOnClickListener(this);
        ivPicture.setOnClickListener(this);
        ivVoice.setOnClickListener(this);
        btVoice.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    private void initMessage() {
        tId = getIntent().getStringExtra(IVariable.TID);
        chatId = getIntent().getStringExtra(IVariable.CHAT_ID);
        if (StringUtils.isEmpty(tId) || StringUtils.isEmpty(chatId)){
            ToastUtils.showToast("聊天id为空");
        }
    }





    @Subscribe
    public void onEvent(ChatEvent chatEvent) {
       if (chatEvent.isSuccess()){
           ChatBean chatBean = GsonUtils.getObject(chatEvent.getResult(), ChatBean.class);
           chatInfo = chatBean.getData();
           if (mMessage.size()!=0) {
               chatAdapter.notifyItemRangeChanged(0, mMessage.size()-1);
           }
       }else {
           LogUtils.e("读取用户头像信息失败");
       }
    }
  public void getUserInfo(){
      Map<String, String> end = MapUtils.Build().addKey(this).addtId("33").end();
      XEventUtils.getUseCommonBackJson(IVariable.GET_CHAT_MESSAGE,end,0,new ChatEvent());
  }


    public static void start(Context context, String tId, String chatId) {
        Intent intent=new Intent(context,ChatActivity.class);
        intent.putExtra(IVariable.TID,tId);
        intent.putExtra(IVariable.CHAT_ID,chatId);
        context.startActivity(intent);
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
            case R.id.iv_voice:
                hideOrShowVoice();
                break;
            case R.id.bt_voice:
                vrvVoice.setVisibility(View.VISIBLE);
                vrvVoice.startRecording();
                break;
        }
    }

    /**
     * 发送文本消息
     */
    private void sendTextMsg() {
        etInput.setText("");
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(etInput.getText().toString(), "254699598117863848");
        notifiyInsert(message);
        message.setChatType(EMMessage.ChatType.GroupChat);
        message.setMessageStatusCallback(new MyCallBack());
        EMClient.getInstance().chatManager().sendMessage(message);

    }

    /**
     * 更新插入一条信息
     * @param message
     */
    private synchronized void notifiyInsert(EMMessage message) {
        //如果是群聊，设置chattype，默认是单聊
        if (mMessage.size()==0 || linearLayoutManager.findLastVisibleItemPosition()>=mMessage.size()-1){
            needMoveToLast=true;
        }
        mMessage.add(message);
        chatAdapter.notifyItemInserted(mMessage.size() - 1);
        moveToLast();
    }
    /**
     * 更新插入信息集合
     * @param message
     */
    private synchronized void notifiyInsertMessages(final List<EMMessage> message) {

        //如果是群聊，设置chattype，默认是单聊
        if (mMessage.size()==0 || linearLayoutManager.findLastVisibleItemPosition()>=mMessage.size()-1){
            needMoveToLast=true;
        }
        mMessage.addAll(message);
        x.task().post(new Runnable() {
            @Override
            public void run() {
                chatAdapter.notifyItemRangeInserted(mMessage.size() - message.size(),message.size());
                moveToLast();
            }
        });

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
           notifiyInsertMessages(messages);
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
    public void moveToLast(){
        if (needMoveToLast) {
            linearLayoutManager.scrollToPosition(mMessage.size() - 1);
            needMoveToLast=false;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        unregisterEventBus(this);
    }

    class MyCallBack implements EMCallBack {

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(int i, String s) {
            LogUtils.e("消息发送失败" + i + "---" + s);
        }

        @Override
        public void onProgress(int i, String s) {

        }
    }

}

