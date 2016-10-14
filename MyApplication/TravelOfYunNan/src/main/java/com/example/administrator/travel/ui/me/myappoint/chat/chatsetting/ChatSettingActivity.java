package com.example.administrator.travel.ui.me.myappoint.chat.chatsetting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.HotSpotsItemDecoration;
import com.example.administrator.travel.ui.adapter.SpaceItemDecoration;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.memberdetail.ChatMemberDetailActivity;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;

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
    @BindView(R.id.s_toggle)
    Switch sToggle;
    @BindView(R.id.bt_clear)
    Button btClear;
    @BindView(R.id.rv_member)
    RecyclerView rvMember;
    private String tId;

    @Override
    protected void initEvent() {
        tId = getIntent().getStringExtra(IVariable.TID);
        tvCursor.setOnClickListener(this);
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
    protected void onSuccess(ChatSettingEvent chatSettingEvent) {
        ChatSettingBean chatSettingBean = GsonUtils.getObject(chatSettingEvent.getResult(), ChatSettingBean.class);
        List<ChatSettingUserBean> dataBeen = chatSettingBean.getData();
        tvChatNumber.setText("群成员("+dataBeen.size()+")");
        ChatSettingAdapter chatSettingAdapter=new ChatSettingAdapter(dataBeen,this,tId);
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
                ChatMemberDetailActivity.start(this,tId);
                break;
        }
    }
}
