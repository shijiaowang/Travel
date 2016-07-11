package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PostReply;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class PostReplyTextHolder extends BaseHolder<PostReply> {
    public PostReplyTextHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(PostReply datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_post_reply, null);
        return inflate;
    }
}
