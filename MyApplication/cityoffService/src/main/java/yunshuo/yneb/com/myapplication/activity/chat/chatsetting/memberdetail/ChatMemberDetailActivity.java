package yunshuo.yneb.com.myapplication.activity.chat.chatsetting.memberdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import yunshuo.yneb.com.myapplication.IVariable;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.activity.BaseNetWorkActivity;
import yunshuo.yneb.com.myapplication.activity.chat.chatsetting.ChatSettingBean;
import yunshuo.yneb.com.myapplication.activity.chat.chatsetting.ChatSettingUserBean;
import yunshuo.yneb.com.myapplication.other.utils.GsonUtils;
import yunshuo.yneb.com.myapplication.other.utils.MapUtils;

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
        builder.add("groupid",tId);
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
