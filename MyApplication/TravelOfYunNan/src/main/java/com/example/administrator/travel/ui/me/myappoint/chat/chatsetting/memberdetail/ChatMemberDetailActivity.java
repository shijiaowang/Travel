package com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.memberdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.ChatSettingBean;
import com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.ChatSettingUserBean;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/10/14 0014.
 * 成员详情
 */

public class ChatMemberDetailActivity extends BaseNetWorkActivity<ChatMemberDetailEvent> {

    @BindView(R.id.rv_member)
    RecyclerView rvMember;
    private String tId;

    @Override
    protected void initEvent() {
        tId = getIntent().getStringExtra(IVariable.TID);
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.addtId(tId);
    }

    @Override
    protected String initUrl() {
        return IVariable.GET_ENTER_APPOINT;
    }

    @Override
    protected void onSuccess(ChatMemberDetailEvent chatMemberDetailEvent) {
        ChatSettingBean chatSettingBean = GsonUtils.getObject(chatMemberDetailEvent.getResult(), ChatSettingBean.class);
        List<ChatSettingUserBean> data = chatSettingBean.getData();
        ChatMemberDetailAdapter chatMemberDetailAdapter = new ChatMemberDetailAdapter(data, this);
        rvMember.setAdapter(chatMemberDetailAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvMember.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_chat_member;
    }

    @Override
    protected String initTitle() {
        return "成员详情";
    }

    public static void start(Context context, String tid) {
        Intent intent = new Intent(context, ChatMemberDetailActivity.class);
        intent.putExtra(IVariable.TID, tid);
        context.startActivity(intent);
    }

}
