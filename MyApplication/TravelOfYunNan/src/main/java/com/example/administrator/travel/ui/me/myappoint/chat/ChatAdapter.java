package com.example.administrator.travel.ui.me.myappoint.chat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/19.
 */

public class ChatAdapter extends BaseRecycleViewAdapter<EMMessage> {
    private static final int TYPE_TEXT = 1;


    public ChatAdapter(List mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TEXT) {
            View view = inflateView(R.layout.activity_chat_text, parent);
            return new ChatLeftTextMessageHolder(view);
        }else {
            return null;
        }


    }

    @Override
    public int getItemViewType(int position) {
        EMMessage.Type type = mDatas.get(position).getType();
        if (type == EMMessage.Type.TXT) {
            return TYPE_TEXT;
        }


        return super.getItemViewType(position);
    }
}
