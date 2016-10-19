package com.example.administrator.travel.ui.me.myappoint.chat;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.utils.GlobalUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/19.
 */

public class ChatLeftTextMessageHolder extends BaseRecycleViewHolder<EMMessage> {
    @BindView(R.id.iv_icon_left) SimpleDraweeView ivIconLeft;
    @BindView(R.id.tv_user_name_left) TextView tvUserNameLeft;
    @BindView(R.id.tv_text_left) TextView tvTextLeft;
    @BindView(R.id.rl_left) RelativeLayout rlLeft;
    @BindView(R.id.iv_icon_right) SimpleDraweeView ivIconRight;
    @BindView(R.id.tv_user_name_right) TextView tvUserNameRight;
    @BindView(R.id.tv_text_right) TextView tvTextRight;
    @BindView(R.id.rl_right) RelativeLayout rlRight;
    public ChatLeftTextMessageHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, EMMessage data, Context mContext) {
        EMTextMessageBody body = (EMTextMessageBody)data.getBody();
        if (data.getFrom().equals(GlobalUtils.getUserInfo().getId())){
             rlLeft.setVisibility(View.GONE);
             rlRight.setVisibility(View.VISIBLE);
             tvTextRight.setText(body.getMessage());
         }else {
             rlLeft.setVisibility(View.VISIBLE);
             rlRight.setVisibility(View.GONE);
             tvTextLeft.setText(body.getMessage());
         }

    }
}
