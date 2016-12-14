package yunshuo.yneb.com.myapplication.activity.chat.chatsetting.privatesetting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import yunshuo.yneb.com.myapplication.IVariable;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.activity.BaseNetWorkActivity;
import yunshuo.yneb.com.myapplication.activity.chat.chatsetting.ChatSettingUserBean;
import yunshuo.yneb.com.myapplication.other.utils.FrescoUtils;
import yunshuo.yneb.com.myapplication.other.utils.GsonUtils;
import yunshuo.yneb.com.myapplication.other.utils.MapUtils;
import yunshuo.yneb.com.myapplication.other.view.FontsIconTextView;

/**
 * Created by wangyang on 2016/10/14 0014.
 * 设置
 */

public class PrivateChatSettingActivity extends BaseNetWorkActivity<PrivateChatSettingEvent> {

    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_cursor)
    FontsIconTextView tvCursor;
    private String userId;

    @Override
    protected void initEvent() {

        userId = getIntent().getStringExtra(IVariable.DATA);
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.add(IVariable.USER_ID,userId).addtId(userId);
    }

    @Override
    protected String initUrl() {
        return IVariable.CHAT_SETTING_USER_INFO;
    }

    @Override
    protected void onSuccess(PrivateChatSettingEvent privateChatSettingEvent) {
        PrivateChatSettingBean privateChatSettingBean = GsonUtils.getObject(privateChatSettingEvent.getResult(), PrivateChatSettingBean.class);
        ChatSettingUserBean dataBean = privateChatSettingBean.getData();
        FrescoUtils.displayIcon(ivIcon,dataBean.getUser_img());
        tvDes.setText(dataBean.getContent());
         tvUserName.setText(dataBean.getNick_name());
        if (dataBean.getIs_boss()==1){
            tvType.setVisibility(View.VISIBLE);
            tvType.setText("发布人");
            tvType.setBackgroundResource(R.drawable.r_green);
        }else if (dataBean.getIs_management()==1){
            tvType.setVisibility(View.VISIBLE);
            tvType.setText("专管员");
            tvType.setBackgroundResource(R.drawable.r_red);
        }else {
            tvType.setVisibility(View.GONE);
        }
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_private_chat_setting;
    }

    @Override
    protected String initTitle() {
        return "私聊设置";
    }

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, PrivateChatSettingActivity.class);
        intent.putExtra(IVariable.DATA, id);
        context.startActivity(intent);
    }
}
