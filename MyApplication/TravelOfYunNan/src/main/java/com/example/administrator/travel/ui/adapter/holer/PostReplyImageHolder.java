package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PostReply;
import com.example.administrator.travel.utils.LogUtils;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class PostReplyImageHolder extends BaseHolder<PostReply> {

    public ImageView mIvReplyIcon;

    public PostReplyImageHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(PostReply datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_post_reply_with_img, null);
        mIvReplyIcon = (ImageView) inflate.findViewById(R.id.iv_reply_icon);
        return inflate;
    }
}
