package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PostDetail;
import com.example.administrator.travel.bean.PostReply;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/11 0011.
 * 回复其他楼层
 */
public class PostReplyUserHolder extends BaseHolder<Object> {

    @ViewInject(R.id.iv_reply_icon)
    public ImageView mIvReplyIcon;
    @ViewInject(R.id.tv_reply_nick_name)
    private TextView mTvReplyNickName;
    @ViewInject(R.id.tv_reply_message)
    private TextView mTvReplyMessage;
    @ViewInject(R.id.tv_floor_number)
    private TextView mTvFloorNumber;
    @ViewInject(R.id.tv_love_number)
    private TextView mTvLoveNumber;
    @ViewInject(R.id.tv_reply_time)
    private TextView mTvReplyTime;
    @ViewInject(R.id.tv_love)
    private TextView mTvLove;



    @ViewInject(R.id.tv_reply_content)
    private TextView mTvReplyContent;
    @ViewInject(R.id.tv_reply_name)
    private TextView mTvReplyName;
    @ViewInject(R.id.tv_reply_floor_number)
    private TextView mTvReplyFloorNumber;

    public PostReplyUserHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {
        if (datas instanceof PostDetail.DataBean.ForumReplyBean){
            PostDetail.DataBean.ForumReplyBean forumReplyBean = (PostDetail.DataBean.ForumReplyBean) datas;




        }
        String content = mTvReplyContent.getText().toString().trim();
        mTvReplyContent.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        spannable.setSpan(new SomeTextClick(mContext,""),content.length()-3,content.length()
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvReplyContent.setText(spannable);
    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_post_reply_user);
        return inflate;
    }


}
