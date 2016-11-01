package com.yunspeak.travel.ui.find.findcommon.deliciousdetail;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadingBarBaseActivity;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/8/25 0025.
 * 发现评论公共adapter
 */
public class DiscussCommonAdapter extends BaseRecycleViewAdapter<TravelReplyBean> {
    private static final int TYPE_POST_USER=0;
    private static final int TYPE_POST_NORMAL=1;



    private  String typeDestination;

    public DiscussCommonAdapter(List<TravelReplyBean> mDatas, Context mContext,String typeDestination) {
        super(mDatas, mContext);
        this.typeDestination = typeDestination;
    }




    @Override
    public BaseRecycleViewHolder<TravelReplyBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_POST_USER){
            return new DiscussCommonReplyUserHolder(inflateView(R.layout.item_activity_post_reply_user,parent),typeDestination);
        }
        return new DiscussCommonReplyTextHolder(inflateView(R.layout.item_activity_post_reply,parent),typeDestination);
    }

    @Override
    public int getItemViewType(int position) {
        TravelReplyBean forumReplyBean = mDatas.get(position);
        if (forumReplyBean.getPid().equals("0")) {
            return TYPE_POST_NORMAL;
        } else{
            return TYPE_POST_USER;
        }
    }






}
