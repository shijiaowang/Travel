package com.yunspeak.travel.ui.me.myappoint.chat.chatsetting.privatesetting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.PrivateChatSettingBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.bean.ChatSettingUserBean;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/14 0014.
 * 设置
 */

public class PrivateChatSettingActivity extends BaseNetWorkActivity<PrivateChatSettingEvent> implements View.OnClickListener {

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
    @BindView(R.id.bt_clear)
    TextView btClear;
    @BindView(R.id.bt_black)
    TextView btBlack;
    private String userId;
    boolean isBlackListNumber=false;

    @Override
    protected void initEvent() {
        btClear.setOnClickListener(this);
        btBlack.setOnClickListener(this);
        userId = getIntent().getStringExtra(IVariable.DATA);
        try {
            List<String> blackListFromServer = EMClient.getInstance().contactManager().getBlackListFromServer();
            if (blackListFromServer.contains(GlobalUtils.getUserInfo().getId())){
                isBlackListNumber=true;
                btBlack.setText("移除黑名单");
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }

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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_clear:
                EnterAppointDialog.showCommonDialog(this, "删除聊天记录", "确定", "是否删除相关聊天记录？", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        boolean isSuccess = EMClient.getInstance().chatManager().deleteConversation(userId, true);
                        if (isSuccess){
                            ToastUtils.showToast("聊天记录删除完毕");
                        }else {
                            ToastUtils.showToast("删除失败");
                        }
                    }
                });
                break;
            case R.id.bt_black:
                if (isBlackListNumber){
                    EnterAppointDialog.showCommonDialog(this, "移除黑名单", "确定", "是否将该用户从黑名单中删除？", new ParentPopClick() {
                        @Override
                        public void onClick(int type) {
                            try {
                                EMClient.getInstance().contactManager().removeUserFromBlackList(userId);
                                ToastUtils.showToast("移除黑名单成功");
                                isBlackListNumber = false;
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                ToastUtils.showToast("移除黑名单失败" + e.getMessage());
                            }

                        }
                    });
                }else {
                    EnterAppointDialog.showCommonDialog(this, "加入黑名单", "确定", "是否将该用户加入黑名单？", new ParentPopClick() {
                        @Override
                        public void onClick(int type) {
                            try {
                                EMClient.getInstance().contactManager().addUserToBlackList(userId, true);
                                ToastUtils.showToast("加入黑名单成功");
                                isBlackListNumber = true;
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                ToastUtils.showToast("加入黑名单失败" + e.getMessage());
                            }

                        }
                    });
                }

                break;

        }
    }
}
