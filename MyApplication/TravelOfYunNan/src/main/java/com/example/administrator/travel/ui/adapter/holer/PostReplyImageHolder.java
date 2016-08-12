package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PostReply;
import com.example.administrator.travel.utils.LogUtils;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class PostReplyImageHolder extends BaseHolder<PostReply> {
   @ViewInject(R.id.iv_reply_icon)
    public ImageView mIvReplyIcon;

    public PostReplyImageHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(PostReply datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_post_reply_with_img);
       /* mIvReplyIcon = (ImageView) inflate.findViewById(R.id.iv_reply_icon);*/
        return inflate;
    }
}
