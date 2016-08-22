package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PostOp;
import com.example.administrator.travel.ui.activity.OtherUserCenterActivity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class PostOpHolder  extends BaseHolder<Object>{
    @ViewInject(R.id.iv_post_op_icon)
    private ImageView mIvPostOpIcon;

    public PostOpHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, final Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_post_op);
        return inflate;
    }
}
