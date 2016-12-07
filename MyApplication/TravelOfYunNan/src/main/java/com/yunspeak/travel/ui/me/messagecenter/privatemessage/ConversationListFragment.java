package com.yunspeak.travel.ui.me.messagecenter.privatemessage;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.me.myappoint.chat.ChatActivity;
import com.yunspeak.travel.utils.ToastUtils;

public class ConversationListFragment extends EaseConversationListFragment {


    @Override
    protected void initView() {
        super.initView();

    }
    
    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        hideTitleBar();
        conversationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                EnterAppointDialog.showCommonDialog(getContext(), "删除窗口", "确定", "是否删除与当前对象的聊天窗口？", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        EMConversation conversation = conversationListView.getItem(position);
                        String username = conversation.getUserName();
                        EMClient.getInstance().chatManager().deleteConversation(username, false);//保存聊天记录
                        conversationListView.refresh();
                    }
                });

                return true;
            }
        });
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.getUserName();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    ToastUtils.showToast("不能与你自己聊天");
                else {
                    ChatActivity.start(getActivity(),username,conversation.getType() == EMConversationType.ChatRoom?EaseConstant.CHATTYPE_GROUP:EaseConstant.CHATTYPE_SINGLE);
                }
            }
        });
        super.setUpView();
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())){
         ToastUtils.showToast("无法连接聊天服务器");
        } else {
            ToastUtils.showToast("无法连接网络");
        }
    }
}
