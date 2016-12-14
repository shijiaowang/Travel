package yunshuo.yneb.com.myapplication.activity.chat.chatsetting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import yunshuo.yneb.com.myapplication.IVariable;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.activity.BaseNetWorkActivity;
import yunshuo.yneb.com.myapplication.activity.chat.chatsetting.memberdetail.ChatMemberDetailActivity;
import yunshuo.yneb.com.myapplication.other.utils.GsonUtils;
import yunshuo.yneb.com.myapplication.other.utils.MapUtils;
import yunshuo.yneb.com.myapplication.other.view.FontsIconTextView;

/**
 * Created by wangyang on 2016/10/14 0014.
 * 聊天设置
 */

public class ChatSettingActivity extends BaseNetWorkActivity<ChatSettingEvent> implements View.OnClickListener {
    @BindView(R.id.tv_cursor)
    FontsIconTextView tvCursor;
    @BindView(R.id.tv_chat_number)
    TextView tvChatNumber;
    @BindView(R.id.rv_member)
    RecyclerView rvMember;
    private String chatId;

    @Override
    protected void initEvent() {
        chatId = getIntent().getStringExtra(IVariable.DATA);
        tvCursor.setOnClickListener(this);
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
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
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

        }
    }
}
