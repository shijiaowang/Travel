package com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.memberdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.ChatSettingBean;
import com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.ChatSettingUserBean;
import com.example.administrator.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class ChatMemberDetailAdapter extends BaseRecycleViewAdapter<ChatSettingUserBean> {


    public ChatMemberDetailAdapter(List<ChatSettingUserBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_chat_member_detail, parent, false);
        return new ChatMemberDetailHolder(inflate);
    }

    @Override
    protected void childBindView(RecyclerView.ViewHolder holder, int position, final ChatSettingUserBean dataBean) {
        final ChatMemberDetailHolder chatMemberDetailHolder= (ChatMemberDetailHolder) holder;
        chatMemberDetailHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserCenterActivity.start(mContext,chatMemberDetailHolder.ivIcon,dataBean.getId());
            }
        });
        FrescoUtils.displayIcon(chatMemberDetailHolder.ivIcon,dataBean.getUser_img());
        chatMemberDetailHolder.tvDes.setText(dataBean.getContent());
        chatMemberDetailHolder.tvUserName.setText(dataBean.getNick_name());
        if (dataBean.getIs_boss()==1){
            chatMemberDetailHolder.tvType.setVisibility(View.VISIBLE);
            chatMemberDetailHolder.tvType.setText("发布人");
            chatMemberDetailHolder.tvType.setBackgroundResource(R.drawable.r_green);
        }else if (dataBean.getIs_management()==1){
            chatMemberDetailHolder.tvType.setVisibility(View.VISIBLE);
            chatMemberDetailHolder.tvType.setText("专管员");
            chatMemberDetailHolder.tvType.setBackgroundResource(R.drawable.r_red);
        }else {
            chatMemberDetailHolder.tvType.setVisibility(View.GONE);
        }

    }


    class ChatMemberDetailHolder extends BaseRecycleViewHolder {
        @BindView(R.id.iv_icon) SimpleDraweeView ivIcon;
        @BindView(R.id.tv_user_name) TextView tvUserName;
        @BindView(R.id.tv_des) TextView tvDes;
        @BindView(R.id.tv_type) TextView tvType;
        public ChatMemberDetailHolder(View itemView) {
            super(itemView);
        }
    }
}
