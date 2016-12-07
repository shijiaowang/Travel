package com.yunspeak.travel.ui.me.myappoint.chat.chatsetting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.home.HotSpotsItemDecoration;
import com.yunspeak.travel.ui.me.myappoint.chat.chatsetting.memberdetail.ChatMemberDetailActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/14 0014.
 * 聊天设置
 */

public class ChatSettingActivity extends BaseNetWorkActivity<ChatSettingEvent> implements View.OnClickListener {
    @BindView(R.id.tv_cursor)
    FontsIconTextView tvCursor;
    @BindView(R.id.tv_chat_number)
    TextView tvChatNumber;
    @BindView(R.id.bt_clear)
    TextView btClear;
    @BindView(R.id.rv_member)
    RecyclerView rvMember;
    private String chatId;

    @Override
    protected void initEvent() {
        chatId = getIntent().getStringExtra(IVariable.DATA);
        tvCursor.setOnClickListener(this);
        btClear.setOnClickListener(this);
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.add("groupid",chatId);
    }

    @Override
    protected String initUrl() {
        return IVariable.GET_ENTER_APPOINT;
    }

    @Override
    protected void onSuccess(ChatSettingEvent chatSettingEvent) {
        ChatSettingBean chatSettingBean = GsonUtils.getObject(chatSettingEvent.getResult(), ChatSettingBean.class);
        List<ChatSettingUserBean> dataBeen = chatSettingBean.getData();
        tvChatNumber.setText("群成员("+dataBeen.size()+")");
        ChatSettingAdapter chatSettingAdapter=new ChatSettingAdapter(dataBeen,this,chatId);
        rvMember.setAdapter(chatSettingAdapter);
        LinearLayoutManager linearLayoutManager=new GridLayoutManager(this,dataBeen.size());
        rvMember.setLayoutManager(linearLayoutManager);
        rvMember.addItemDecoration(new HotSpotsItemDecoration(15));

    }


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_chat_setting;
    }

    @Override
    protected String initTitle() {
        return "聊天设置";
    }

    public static void start(Context context, String tid) {
        Intent intent = new Intent(context, ChatSettingActivity.class);
        intent.putExtra(IVariable.TID, tid);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cursor:
                ChatMemberDetailActivity.start(this,chatId);
                break;
            case R.id.bt_clear:
                EnterAppointDialog.showCommonDialog(this, "删除聊天记录", "确定", "是否删除相关聊天记录？", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        boolean isSuccess = EMClient.getInstance().chatManager().deleteConversation(chatId, true);
                        if (isSuccess){
                            ToastUtils.showToast("聊天记录删除完毕");
                        }else {
                            ToastUtils.showToast("删除失败");
                        }
                    }
                });

                break;
        }
    }
}
