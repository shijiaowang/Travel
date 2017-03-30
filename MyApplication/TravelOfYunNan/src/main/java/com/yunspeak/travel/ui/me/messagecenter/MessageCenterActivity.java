package com.yunspeak.travel.ui.me.messagecenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.MessageCenterBean;
import com.yunspeak.travel.databinding.MessageCenterBinding;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.ICallBack;
import com.yunspeak.travel.download.INetworkCallBack;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseBarActivity;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.messagecenter.appointmessage.AppointMessageActivity;
import com.yunspeak.travel.ui.me.messagecenter.model.MessageCenter;
import com.yunspeak.travel.ui.me.messagecenter.model.MessageCenterModel;
import com.yunspeak.travel.ui.me.messagecenter.privatemessage.MessagePrivateActivity;
import com.yunspeak.travel.ui.me.messagecenter.relateme.RelateMeActivity;
import com.yunspeak.travel.ui.me.messagecenter.systemmessage.SystemMessageActivity;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/15 0015.
 * 消息中心
 */
public class MessageCenterActivity extends BaseBarActivity<MessageCenterBinding> {


    private MessageCenterModel messageCenterModel;

    @Override
    protected void onStart() {
        super.onStart();
        if (messageCenterModel!=null){
            messageCenterModel.refreshMessage();
        }

    }






    @Override
    protected int initLayoutRes() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initOptions() {
        Map<String, String> end = MapUtils.Build().addKey().addUserId().addType("2").end();
        HttpClient.getInstance().getDataDealErrorSelf(MessageCenter.class, new INetworkCallBack<MessageCenter>() {
            @Override
            public void accept(@NonNull MessageCenter messageCenter) throws Exception {
                messageCenterModel=messageCenter.getData();
                dataBinding.setMessageCenter(messageCenterModel);
            }

            @Override
            public void error(Throwable throwable) {
                messageCenterModel=new MessageCenterModel();
                dataBinding.setMessageCenter(messageCenterModel);
            }
        },end, IRequestUrl.MESSAGE_CENTER_COUNT);

    }

    @Override
    protected String initTitle() {
        return getString(R.string.message_center);
    }
}

