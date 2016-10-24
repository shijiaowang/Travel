package com.yunspeak.travel.ui.me.myappoint.chat.chatsetting.memberdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.myappoint.chat.chatsetting.ChatSettingBean;
import com.yunspeak.travel.ui.me.myappoint.chat.chatsetting.ChatSettingUserBean;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FrescoUtils;
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
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_chat_member_detail, parent, false);
        return new ChatMemberDetailHolder(inflate);
    }




    class ChatMemberDetailHolder extends BaseRecycleViewHolder<ChatSettingUserBean> {
        @BindView(R.id.iv_icon) SimpleDraweeView ivIcon;
        @BindView(R.id.tv_user_name) TextView tvUserName;
        @BindView(R.id.tv_des) TextView tvDes;
        @BindView(R.id.tv_type) TextView tvType;
        public ChatMemberDetailHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void childBindView(int position, final ChatSettingUserBean dataBean, final Context mContext) {
          itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OtherUserCenterActivity.start(mContext,ivIcon,dataBean.getId());
                }
            });
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
    }
}
