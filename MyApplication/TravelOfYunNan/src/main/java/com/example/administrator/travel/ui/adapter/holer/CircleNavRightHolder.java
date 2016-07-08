package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleNavRight;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavRightHolder extends BaseHolder<CircleNavRight> {

    private RelativeLayout mRlPost;
    public CircleNavRightHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CircleNavRight datas, final Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View root=View.inflate(mContext, R.layout.item_fragment_circle_nav_right,null);
        mRlPost = (RelativeLayout) root.findViewById(R.id.rl_post);
        return root;
    }
}
