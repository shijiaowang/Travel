package com.yunspeak.travel.ui.me.myappoint.chat.chatsetting.privatesetting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.myappoint.chat.chatsetting.ChatSettingUserBean;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

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
    @BindView(R.id.s_toggle_message)
    Switch sToggleMessage;
    @BindView(R.id.s_toggle)
    Switch sToggle;
    @BindView(R.id.bt_clear)
    Button btClear;
    @BindView(R.id.bt_black)
    Button btBlack;
    private String userId;
    private String tId;

    @Override
    protected void initEvent() {
        userId = getIntent().getStringExtra(IVariable.ID);
        tId = getIntent().getStringExtra(IVariable.TID);
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.add(IVariable.USER_ID,userId).addtId(tId);
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

    public static void start(Context context, String id,String tId) {
        Intent intent = new Intent(context, PrivateChatSettingActivity.class);
        intent.putExtra(IVariable.ID, id);
        intent.putExtra(IVariable.TID, tId);
        context.startActivity(intent);
    }


}
