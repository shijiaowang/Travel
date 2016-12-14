package yunshuo.yneb.com.myapplication.other.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.easeui.EaseConstant;

import butterknife.BindView;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.activity.chat.ChatActivity;
import yunshuo.yneb.com.myapplication.other.bean.SearchUserBean;
import yunshuo.yneb.com.myapplication.other.utils.FrescoUtils;

/**
 * Created by wangyang on 2016/8/22 0022.
 */
public class SearchCommonHolder extends BaseRecycleViewHolder<SearchUserBean.DataBean> {
    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;


    public SearchCommonHolder(View itemView) {
        super(itemView);
    }
    @Override
    public void childBindView(final int position, final SearchUserBean.DataBean data, final Context mContext) {
        tvTitle.setText(data.getNick_name());
        tvContent.setText(data.getContent());
        FrescoUtils.displayIcon(ivIcon, data.getUser_img());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getId()==null)return;
                ChatActivity.start(mContext,data.getId(), EaseConstant.CHATTYPE_SINGLE);
            }
        });
    }

}
