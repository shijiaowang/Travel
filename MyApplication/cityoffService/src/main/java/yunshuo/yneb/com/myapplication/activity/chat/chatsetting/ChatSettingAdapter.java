package yunshuo.yneb.com.myapplication.activity.chat.chatsetting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.activity.chat.chatsetting.privatesetting.PrivateChatSettingActivity;
import yunshuo.yneb.com.myapplication.other.adapter.BaseRecycleViewAdapter;
import yunshuo.yneb.com.myapplication.other.adapter.BaseRecycleViewHolder;
import yunshuo.yneb.com.myapplication.other.utils.FrescoUtils;

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
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatSettingHolder(inflateView(R.layout.item_activity_chat_setting, parent));
    }

    class ChatSettingHolder extends BaseRecycleViewHolder<ChatSettingUserBean> {
        @BindView(R.id.iv_icon)
        SimpleDraweeView ivIcon;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        public ChatSettingHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void childBindView(int position, final ChatSettingUserBean dataBean, final Context mContext) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrivateChatSettingActivity.start(mContext,dataBean.getId());
                }
            });
            tvUserName.setText(dataBean.getNick_name());
            FrescoUtils.displayIcon(ivIcon,dataBean.getUser_img());
        }
    }
}
