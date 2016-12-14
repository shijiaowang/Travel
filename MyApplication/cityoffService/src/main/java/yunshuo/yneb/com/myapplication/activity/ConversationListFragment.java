package yunshuo.yneb.com.myapplication.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;

import yunshuo.yneb.com.myapplication.activity.chat.ChatActivity;
import yunshuo.yneb.com.myapplication.other.utils.ToastUtils;

/**
 * 聊天列表
 */

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
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.getUserName();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    ToastUtils.showToast("不能与你自己聊天");
                else {
                    ChatActivity.start(getActivity(),username,conversation.getType() == EMConversationType.GroupChat?EaseConstant.CHATTYPE_GROUP:EaseConstant.CHATTYPE_SINGLE);
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
