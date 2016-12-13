package yunshuo.yneb.com.myapplication.activity.chat.chatsetting.memberdetail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.activity.chat.chatsetting.ChatSettingUserBean;
import yunshuo.yneb.com.myapplication.other.adapter.BaseRecycleViewAdapter;
import yunshuo.yneb.com.myapplication.other.adapter.BaseRecycleViewHolder;
import yunshuo.yneb.com.myapplication.other.utils.FrescoUtils;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class ChatMemberDetailAdapter extends BaseRecycleViewAdapter<ChatSettingUserBean> {


    public ChatMemberDetailAdapter(List<ChatSettingUserBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatMemberDetailHolder(inflateView(R.layout.item_activity_chat_member_detail, parent));
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
                    // TODO: 2016/12/13 0013 点击用户处理
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
