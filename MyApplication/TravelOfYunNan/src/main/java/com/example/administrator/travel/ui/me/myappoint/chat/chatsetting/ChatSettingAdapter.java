package com.example.administrator.travel.ui.me.myappoint.chat.chatsetting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.privatesetting.PrivateChatSettingActivity;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class ChatSettingAdapter extends BaseRecycleViewAdapter<ChatSettingUserBean> {


    private final String tid;

    public ChatSettingAdapter(List<ChatSettingUserBean> mDatas, Context mContext, String tid) {
        super(mDatas, mContext);
        this.tid = tid;
    }

    @Override
    protected void childBindView(RecyclerView.ViewHolder holder, int position, final ChatSettingUserBean dataBean) {

        ChatSettingHolder chatSettingHolder = (ChatSettingHolder) holder;
        chatSettingHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivateChatSettingActivity.start(mContext,dataBean.getId(),tid);
            }
        });
        chatSettingHolder.tvUserName.setText(dataBean.getNick_name());
        FrescoUtils.displayIcon(chatSettingHolder.ivIcon,dataBean.getUser_img());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_chat_setting, parent, false);
        return new ChatSettingHolder(inflate);
    }

    class ChatSettingHolder extends BaseRecycleViewHolder {
        @BindView(R.id.iv_icon)
        SimpleDraweeView ivIcon;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        public ChatSettingHolder(View itemView) {
            super(itemView);
        }
    }
}
