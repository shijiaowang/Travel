package com.yunspeak.travel;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.NetUtils;
import com.yunspeak.travel.db.DBManager;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.me.myappoint.chat.ChatActivity;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UIUtils;

import org.xutils.x;

import java.util.Iterator;
import java.util.List;


public class YunSpeakHelper {

    private static YunSpeakHelper instance;
    private EaseUI easeUI;

    public  void  setUserProfileProvider(final Context appContext){
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return DBManager.getChatUserByChatId(username);
            }
        });
        //set notification options, will use default if you don't set it
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //you can update title here
                try {
                    String from = message.getFrom();
                    EaseUser user = easeUI.getUserProfileProvider().getUser(from);
                    return user.getNickname();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "聊天消息";
                }

            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //you can update icon here
                return R.mipmap.icon;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // be used on notification bar, different text according the message type.
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if(message.getType() == EMMessage.Type.TXT){
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if(user != null){
                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
                        return String.format(appContext.getString(R.string.at_your_in_group), user.getNick());
                    }
                    return user.getNick() + ": " + ticker;
                }else{
                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
                        return String.format(appContext.getString(R.string.at_your_in_group), message.getFrom());
                    }
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                return fromUsersNum + "个联系人发送了" + messageNum + "条消息给您";
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                Intent intent;
                if (HomeActivity.instance==null){
                    intent=new Intent(appContext,HomeActivity.class);
                    intent.putExtra(IVariable.MSG_TYPE,"9");//9为跳转聊天页面
                    intent.putExtra(IVariable.CACHE_LOGIN_ARE_WITH_NETWORK,false);//9为跳转聊天页面
                }else {
                    // you can set what activity you want display when user click the notification
                    intent = new Intent(appContext, ChatActivity.class);
                }
                    EMMessage.ChatType chatType = message.getChatType();
                    if (chatType == EMMessage.ChatType.Chat) { // single chat message
                        intent.putExtra(IVariable.CHAT_ID, message.getFrom());
                        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                    } else { // group chat message
                        // message.getTo() is the group id
                        intent.putExtra(IVariable.CHAT_ID, message.getTo());
                        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                    }
                return intent;
            }
        });
    }

    private EaseUser getUserInfo(String username){
        // To get instance of EaseUser, here we get it from the user list in memory
        // You'd better cache it if you get it from your server
        EaseUser user = EaseUI.getInstance().getUserProfileProvider().getUser(username);
        if(user == null){
            user = new EaseUser(username);
            EaseCommonUtils.setUserInitialLetter(user);
        }
        return user;
    }
    /**
     * register group and contact listener, you need register when login
     */
    public void registerGroupAndContactListener(){
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
            EMClient.getInstance().groupManager().addGroupChangeListener(new MyGroupChangeListener());
            //EMClient.getInstance().contactManager().setContactListener(new MyContactListener());

    }
    public void pushActivity(Activity activity) {
        easeUI.pushActivity(activity);
    }

    public void popActivity(Activity activity) {
        easeUI.popActivity(activity);
    }
    public  void init(Context context){
        easeUI = EaseUI.getInstance();
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        options.setDeleteMessagesAsExitGroup(true);
        options.setAutoLogin(true);
        EMClient.getInstance().init(context, options);
        easeUI.init(context,options);

        //注册推送服务，每次调用register方法都会回调该接口
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid,context);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMClient.getInstance().init(context, options);
        setUserProfileProvider(context);
        registerMessageListener();
        registerGroupAndContactListener();
    }
    public synchronized static YunSpeakHelper getInstance() {
        if (instance == null) {
            instance = new YunSpeakHelper();
        }
        return instance;
    }
    private EaseNotifier getNotifier(){
        return easeUI.getNotifier();
    }
    private void registerMessageListener() {
        EMMessageListener  messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    LogUtils.e("onMessageReceived id : " + message.getMsgId());
                    // in background, do not refresh UI, notify it in notification bar
                    if(!easeUI.hasForegroundActivies()){
                        getNotifier().onNewMsg(message);
                    }
                }
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

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }
    private String getAppName(int pID,Context context) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    /**
     * group change listener
     */
    class MyGroupChangeListener implements EMGroupChangeListener {

        @Override
        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
            LogUtils.e("onInvitationReceived");
        }

        @Override
        public void onInvitationAccepted(String groupId, String invitee, String reason) {
            LogUtils.e("onInvitationAccepted");

        }

        @Override
        public void onInvitationDeclined(String groupId, String invitee, String reason) {
            LogUtils.e("onInvitationDeclined");

        }

        @Override
        public void onUserRemoved(String groupId, String groupName) {
            LogUtils.e("onUserRemoved");
        }

        @Override
        public void onGroupDestroyed(String groupId, String groupName) {
            LogUtils.e("onGroupDestroyed");
        }

        @Override
        public void onApplicationReceived(String groupId, String groupName, String applyer, String reason) {
            LogUtils.e("onApplicationReceived");
        }

        @Override
        public void onApplicationAccept(String groupId, String groupName, String accepter) {
            LogUtils.e("onApplicationAccept");
        }

        @Override
        public void onApplicationDeclined(String groupId, String groupName, String decliner, String reason) {
            LogUtils.e("onApplicationDeclined");
        }

        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
            LogUtils.e("onAutoAcceptInvitationFromGroup");
        }
    }
    //实现ConnectionListener接口
    class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            LogUtils.e("成功连接聊天服务器");
        }

        @Override
        public void onDisconnected(final int error) {

            if (error == EMError.USER_REMOVED) {
                toast("聊天账号已被移除");
            } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                LogUtils.e("账号在其他地方登录");

            } else {
                if (NetUtils.hasNetwork(UIUtils.getContext())) {
                    LogUtils.e("无法连接聊天服务器");
                }
                //连接不到聊天服务器
                else {
                    //当前网络不可用，请检查网络设置
                    toast("当前网络不可用，请检查网络。");
                }
            }

        }

    }

    private void toast(final String text) {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(text);
            }
        });
    }
}


