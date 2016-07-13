package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PostOp;
import com.example.administrator.travel.ui.activity.OtherUserCenterActivity;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class PostOpHolder  extends BaseHolder<PostOp>{

    private ImageView mIvPostOpIcon;

    public PostOpHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(PostOp datas, final Context mContext) {
       mIvPostOpIcon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mContext.startActivity(new Intent(mContext,OtherUserCenterActivity.class));
           }
       });
    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_post_op, null);
        mIvPostOpIcon = (ImageView) inflate.findViewById(R.id.iv_post_op_icon);

        return inflate;
    }
}
