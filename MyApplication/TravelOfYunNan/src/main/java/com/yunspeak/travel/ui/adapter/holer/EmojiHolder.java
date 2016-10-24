package com.yunspeak.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.R;

/**
 * Created by android on 2016/7/31.
 */
public class EmojiHolder extends BaseHolder {
    public EmojiHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_create_post_emoji);
    }
}
