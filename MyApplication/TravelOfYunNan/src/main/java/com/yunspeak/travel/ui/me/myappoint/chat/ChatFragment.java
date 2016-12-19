package com.yunspeak.travel.ui.me.myappoint.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.utils.GlobalUtils;

/**
 * Created by wangyang on 2016/10/21 0021.
 */

public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        super.setUpView();
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }


    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {
            OtherUserCenterActivity.start(getContext(), username);
    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void resendMessage(final EMMessage message) {
        if (!EMClient.getInstance().isConnected()){//未登陆进行登陆
            EMClient.getInstance().login(GlobalUtils.getUserInfo().getId(), GlobalUtils.getUserInfo().getPwd(), new EMCallBack() {
                @Override
                public void onSuccess() {
                  resendMessage(message);
                }
                @Override
                public void onError(int i, String s) {
                }
                @Override
                public void onProgress(int i, String s) {
                }
            });
        }else {
            super.resendMessage(message);
        }

    }


}
